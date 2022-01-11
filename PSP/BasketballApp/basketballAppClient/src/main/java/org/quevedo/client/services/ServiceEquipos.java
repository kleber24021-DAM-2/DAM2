package org.quevedo.client.services;

import io.vavr.control.Either;
import org.quevedo.client.dao.implementations.DaoEquipos;
import org.quevedo.sharedmodels.Equipo;

import javax.inject.Inject;
import java.util.List;

public class ServiceEquipos {
    private final DaoEquipos daoEquipos;

    @Inject
    public ServiceEquipos(DaoEquipos daoEquipos) {
        this.daoEquipos = daoEquipos;
    }

    public Either<String, List<Equipo>> getAllEquipos() {
        return daoEquipos.getAllEquipos();
    }

    public Either<String, Equipo> registerEquipo(String nombreEquipo) {
        return daoEquipos.registerEquipo(nombreEquipo);
    }

}
