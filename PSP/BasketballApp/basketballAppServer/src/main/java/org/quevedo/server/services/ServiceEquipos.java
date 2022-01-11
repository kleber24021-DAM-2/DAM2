package org.quevedo.server.services;

import io.vavr.control.Either;
import jakarta.inject.Inject;
import org.quevedo.server.dao.implementations.DaoEquipos;
import org.quevedo.server.ee.utils.EEConst;
import org.quevedo.sharedmodels.Equipo;
import org.quevedo.sharedmodels.servererror.ServerError;
import org.quevedo.sharedmodels.servererror.TipoError;

import java.util.List;

public class ServiceEquipos {
    private final DaoEquipos daoEquipos;

    @Inject
    public ServiceEquipos(DaoEquipos daoEquipos) {
        this.daoEquipos = daoEquipos;
    }

    public Either<ServerError, List<Equipo>> getAllEquipos() {
        return daoEquipos.getAllEquipos();
    }

    public Either<ServerError, Equipo> addEquipo(String nombreEquipo) {
        Either<ServerError, Equipo> result;
        if (nombreEquipo == null || nombreEquipo.isEmpty()) {
            result = Either.left(new ServerError(EEConst.MSG_EMPTY_QUERY_PARAM, TipoError.BAD_INPUT));
        } else {
            result = daoEquipos.addEquipo(nombreEquipo);
        }
        return result;
    }
}
