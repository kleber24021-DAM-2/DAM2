package org.quevedo.client.dao.implementations;

import com.google.gson.Gson;
import io.reactivex.rxjava3.core.Single;
import io.vavr.control.Either;
import org.quevedo.client.dao.retrofit.interfaces.EquipoApi;
import org.quevedo.sharedmodels.Equipo;

import javax.inject.Inject;
import java.util.List;

public class DaoEquipos extends DaoGeneric {
    private final EquipoApi equipoApi;
    private final Gson gson;

    @Inject
    public DaoEquipos(EquipoApi equipoApi, Gson gson) {
        this.equipoApi = equipoApi;
        this.gson = gson;
    }

    public Single<Either<String, List<Equipo>>> getAllEquipos() {
        return safeSingleApiCall(equipoApi.getAllEquipos(), gson);
    }

    public Single<Either<String , Equipo>> registerEquipo(String nombreEquipo) {
        return safeSingleApiCall(equipoApi.addEquipo(nombreEquipo), gson);
    }
}
