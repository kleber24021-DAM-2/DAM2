package org.quevedo.server.dao.implementations;

import io.vavr.control.Either;
import jakarta.inject.Inject;
import lombok.extern.log4j.Log4j2;
import org.quevedo.server.dao.model.Usuario;
import org.quevedo.server.dao.utils.DaoConstants;
import org.quevedo.sharedmodels.servererror.ServerError;
import org.quevedo.sharedmodels.servererror.TipoError;
import org.quevedo.sharedmodels.usuario.TipoUsuario;
import org.quevedo.sharedmodels.usuario.UsuarioGetDTO;
import org.springframework.dao.DataAccessException;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.dao.IncorrectResultSizeDataAccessException;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.jdbc.support.KeyHolder;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

@Log4j2
public class DaoUsuarios {
    private final JdbcTemplate jdbcTemplate;
    private final UsuarioMapper usuarioMapper;

    @Inject
    public DaoUsuarios(JdbcTemplate jdbcTemplate, UsuarioMapper usuarioMapper) {
        this.jdbcTemplate = jdbcTemplate;
        this.usuarioMapper = usuarioMapper;
    }

    public Either<String, List<UsuarioGetDTO>> getAllUsuarios() {
        Either<String, List<UsuarioGetDTO>> result;
        try {
            List<UsuarioGetDTO> listaUsuario = jdbcTemplate.query(DaoConstants.SELECT_FROM_USUARIOS, usuarioMapper)
                    .stream().map(usuario -> new UsuarioGetDTO(usuario.getId(), usuario.getCorreo(), usuario.getTipoUsuario()))
                    .collect(Collectors.toList());
            result = Either.right(listaUsuario);
        } catch (DataAccessException dataAccessException) {
            log.error(dataAccessException.getMessage(), dataAccessException);
            result = Either.left(dataAccessException.getMessage());
        }
        return result;
    }

    public Either<String, Usuario> getUsuarioByCodActivacion(String codActivacion) {
        Either<String, Usuario> result;
        try {
            result = Either.right(jdbcTemplate.queryForObject(DaoConstants.SELECT_FROM_USUARIOS_BY_COD, usuarioMapper, codActivacion));
        } catch (EmptyResultDataAccessException emptyResultEx) {
            result = Either.left(DaoConstants.MSG_USER_NOT_FOUND);
        } catch (DataAccessException dataAccessException) {
            log.error(dataAccessException.getMessage(), dataAccessException);
            result = Either.left(DaoConstants.MSG_DB_FAILED);
        }
        return result;
    }

    public Either<ServerError, UsuarioGetDTO> crearUsuario(Usuario nuevoUsuario) {
        Either<ServerError, UsuarioGetDTO> result;

        try {
            KeyHolder keyHolder = new GeneratedKeyHolder();
            jdbcTemplate.update(connection -> {
                PreparedStatement ps = connection.prepareStatement(
                        DaoConstants.CREATE_USER, Statement.RETURN_GENERATED_KEYS);
                ps.setString(1, nuevoUsuario.getCorreo());
                ps.setString(2, nuevoUsuario.getPassword());
                ps.setString(3, nuevoUsuario.getCodigoActivacion());
                ps.setBoolean(4, nuevoUsuario.isActivo());
                ps.setString(5, nuevoUsuario.getFechaActivacion().format(DaoConstants.DATE_TIME_FORMATTER));
                ps.setInt(6, nuevoUsuario.getTipoUsuario().getNum());
                return ps;
            }, keyHolder);
            nuevoUsuario.setId(Objects.requireNonNull(keyHolder.getKey()).intValue());
            result = Either.right(new UsuarioGetDTO(nuevoUsuario.getId(), nuevoUsuario.getCorreo(), nuevoUsuario.getTipoUsuario()));
        } catch (DataAccessException dataException) {
            log.error(dataException.getMessage(), dataException);
            result = Either.left(new ServerError(dataException.getMessage(), TipoError.DB_ERROR));
        }
        return result;
    }

