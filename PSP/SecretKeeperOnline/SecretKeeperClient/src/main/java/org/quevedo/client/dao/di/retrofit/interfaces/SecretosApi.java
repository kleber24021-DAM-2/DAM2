package org.quevedo.client.dao.di.retrofit.interfaces;

import org.quevedo.common.models.Secreto;
import org.quevedo.common.models.SecretoDTO;
import org.quevedo.common.models.UserPassword;
import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.Query;

import java.util.List;

public interface SecretosApi {

    @GET(ServerPaths.SECRET)
    Call<List<Secreto>> getAllSecretosByUsername(@Query(ServerPaths.USERNAME) String username);

    @POST(ServerPaths.SECRET)
    Call<SecretoDTO> createSecret(@Body SecretoDTO toSave);

    @GET(ServerPaths.SECRET_PASSWORD)
    Call<UserPassword> getPasswordBySecretUserId(@Query(ServerPaths.SECRET_ID) int SecretId, @Query(ServerPaths.QUERY_PARAM_USER_ID) int userId);
}
