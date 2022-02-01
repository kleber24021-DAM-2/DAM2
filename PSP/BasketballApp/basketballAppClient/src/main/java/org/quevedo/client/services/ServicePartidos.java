package org.quevedo.client.services;

import io.reactivex.rxjava3.core.Single;
import io.vavr.control.Either;
import org.pdfsam.rxjavafx.schedulers.JavaFxScheduler;
import org.quevedo.client.dao.implementations.DaoPartidos;
import org.quevedo.sharedmodels.partido.Partido;
import org.quevedo.sharedmodels.partido.RegisterPartidoDTO;
import org.quevedo.sharedmodels.partido.UpdateResultPartidoDTO;

import javax.inject.Inject;
import java.util.List;

public class ServicePartidos {
    private final DaoPartidos daoPartidos;

    @Inject
    public ServicePartidos(DaoPartidos daoPartidos) {
        this.daoPartidos = daoPartidos;
    }

    public Single<Either<String, List<Partido>>> getAllPartidos() {
        return daoPartidos.getAllPartidos().observeOn(JavaFxScheduler.platform());
    }

    public Single<Either<String, List<Partido>>> getFilteredPartidos(String nombreEquipo, Integer numeroJornada) {
        String numJornada;
        if (numeroJornada == null) {
            numJornada = ServiceConstants.EMPTY_STRING;
        } else {
            numJornada = numeroJornada.toString();
        }
        if (nombreEquipo == null){
            nombreEquipo = ServiceConstants.EMPTY_STRING;
        }
        return daoPartidos.getFilteredPartidos(nombreEquipo, numJornada).observeOn(JavaFxScheduler.platform());
    }

    public Single<Either<String, Partido>> addPartido(RegisterPartidoDTO partidoToRegister) {
        return daoPartidos.addPartido(partidoToRegister).observeOn(JavaFxScheduler.platform());
    }

    public Single<Either<String, Partido>> registerResult(UpdateResultPartidoDTO partidoResult) {
        return daoPartidos.registerResult(partidoResult).observeOn(JavaFxScheduler.platform());
    }
}
