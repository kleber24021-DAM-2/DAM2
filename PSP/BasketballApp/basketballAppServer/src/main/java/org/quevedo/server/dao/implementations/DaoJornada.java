package org.quevedo.server.dao.implementations;

import io.vavr.control.Either;
import jakarta.inject.Inject;
import lombok.extern.log4j.Log4j2;
import org.quevedo.server.dao.utils.DaoConstants;
import org.quevedo.sharedmodels.Jornada;
import org.quevedo.sharedmodels.servererror.ServerError;
import org.quevedo.sharedmodels.servererror.TipoError;
import org.springframework.dao.DataAccessException;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.jdbc.support.KeyHolder;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.time.LocalDate;
import java.util.List;
import java.util.Objects;

@Log4j2
public class DaoJornada {
    private final JdbcTemplate template;
    private final JornadaMapper jornadaMapper;

    @Inject
    public DaoJornada(JdbcTemplate template, JornadaMapper jornadaMapper) {
        this.template = template;
        this.jornadaMapper = jornadaMapper;
    }

    public Either<ServerError, List<Jornada>> getAllJornadas() {
        Either<ServerError, List<Jornada>> result;
        try {
            result = Either.right(template.query(DaoConstants.SELECT_ALL_JORNADAS, jornadaMapper));
        } catch (DataAccessException dataAccessException) {
            log.error(dataAccessException.getMessage(), dataAccessException);
            result = Either.left(new ServerError(DaoConstants.MSG_DB_FAILED, TipoError.DB_ERROR));
        }
        return result;
    }

    public Either<ServerError, Jornada> addJornada(LocalDate fechaJornada) {
        Either<ServerError, Jornada> result;
        try {
            KeyHolder keyHolder = new GeneratedKeyHolder();
            template.update(connection -> {
                PreparedStatement ps = connection.prepareStatement(DaoConstants.INSERT_JORNADA, Statement.RETURN_GENERATED_KEYS);
                ps.setString(1, fechaJornada.format(DaoConstants.DATE_FORMATTER));
                return ps;
            }, keyHolder);
            result = Either.right(new Jornada(Objects.requireNonNull(keyHolder.getKey()).intValue(), fechaJornada));
        } catch (DataAccessException dataAccessException) {
            log.error(dataAccessException.getMessage(), dataAccessException);
            result = Either.left(new ServerError(DaoConstants.MSG_DB_FAILED, TipoError.DB_ERROR));
        }
        return result;
    }

    private static class JornadaMapper implements RowMapper<Jornada> {

        @Override
        public Jornada mapRow(ResultSet rs, int i) throws SQLException {
            return new Jornada(
                    rs.getInt(DaoConstants.COL_ID_JORNADA),
                    LocalDate.parse(rs.getString(DaoConstants.COL_FECHA), DaoConstants.DATE_FORMATTER)
            );
        }
    }
}
