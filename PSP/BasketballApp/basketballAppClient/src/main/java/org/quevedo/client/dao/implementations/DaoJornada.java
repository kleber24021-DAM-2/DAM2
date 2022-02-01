package org.quevedo.client.dao.implementations;

import com.google.gson.Gson;
import io.reactivex.rxjava3.core.Single;
import io.vavr.control.Either;
import org.quevedo.client.dao.retrofit.interfaces.JornadaApi;
import org.quevedo.sharedmodels.Jornada;

import javax.inject.Inject;
import java.util.List;

public class DaoJornada extends DaoGeneric {
    private final JornadaApi jornadaApi;
    private final Gson gson;

    @Inject
    public DaoJornada(JornadaApi jornadaApi, Gson gson) {
        this.jornadaApi = jornadaApi;
        this.gson = gson;
    }

    public Single<Either<String, List<Jornada>>> getAllJornadas() {
        return safeSingleApiCall(jornadaApi.getAllJornadas(), gson);
    }

    public Single<Either<String, Jornada>> addJornada(String date) {
        return safeSingleApiCall(jornadaApi.addNewJornada(date), gson);
    }
}
