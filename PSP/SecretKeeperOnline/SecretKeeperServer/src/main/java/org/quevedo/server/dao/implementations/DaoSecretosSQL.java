package org.quevedo.server.dao.implementations;

import io.vavr.control.Either;
import jakarta.inject.Inject;
import lombok.extern.log4j.Log4j2;
import org.quevedo.common.models.Secreto;
import org.quevedo.common.models.SecretoDTO;
import org.quevedo.server.dao.interfaces.DaoSecretos;
import org.quevedo.server.dao.utils.DaoConstants;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.datasource.DataSourceTransactionManager;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.jdbc.support.KeyHolder;
import org.springframework.transaction.TransactionDefinition;
import org.springframework.transaction.TransactionStatus;
import org.springframework.transaction.support.DefaultTransactionDefinition;

import javax.sql.DataSource;
import java.sql.PreparedStatement;
import java.sql.Statement;
import java.util.List;
import java.util.Map;

@Log4j2
public class DaoSecretosSQL implements DaoSecretos {
    private final JdbcTemplate template;
    private final DataSource dataSource;

    @Inject
    public DaoSecretosSQL(JdbcTemplate template, DataSource dataSource) {
        this.template = template;
        this.dataSource = dataSource;
    }

    @Override
    public Either<String, List<Secreto>> getAllSecretosByUsername(String username) {
        Either<String, List<Secreto>> result;
        try {
            List<Secreto> secretoList = template.query(DaoConstants.SELECT_SECRET_BY_USERNAME, new BeanPropertyRowMapper<>(Secreto.class), username);
            result = Either.right(secretoList);
        } catch (Exception e) {
            log.error(e.getMessage(), e);
            result = Either.left(e.getMessage());
        }
        return result;
    }


    @Override
    public Either<String, SecretoDTO> saveSecret(SecretoDTO secreto) {
        Either<String, SecretoDTO> result;
        KeyHolder keyHolder = new GeneratedKeyHolder();
        TransactionDefinition transactionDefinition = new DefaultTransactionDefinition();
        DataSourceTransactionManager transactionManager = new DataSourceTransactionManager(dataSource);
        TransactionStatus status = transactionManager.getTransaction(transactionDefinition);
        try {
            JdbcTemplate jdbcTemplate = new JdbcTemplate(transactionManager.getDataSource());
            jdbcTemplate.update(connection -> {
                PreparedStatement ps1 =
                        connection.prepareStatement(
                                DaoConstants.INSERT_SECRET,
                                Statement.RETURN_GENERATED_KEYS);
                ps1.setString(1, secreto.getMessage());
                ps1.setString(2, secreto.getSecretName());
                ps1.setString(3, secreto.getSignature());
                ps1.setString(4, secreto.getSecretOwner());
                return ps1;
            }, keyHolder);
            secreto.setId(keyHolder.getKey().intValue());
            Map<String, String> map = secreto.getSharedWith();
            map.forEach(((usuario, s) -> {
                jdbcTemplate.update(connection -> {
                    PreparedStatement ps2 =
                            connection.prepareStatement(
                                    DaoConstants.INSERT_SHARED
                            );
                    ps2.setString(1, secreto.getSharedWith().get(usuario));
                    ps2.setInt(2, Integer.parseInt(usuario));
                    ps2.setInt(3, secreto.getId());
                    return ps2;
                });
            }));
            transactionManager.commit(status);
            secreto.setId(keyHolder.getKey().intValue());
            result = Either.right(secreto);
        } catch (Exception e) {
            log.error(e.getMessage(), e);
            transactionManager.rollback(status);
            result = Either.left(e.getMessage());
        }
        return result;
    }

    @Override
    public Either<String, String> getOwnPasswordBySecretIdAndUserId(int secretId, int userId) {
        Either<String, String> result;
        try {
            String password = template.queryForObject(DaoConstants.SELECT_PASSWORD, String.class, secretId, userId);
            result = Either.right(password);
        } catch (Exception e) {
            log.error(e.getMessage(), e);
            result = Either.left(e.getMessage());
        }
        return result;
    }
}
