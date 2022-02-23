package org.quevedo.client.dao.di.retrofit.interfaces;

import org.quevedo.common.models.Usuario;
import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.Query;

public interface UsuariosApi {
    @GET("user/check")
    Call<Boolean> checkIfUserExists(@Query("username") String username);

    @POST("user")
    Call<Usuario> registerUser(@Query("username") String username, @Query("publickey") String publicKey);
}
