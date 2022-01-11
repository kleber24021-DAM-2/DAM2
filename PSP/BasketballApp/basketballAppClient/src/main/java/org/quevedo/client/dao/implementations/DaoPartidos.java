package org.quevedo.client.dao.implementations;

import com.google.gson.Gson;
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

    public Either<String, List<Partido>> getAllPartidos() {
        return safeApiCall(partidosApi.getAllPartidos(), gson);
    }

    public Either<String, List<Partido>> getFilteredPartidos(String nombrePartidos, String numeroJornada) {
        return safeApiCall(partidosApi.getFilteredPartidos(nombrePartidos, numeroJornada), gson);
    }

    //Funciones admin

    public Either<String, Partido> addPartido(RegisterPartidoDTO partidoToRegister) {
        return safeApiCall(partidosApi.addPartido(partidoToRegister), gson);
    }

    public Either<String, Partido> registerResult(UpdateResultPartidoDTO partidoResult) {
        return safeApiCall(partidosApi.registerResult(partidoResult), gson);
    }
}
