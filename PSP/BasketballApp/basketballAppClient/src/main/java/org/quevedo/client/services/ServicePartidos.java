package org.quevedo.client.services;

import io.vavr.control.Either;
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

    public Either<String, List<Partido>> getAllPartidos() {
        return daoPartidos.getAllPartidos();
    }

    public Either<String, List<Partido>> getFilteredPartidos(String nombreEquipo, Integer numeroJornada) {
        String numJornada;
        if (numeroJornada == null) {
            numJornada = "";
        } else {
            numJornada = numeroJornada.toString();
        }
        return daoPartidos.getFilteredPartidos(nombreEquipo, numJornada);
    }

    public Either<String, Partido> addPartido(RegisterPartidoDTO partidoToRegister) {
        return daoPartidos.addPartido(partidoToRegister);
    }

    public Either<String, Partido> registerResult(UpdateResultPartidoDTO partidoResult) {
        return daoPartidos.registerResult(partidoResult);
    }
}
