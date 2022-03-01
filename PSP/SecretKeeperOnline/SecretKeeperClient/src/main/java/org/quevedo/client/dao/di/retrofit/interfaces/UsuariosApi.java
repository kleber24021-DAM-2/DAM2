package org.quevedo.client.dao.di.retrofit.interfaces;

import org.quevedo.common.models.Usuario;
import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.Path;
import retrofit2.http.Query;

import java.util.List;

public interface UsuariosApi {

    @GET(ServerPaths.USER_CHECK)
    Call<Boolean> checkIfUserExists(@Query(ServerPaths.USERNAME) String username);

    @POST(ServerPaths.USER)
    Call<Usuario> registerUser(@Query(ServerPaths.USERNAME) String username, @Query(ServerPaths.PUBLICKEY) String publicKey);

    @GET(ServerPaths.USER_GET)
    Call<Usuario> getUserByUsername(@Query(ServerPaths.USERNAME) String username);

    @GET(ServerPaths.USER_ID)
    Call<Usuario> getUserById(@Path(ServerPaths.ID) int id);

    @GET(ServerPaths.USER)
    Call<List<Usuario>> getAllUsers();
}