    public int checkUserMail(String correo) {
        Integer result = jdbcTemplate.queryForObject(DaoConstants.COUNT_USER_BY_CORREO, Integer.class, correo);
        return Objects.requireNonNullElse(result, -1);
    }

    public boolean activateUsuario(Usuario toActivateUser) {
        boolean result;
        try {
            jdbcTemplate.update(DaoConstants.UPDATE_USUARIOS_ACTIVATE_BY_ID, toActivateUser.getId());
            result = true;
        } catch (DataAccessException dataAccessException) {
            log.error(dataAccessException.getMessage(), dataAccessException);
            result = false;
        }
        return result;
    }

    public boolean deleteUsuario(Usuario toActivateUser) {
        boolean toReturn = false;
        try {
            jdbcTemplate.update(DaoConstants.DELETE_USUARIOS_BY_ID, toActivateUser.getId());
            toReturn = true;
        } catch (DataAccessException dataAccessException) {
            log.error(dataAccessException.getMessage(), dataAccessException);
        }
        return toReturn;
    }

    public boolean changePassword(Usuario actualUser, String hashedPassword) {
        boolean toReturn = false;
        try {
            jdbcTemplate.update(DaoConstants.UPDATE_USUARIOS_CHANGE_PASSWORD_BY_ID, hashedPassword, actualUser.getId());
            toReturn = true;
        } catch (DataAccessException dataAccessException) {
            log.error(dataAccessException.getMessage(), dataAccessException);
        }
        return toReturn;
    }

    public boolean startPasswordChange(String newRandomCode, String changePasswordEmail, LocalDateTime validTime) {
        boolean toReturn = false;
        try {
            jdbcTemplate.update(DaoConstants.UPDATE_USUARIOS_START_PASSWORD_CHANGE, newRandomCode, validTime, changePasswordEmail);
            toReturn = true;
        } catch (DataAccessException dataAccessException) {
            log.error(dataAccessException.getMessage(), dataAccessException);
        }
        return toReturn;
    }

    public Either<String, Usuario> getUsuarioByCorreo(String correo) {
        Either<String, Usuario> result;
        try {
            result = Either.right(jdbcTemplate.queryForObject(DaoConstants.SELECT_USUARIO_BY_CORREO, usuarioMapper, correo));
        } catch (IncorrectResultSizeDataAccessException incorrectSizeEx) {
            result = Either.right(null);
        } catch (DataAccessException dataAccessException) {
            log.error(dataAccessException.getMessage(), dataAccessException);
            result = Either.left(DaoConstants.MSG_DB_FAILED);
        }
        return result;
    }

    public Either<ServerError, TipoUsuario> getUserLevelByMail(String email) {
        Either<ServerError, TipoUsuario> result;
        try {
            result = Either.right(TipoUsuario.get(jdbcTemplate.queryForObject("select tipoUsuario from usuarios where correo = ?", Integer.class, email)));
        } catch (DataAccessException dataAccessException) {
            log.error(dataAccessException.getMessage(), dataAccessException);
            result = Either.left(new ServerError(DaoConstants.MSG_DB_FAILED, TipoError.DB_ERROR));
        }
        return result;
    }

    private static final class UsuarioMapper implements RowMapper<Usuario> {

        @Override
        public Usuario mapRow(ResultSet resultSet, int i) throws SQLException {
            return new Usuario(
                    resultSet.getInt(DaoConstants.COL_ID),
                    resultSet.getString(DaoConstants.COL_CORREO),
                    resultSet.getString(DaoConstants.COL_HASHED_PASSWORD),
                    resultSet.getString(DaoConstants.COL_COD_ACTIVACION),
                    resultSet.getBoolean(DaoConstants.COL_IS_ACTIVO),
                    LocalDateTime.parse(resultSet.getString(DaoConstants.COL_FECHA_LIMITE_ACTIVACION), DaoConstants.DATE_TIME_FORMATTER),
                    TipoUsuario.get(resultSet.getInt(DaoConstants.COL_TIPO_USUARIO))
            );
        }
    }
}
