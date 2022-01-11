package org.quevedo.server.dao.implementations;

import io.vavr.control.Either;
import jakarta.inject.Inject;
import lombok.extern.log4j.Log4j2;
import org.quevedo.server.dao.utils.DaoConstants;
import org.quevedo.sharedmodels.Equipo;
import org.quevedo.sharedmodels.Jornada;
import org.quevedo.sharedmodels.partido.Partido;
import org.quevedo.sharedmodels.partido.RegisterPartidoDTO;
import org.quevedo.sharedmodels.partido.UpdateResultPartidoDTO;
import org.quevedo.sharedmodels.servererror.ServerError;
import org.quevedo.sharedmodels.servererror.TipoError;
import org.springframework.dao.DataAccessException;
import org.springframework.dao.DataIntegrityViolationException;
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
import java.util.stream.Collectors;

@Log4j2
public class DaoPartidos {
    private final JdbcTemplate template;
    private final PartidoMapper partidoMapper;

    @Inject
    public DaoPartidos(JdbcTemplate template, PartidoMapper partidoMapper) {
        this.template = template;
        this.partidoMapper = partidoMapper;
    }

    private Either<ServerError, Partido> getPartidoById(int id) {
        Either<ServerError, Partido> result;
        try {
            result = Either.right(template.queryForObject(DaoConstants.SELECT_PARTIDO_BY_ID, partidoMapper, id));
        } catch (DataAccessException dataAccessException) {
            log.error(dataAccessException.getMessage(), dataAccessException);
            result = Either.left(new ServerError(DaoConstants.MSG_DB_FAILED, TipoError.DB_ERROR));
        }
        return result;
    }

    public Either<ServerError, List<Partido>> getAllPartidos() {
        Either<ServerError, List<Partido>> result;
        try {
            result = Either.right(template.query(DaoConstants.SELECT_ALL_PARTIDOS, partidoMapper));
        } catch (DataAccessException dataAccessException) {
            log.error(dataAccessException.getMessage(), dataAccessException);
            result = Either.left(new ServerError(DaoConstants.MSG_DB_FAILED, TipoError.DB_ERROR));
        }
        return result;
    }

    public Either<ServerError, List<Partido>> getPartidosByEquipoAndJornada(String nombreEquipo, int idJornada) {
        Either<ServerError, List<Partido>> result;
        List<Partido> queryResult;
        String queryNombreEquipo = DaoConstants.PERCENTAGE + nombreEquipo + DaoConstants.PERCENTAGE;
        try {
            queryResult = template.query(DaoConstants.SELECT_PARTIDOS_BY_EQUIPO, partidoMapper, queryNombreEquipo, queryNombreEquipo);
            result = Either.right(queryResult.stream()
                    .filter(partido -> {
                        if (idJornada == -1) {
                            return true;
                        } else {
                            return partido.getJornadaPartido().getIdJornada() == idJornada;
                        }
                    }).collect(Collectors.toList()));
        } catch (DataAccessException dataAccessException) {
            log.error(dataAccessException.getMessage(), dataAccessException);
            result = Either.left(new ServerError(DaoConstants.MSG_DB_FAILED, TipoError.DB_ERROR));
        }
        return result;
    }

    public Either<ServerError, Partido> addPartido(RegisterPartidoDTO partidoToRegister) {
        Either<ServerError, Partido> result;
        try {
            KeyHolder keyHolder = new GeneratedKeyHolder();
            template.update(connection -> {
                PreparedStatement ps = connection.prepareStatement(
                        DaoConstants.INSERT_PARTIDO, Statement.RETURN_GENERATED_KEYS);
                ps.setInt(1, partidoToRegister.getIdJornada());
                ps.setInt(2, partidoToRegister.getIdEquipoLocal());
                ps.setInt(3, partidoToRegister.getIdEquipoVisitante());
                ps.setInt(4, partidoToRegister.getResultadoLocal());
                ps.setInt(5, partidoToRegister.getResultadoVisitante());
                return ps;
            }, keyHolder);
            result = getPartidoById(Objects.requireNonNull(keyHolder.getKey()).intValue());
        } catch (DataIntegrityViolationException integrityViolationException) {
            result = Either.left(new ServerError(DaoConstants.MSG_INTEGRITY_VIOLATION + integrityViolationException.getMessage(), TipoError.BAD_INPUT));
        } catch (DataAccessException dataAccessException) {
            log.error(dataAccessException.getMessage(), dataAccessException);
            result = Either.left(new ServerError(DaoConstants.MSG_DB_FAILED, TipoError.DB_ERROR));
        }
        return result;
    }

    public Either<ServerError, Partido> setPartidoResult(UpdateResultPartidoDTO results) {
        Either<ServerError, Partido> result;
        try {
            int rowsAffected = template.update(DaoConstants.UPDATE_PARTIDO_RESULT, results.getResultadoLocal(), results.getResultadoVisitante(), results.getIdPartido());
            if (rowsAffected == 0) {
                result = Either.left(new ServerError(DaoConstants.MSG_NO_VALID_ID, TipoError.BAD_INPUT));
            } else {
                result = getPartidoById(results.getIdPartido());
            }
        } catch (DataAccessException dataAccessException) {
            log.error(dataAccessException.getMessage(), dataAccessException);
            result = Either.left(new ServerError(DaoConstants.MSG_DB_FAILED, TipoError.DB_ERROR));
        }
        return result;
    }

    private static final class PartidoMapper implements RowMapper<Partido> {

        @Override
        public Partido mapRow(ResultSet rs, int i) throws SQLException {
            return Partido.builder()
                    .idPartido(rs.getInt(DaoConstants.COL_ID_PARTIDO))
                    .jornadaPartido(new Jornada(rs.getInt(DaoConstants.COL_ID_JORNADA), LocalDate.parse(rs.getString(DaoConstants.COL_FECHA), DaoConstants.DATE_FORMATTER)))
                    .equipoLocal(new Equipo(rs.getInt(DaoConstants.COL_ID_LOCAL), rs.getString(DaoConstants.COL_E_NOMBRE)))
                    .equipoVisitante(new Equipo(rs.getInt(DaoConstants.COL_ID_VISITANTE), rs.getString(DaoConstants.COL_E_2_NOMBRE)))
                    .resultadoLocal(rs.getInt(DaoConstants.COL_RESULTADO_LOCAL))
                    .resultadoVisitante(rs.getInt(DaoConstants.COL_RESULTADO_VISITANTE))
                    .build();
        }
    }
}