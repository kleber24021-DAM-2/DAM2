package org.quevedo.server.services;

import io.vavr.control.Either;
import jakarta.inject.Inject;
import org.quevedo.server.dao.implementations.DaoPartidos;
import org.quevedo.server.ee.utils.EEConst;
import org.quevedo.sharedmodels.partido.Partido;
import org.quevedo.sharedmodels.partido.RegisterPartidoDTO;
import org.quevedo.sharedmodels.partido.UpdateResultPartidoDTO;
import org.quevedo.sharedmodels.servererror.ServerError;
import org.quevedo.sharedmodels.servererror.TipoError;

import java.util.List;

public class ServicesPartidos {
    private final DaoPartidos daoPartidos;

    @Inject
    public ServicesPartidos(DaoPartidos daoPartidos) {
        this.daoPartidos = daoPartidos;
    }

    public Either<ServerError, List<Partido>> getAllPartidos() {
        return daoPartidos.getAllPartidos();
    }

    public Either<ServerError, List<Partido>> getPartidosByEquipoAndIdJornada(String nombreEquipo, Integer idJornada) {
        Either<ServerError, List<Partido>> result;
        if (nombreEquipo.isEmpty() && idJornada == null) {
            result = Either.left(new ServerError(EEConst.MSG_EMPTY_QUERY_PARAM, TipoError.BAD_INPUT));
        } else {
            if (idJornada == null) {
                idJornada = -1;
            }
            result = daoPartidos.getPartidosByEquipoAndJornada(nombreEquipo, idJornada);
        }
        return result;
    }

    public Either<ServerError, Partido> addPartido(RegisterPartidoDTO toRegister) {
        return daoPartidos.addPartido(toRegister);
    }

    public Either<ServerError, Partido> registerPartidoResult(UpdateResultPartidoDTO results) {
        return daoPartidos.setPartidoResult(results);
    }
}
