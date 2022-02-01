package org.quevedo.client.dao.retrofit.interfaces;

import io.reactivex.rxjava3.core.Single;
import org.quevedo.client.dao.retrofit.ApiConsts;
import org.quevedo.sharedmodels.partido.Partido;
import org.quevedo.sharedmodels.partido.RegisterPartidoDTO;
import org.quevedo.sharedmodels.partido.UpdateResultPartidoDTO;
import retrofit2.http.Body;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.Query;

import java.util.List;

public interface PartidosApi {

    //Free for all methods
    @GET(ApiConsts.PATH_PARTIDOS)
    Single<List<Partido>> getAllPartidos();

    @GET(ApiConsts.PATH_PARTIDOS + ApiConsts.PATH_FILTER)
    Single<List<Partido>> getFilteredPartidos(@Query(ApiConsts.QUERY_PARAM_EQUIPO) String nombreEquipo, @Query(ApiConsts.QUERY_PARAM_JORNADA) String numeroJornada);

    //Admin part
    @POST(ApiConsts.PATH_PARTIDOS + ApiConsts.PATH_ADD)
    Single<Partido> addPartido(@Body RegisterPartidoDTO toRegister);

    @POST(ApiConsts.PATH_PARTIDOS + ApiConsts.PATH_RESULT)
    Single<Partido> registerResult(@Body UpdateResultPartidoDTO newResult);
}
