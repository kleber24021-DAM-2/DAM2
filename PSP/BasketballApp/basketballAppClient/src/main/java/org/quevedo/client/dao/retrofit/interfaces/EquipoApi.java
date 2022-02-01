package org.quevedo.client.dao.retrofit.interfaces;

import io.reactivex.rxjava3.core.Single;
import org.quevedo.client.dao.retrofit.ApiConsts;
import org.quevedo.sharedmodels.Equipo;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.Query;

import java.util.List;

public interface EquipoApi {

    @GET(ApiConsts.PATH_ADMIN + ApiConsts.PATH_EQUIPOS)
    Single<List<Equipo>> getAllEquipos();

    @POST(ApiConsts.PATH_ADMIN + ApiConsts.PATH_EQUIPOS)
    Single<Equipo> addEquipo(@Query(ApiConsts.PARAM_NOMBRE) String nombreEquipo);
}
