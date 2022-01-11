package org.quevedo.client.dao.retrofit.interfaces;

import org.quevedo.client.dao.retrofit.ApiConsts;
import org.quevedo.sharedmodels.Jornada;
import retrofit2.Call;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.GET;
import retrofit2.http.POST;

import java.util.List;

public interface JornadaApi {

    @GET(ApiConsts.PATH_ADMIN + ApiConsts.PATH_JORNADAS)
    Call<List<Jornada>> getAllJornadas();

    @FormUrlEncoded
    @POST(ApiConsts.PATH_ADMIN + ApiConsts.PATH_JORNADAS)
    Call<Jornada> addNewJornada(@Field(ApiConsts.PARAM_FECHA) String fecha);
}
