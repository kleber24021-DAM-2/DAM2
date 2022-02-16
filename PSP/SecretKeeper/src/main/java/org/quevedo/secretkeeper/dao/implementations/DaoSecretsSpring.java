package org.quevedo.secretkeeper.dao.implementations;

import io.vavr.control.Either;
import lombok.extern.log4j.Log4j2;
import org.quevedo.secretkeeper.dao.api.DaoSecrets;
import org.quevedo.secretkeeper.dao.utils.DBConnectionPool;
import org.quevedo.secretkeeper.dao.utils.DaoConsts;
import org.quevedo.secretkeeper.dao.utils.UserCache;
import org.quevedo.secretkeeper.model.Secret;
import org.quevedo.secretkeeper.security.simetrical.GCMCipher;
import org.springframework.dao.DataAccessException;
import org.springframework.dao.IncorrectResultSizeDataAccessException;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.datasource.DataSourceTransactionManager;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.jdbc.support.KeyHolder;
import org.springframework.transaction.TransactionDefinition;
import org.springframework.transaction.TransactionStatus;
import org.springframework.transaction.support.DefaultTransactionDefinition;

import javax.inject.Inject;
import java.sql.PreparedStatement;
import java.sql.Statement;
import java.util.List;
import java.util.Map;
import java.util.Objects;

@Log4j2
public class DaoSecretsSpring implements DaoSecrets {

    private final JdbcTemplate template;
    private final GCMCipher gcmCipher;
    private final DBConnectionPool dbConnectionPool;

    @Inject
    public DaoSecretsSpring(JdbcTemplate jdbcTemplate, GCMCipher gcmCipher, DBConnectionPool dbConnectionPool, UserCache userCache) {
        this.template = jdbcTemplate;
        this.gcmCipher = gcmCipher;
        this.dbConnectionPool = dbConnectionPool;
    }


    @Override
    public Either<String, Secret> saveSecret(Secret toSave) {
        Either<String, Secret> result;

        KeyHolder keyHolder = new GeneratedKeyHolder();
        TransactionDefinition transactionDefinition = new DefaultTransactionDefinition();
        DataSourceTransactionManager transactionManager = new DataSourceTransactionManager(dbConnectionPool.getHikariDatasource());
        TransactionStatus status = transactionManager.getTransaction(transactionDefinition);

        try {
            template.update(connection -> {
                PreparedStatement preparedStatement = connection.prepareStatement(DaoConsts.QUERY_INSERT_SECRET, Statement.RETURN_GENERATED_KEYS);
                preparedStatement.setString(1, toSave.getMessage());
                preparedStatement.setString(2, toSave.getSecretName());
                return preparedStatement;
            }, keyHolder);
            toSave.setId(Objects.requireNonNull(keyHolder.getKey()).intValue());
            toSave.getSharedWith().forEach((name, pass) ->
                    template.update(connection -> {
                        PreparedStatement preparedStatement = connection.prepareStatement(DaoConsts.QUERY_INSERT_SHARED);
                        preparedStatement.setString(1, name);
                        preparedStatement.setInt(2, toSave.getId());
                        preparedStatement.setString(3, pass);
                        return preparedStatement;
                    })
            );
            transactionManager.commit(status);
            result = Either.right(toSave);
        } catch (Exception e) {
            transactionManager.rollback(status);
            log.error(e.getMessage(), e);
            result = Either.left(e.getMessage());
        }
        return result;
    }

    @Override
    public Either<String, Secret> fetchSecret(Secret toFetch) {
        Either<String, Secret> result;
        try {
            Secret fetchedSecret = template.queryForObject(DaoConsts.SELECT_SECRET_BY_ID, new BeanPropertyRowMapper<>(Secret.class), toFetch.getId());
            List<Map<String, Object>> results = template.queryForList(DaoConsts.QUERY_GET_SHARED_WITH_BY_ID, toFetch.getId());
            results.forEach(map -> fetchedSecret.addToSharedUsers((String) map.get(DaoConsts.NOMBRE_USUARIO), (String) map.get(DaoConsts.PASSWORD_CIFRADA)));
            result = Either.right(fetchedSecret);
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
