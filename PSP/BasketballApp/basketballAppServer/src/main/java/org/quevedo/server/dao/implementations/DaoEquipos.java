package org.quevedo.server.dao.implementations;

import io.vavr.control.Either;
import jakarta.inject.Inject;
import lombok.extern.log4j.Log4j2;
import org.quevedo.sharedmodels.servererror.ServerError;
import org.quevedo.sharedmodels.servererror.TipoError;
import org.quevedo.server.dao.utils.DaoConstants;
import org.quevedo.sharedmodels.Equipo;
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
public class DaoEquipos {
    private final JdbcTemplate template;
    @Inject
    public DaoEquipos(JdbcTemplate template){
        this.template = template;
    }

    public Either<ServerError, List<Equipo>> getAllEquipos(){
        Either<ServerError, List<Equipo>> result;
        try {
            result = Either.right(template.query(DaoConstants.SELECT_ALL_EQUIPOS, new BeanPropertyRowMapper<>(Equipo.class)));
        }catch (DataAccessException dataAccessException){
            log.error(dataAccessException.getMessage(), dataAccessException);
            result = Either.left(new ServerError(DaoConstants.MSG_DB_FAILED, TipoError.DB_ERROR));
        }
        return result;
    }

    public Either<ServerError, Equipo> addEquipo(String nombreEquipo){
        Either<ServerError, Equipo> result;
        try {
            KeyHolder keyHolder = new GeneratedKeyHolder();
            template.update(connection -> {
                PreparedStatement ps = connection.prepareStatement(
                        DaoConstants.INSERT_EQUIPO, Statement.RETURN_GENERATED_KEYS);
                ps.setString(1, nombreEquipo);
                return ps;
            }, keyHolder);
            result = Either.right(new Equipo(Objects.requireNonNull(keyHolder.getKey()).intValue(), nombreEquipo));
        }catch (DataAccessException dataAccessException){
            log.error(dataAccessException.getMessage(), dataAccessException);
            result = Either.left(new ServerError(DaoConstants.MSG_DB_FAILED, TipoError.DB_ERROR));
        }
        return result;
    }
}
