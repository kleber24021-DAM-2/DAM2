package org.quevedo.server.dao.implementations;

import io.vavr.control.Either;
import jakarta.inject.Inject;
import lombok.extern.log4j.Log4j2;
import org.quevedo.common.models.Usuario;
import org.quevedo.server.dao.interfaces.DaoUsuario;
import org.quevedo.server.dao.utils.DaoConstants;
import org.springframework.dao.DataAccessException;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.jdbc.support.KeyHolder;

import java.sql.PreparedStatement;
import java.sql.Statement;
import java.util.List;
import java.util.Objects;

@Log4j2
public class DaoUsersSQL implements DaoUsuario {
    private final JdbcTemplate template;

    @Inject
    public DaoUsersSQL(JdbcTemplate template) {
        this.template = template;
    }

    @Override
    public Either<String, Usuario> registerUser(String username, String userCertificate) {
        Either<String, Usuario> result;
        try {
            KeyHolder keyHolder = new GeneratedKeyHolder();
            template.update(connection -> {
                PreparedStatement ps = connection.prepareStatement(
                        DaoConstants.INSERT_USER,
                        Statement.RETURN_GENERATED_KEYS
                );
                ps.setString(1, username);
                ps.setString(2, userCertificate);
                return ps;
            }, keyHolder);
            result = Either.right(new Usuario(Objects.requireNonNull(keyHolder.getKey()).intValue(), username, userCertificate));
        } catch (DataAccessException dataAccessException) {
            log.error(dataAccessException.getMessage(), dataAccessException);
            result = Either.left(dataAccessException.getMessage());
        }
        return result;
    }

    @Override
    public Either<String, Boolean> userExists(String username) {
        Either<String, Boolean> result;
        try {
            List<Usuario> users = template.query(DaoConstants.SELECT_USER_BY_NAME, new BeanPropertyRowMapper<>(Usuario.class), username);
            result = Either.right(users.stream().anyMatch(user -> user.getNombre().equals(username)));
        } catch (Exception exception) {
            log.error(exception.getMessage(), exception);
            result = Either.left(exception.getMessage());
        }
        return result;
    }

    @Override
    public Either<String, Usuario> getUsuarioByUsername(String username) {
        Either<String, Usuario> result;
        try {
            Usuario usuario = template.queryForObject(DaoConstants.SELECT_USER_BY_NAME, new BeanPropertyRowMapper<>(Usuario.class), username);
            result = Either.right(usuario);
        } catch (Exception exception) {
            log.error(exception.getMessage(), exception);
            result = Either.left(exception.getMessage());
        }
        return result;
    }

    @Override
    public Either<String, List<Usuario>> getAllUsers() {
        Either<String, List<Usuario>> result;
        try {
            List<Usuario> usuarios = template.query(DaoConstants.SELECT_ALL_USERS, new BeanPropertyRowMapper<>(Usuario.class));
            result = Either.right(usuarios);
        } catch (Exception exception) {
            log.error(exception.getMessage(), exception);
            result = Either.left(exception.getMessage());
        }
        return result;
    }

    @Override
    public Either<String, Usuario> getUsuarioById(int id) {
        Either<String, Usuario> result;
        try {
            Usuario usuario = template.queryForObject(DaoConstants.SELECT_USER_BY_ID, new BeanPropertyRowMapper<>(Usuario.class), id);
            result = Either.right(usuario);
        } catch (Exception exception) {
            log.error(exception.getMessage(), exception);
            result = Either.left(exception.getMessage());
        }
        return result;
    }
}
