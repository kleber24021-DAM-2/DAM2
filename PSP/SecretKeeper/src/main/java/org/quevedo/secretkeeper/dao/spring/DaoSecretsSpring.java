package org.quevedo.secretkeeper.dao.spring;

import io.vavr.control.Either;
import lombok.extern.log4j.Log4j2;
import org.quevedo.secretkeeper.dao.api.DaoSecrets;
import org.quevedo.secretkeeper.dao.security.GCMCipher;
import org.quevedo.secretkeeper.dao.utils.DBConnectionPool;
import org.quevedo.secretkeeper.dao.utils.DaoConsts;
import org.quevedo.secretkeeper.model.Secret;
import org.springframework.dao.DataAccessException;
import org.springframework.dao.IncorrectResultSizeDataAccessException;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.jdbc.support.KeyHolder;

import javax.inject.Inject;
import java.sql.PreparedStatement;
import java.sql.Statement;
import java.util.List;
import java.util.Objects;

@Log4j2
public class DaoSecretsSpring implements DaoSecrets {

    private final JdbcTemplate template;
    private final GCMCipher gcmCipher;
    private final DBConnectionPool dbConnectionPool;

    @Inject
    public DaoSecretsSpring(JdbcTemplate jdbcTemplate, GCMCipher gcmCipher, DBConnectionPool dbConnectionPool) {
        this.template = jdbcTemplate;
        this.gcmCipher = gcmCipher;
        this.dbConnectionPool = dbConnectionPool;
    }

    @Override
    public Either<String, Secret> saveSecret(Secret toSave, String password) {
        Either<String, Secret> result;
        String cipheredMessage = gcmCipher.encrypt(toSave.getMessage(), password);
        if (cipheredMessage != null) {
            toSave.setMessage(cipheredMessage);
            try {
                KeyHolder keyHolder = new GeneratedKeyHolder();
                template.update(connection -> {
                    PreparedStatement ps = connection.prepareStatement(
                            DaoConsts.INSERT_SECRET, Statement.RETURN_GENERATED_KEYS);
                    ps.setString(1, toSave.getMessage());
                    ps.setString(2, toSave.getOwner());
                    return ps;
                }, keyHolder);
                toSave.setId(Objects.requireNonNull(keyHolder.getKey()).intValue());
                result = Either.right(toSave);
            } catch (DataAccessException dataAccessException) {
                log.error(dataAccessException.getMessage(), dataAccessException);
                result = Either.left(DaoConsts.MSG_DB_FAILED);
            }
        } else {
            result = Either.left(DaoConsts.MSG_ENCRYPT_FAILED);
        }
        return result;
    }

    @Override
    public Either<String, Secret> fetchSecret(Secret toFetch, String password) {
        Either<String, Secret> result;
        try {
            Secret fetchedSecret = template.queryForObject(DaoConsts.SELECT_SECRET_BY_ID, new BeanPropertyRowMapper<>(Secret.class), toFetch.getId());
            String decrypted = gcmCipher.decrypt(Objects.requireNonNull(fetchedSecret).getMessage(), password);
            if (decrypted != null) {
                toFetch.setMessage(decrypted);
                result = Either.right(toFetch);
            } else {
                result = Either.left(DaoConsts.MSG_DECRYPT_FAILED);
            }
        } catch (IncorrectResultSizeDataAccessException incorrectResultSizeDataAccessException) {
            result = Either.left(DaoConsts.MSG_SECRET_NULL);
        } catch (DataAccessException dataAccessException) {
            log.error(dataAccessException);
            result = Either.left(DaoConsts.MSG_DB_FAILED);
        }
        return result;
    }

    @Override
    public Either<String, List<Secret>> getAllUndecryptedSecrets() {
        Either<String, List<Secret>> result;
        try {
            result = Either.right(template.query(DaoConsts.SELECT_ALL_SECRETS, new BeanPropertyRowMapper<>(Secret.class)));
        } catch (DataAccessException dataAccessException) {
            log.error(dataAccessException.getMessage(), dataAccessException);
            result = Either.left(DaoConsts.MSG_DB_FAILED);
        }
        return result;
    }

    @Override
    public void closePool() {
        dbConnectionPool.closePool();
    }
}
