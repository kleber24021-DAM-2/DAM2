package org.quevedo.server.services;

import io.vavr.control.Either;
import jakarta.inject.Inject;
import org.quevedo.server.dao.implementations.DaoJornada;
import org.quevedo.server.dao.utils.DaoConstants;
import org.quevedo.server.ee.utils.EEConst;
import org.quevedo.sharedmodels.Jornada;
import org.quevedo.sharedmodels.servererror.ServerError;
import org.quevedo.sharedmodels.servererror.TipoError;

import java.time.LocalDate;
import java.time.format.DateTimeParseException;
import java.util.List;

public class ServiceJornada {
    private final DaoJornada daoJornada;

    @Inject
    public ServiceJornada(DaoJornada daoJornada) {
        this.daoJornada = daoJornada;
    }

    public Either<ServerError, List<Jornada>> getAllJornadas() {
        return daoJornada.getAllJornadas();
    }

    public Either<ServerError, Jornada> addJornada(String fecha) {
        LocalDate localDate;
        Either<ServerError, Jornada> result;
        try {
            localDate = LocalDate.parse(fecha, DaoConstants.DATE_FORMATTER);
            result = daoJornada.addJornada(localDate);
        } catch (DateTimeParseException parseException) {
            result = Either.left(new ServerError(EEConst.MSG_DATE_ERROR, TipoError.BAD_INPUT));
        }
        return result;
    }
}
