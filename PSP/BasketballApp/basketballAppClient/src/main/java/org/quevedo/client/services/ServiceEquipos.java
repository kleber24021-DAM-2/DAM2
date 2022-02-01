package org.quevedo.client.services;

import io.reactivex.rxjava3.core.Single;
import io.vavr.control.Either;
import org.pdfsam.rxjavafx.schedulers.JavaFxScheduler;
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

    public Single<Either<String, List<Equipo>>> getAllEquipos() {
        return daoEquipos.getAllEquipos().observeOn(JavaFxScheduler.platform());
    }

    public Single<Either<String, Equipo>> registerEquipo(String nombreEquipo) {
        return daoEquipos.registerEquipo(nombreEquipo).observeOn(JavaFxScheduler.platform());
    }

}
