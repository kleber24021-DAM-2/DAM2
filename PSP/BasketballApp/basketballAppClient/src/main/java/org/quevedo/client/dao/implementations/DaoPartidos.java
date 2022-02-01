package org.quevedo.client.dao.implementations;

import com.google.gson.Gson;
import io.reactivex.rxjava3.core.Single;
import io.vavr.control.Either;
import org.quevedo.client.dao.retrofit.interfaces.PartidosApi;
import org.quevedo.sharedmodels.partido.Partido;
import org.quevedo.sharedmodels.partido.RegisterPartidoDTO;
import org.quevedo.sharedmodels.partido.UpdateResultPartidoDTO;

import javax.inject.Inject;
import java.util.List;

public class DaoPartidos extends DaoGeneric {
    private final PartidosApi partidosApi;
    private final Gson gson;

    @Inject
    public DaoPartidos(PartidosApi partidosApi, Gson gson) {
        this.partidosApi = partidosApi;
        this.gson = gson;
    }

    public Single<Either<String, List<Partido>>> getAllPartidos() {
        return safeSingleApiCall(partidosApi.getAllPartidos(), gson);
    }

    public Single<Either<String, List<Partido>>> getFilteredPartidos(String nombrePartidos, String numeroJornada) {
        return safeSingleApiCall(partidosApi.getFilteredPartidos(nombrePartidos, numeroJornada), gson);
    }

    //Funciones admin

    public Single<Either<String, Partido>> addPartido(RegisterPartidoDTO partidoToRegister) {
        return safeSingleApiCall(partidosApi.addPartido(partidoToRegister), gson);
    }

    public Single<Either<String, Partido>> registerResult(UpdateResultPartidoDTO partidoResult) {
        return safeSingleApiCall(partidosApi.registerResult(partidoResult), gson);
    }
}
