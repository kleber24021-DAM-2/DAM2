package org.quevedo.client.dao.retrofit.interfaces;

import io.reactivex.rxjava3.core.Single;
import org.quevedo.client.dao.retrofit.ApiConsts;
import org.quevedo.sharedmodels.Jornada;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.GET;
import retrofit2.http.POST;

import java.util.List;

public interface JornadaApi {

    @GET(ApiConsts.PATH_ADMIN + ApiConsts.PATH_JORNADAS)
    Single<List<Jornada>> getAllJornadas();

    @FormUrlEncoded
    @POST(ApiConsts.PATH_ADMIN + ApiConsts.PATH_JORNADAS)
    Single<Jornada> addNewJornada(@Field(ApiConsts.PARAM_FECHA) String fecha);
}
