package org.quevedo.client.dao.retrofit.interfaces;

import org.quevedo.client.dao.retrofit.ApiConsts;
import org.quevedo.sharedmodels.usuario.LoginResultDTO;
import org.quevedo.sharedmodels.usuario.UsuarioGetDTO;
import org.quevedo.sharedmodels.usuario.UsuarioRegisterDTO;
import retrofit2.Call;
import retrofit2.http.*;

import java.util.List;

public interface UsersApi {

    @GET(ApiConsts.PATH_USERS)
    Call<List<UsuarioGetDTO>> getAllUsuarios();

    @POST(ApiConsts.PATH_USERS + ApiConsts.PATH_SLASH_ADMIN)
    Call<UsuarioGetDTO> registrarUsuarioAdmin(@Body UsuarioRegisterDTO toRegister);

    @POST(ApiConsts.PATH_USERS)
    Call<UsuarioGetDTO> registrarUsuarioNormal(@Body UsuarioRegisterDTO toRegister);

    @POST(ApiConsts.PATH_USERS + ApiConsts.PATH_PASSWORDCHANGE)
    Call<Void> changePassword(@Query(ApiConsts.PARAM_CORREO) String accountEmail);

    @GET(ApiConsts.PATH_LOGOUT)
    Call<Void> doLogout();

    @FormUrlEncoded
    @POST(ApiConsts.PATH_LOGIN)
    Call<LoginResultDTO> doLogin(@Field(ApiConsts.PARAM_CORREO) String correo, @Field(ApiConsts.PARAM_PASS) String password);
}
