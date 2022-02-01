package org.quevedo.client.dao.retrofit.interfaces;

import io.reactivex.rxjava3.core.Single;
import org.quevedo.client.dao.retrofit.ApiConsts;
import org.quevedo.sharedmodels.usuario.LoginResultDTO;
import org.quevedo.sharedmodels.usuario.UsuarioGetDTO;
import org.quevedo.sharedmodels.usuario.UsuarioRegisterDTO;
import retrofit2.http.Body;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.Query;

import java.util.List;

public interface UsersApi {

    @GET(ApiConsts.PATH_USERS)
    Single<List<UsuarioGetDTO>> getAllUsuarios();

    @POST(ApiConsts.PATH_USERS + ApiConsts.PATH_SLASH_ADMIN)
    Single<UsuarioGetDTO> registrarUsuarioAdmin(@Body UsuarioRegisterDTO toRegister);

    @POST(ApiConsts.PATH_USERS)
    Single<UsuarioGetDTO> registrarUsuarioNormal(@Body UsuarioRegisterDTO toRegister);

    @POST(ApiConsts.PATH_USERS + ApiConsts.PATH_PASSWORDCHANGE)
    Single<Void> changePassword(@Query(ApiConsts.PARAM_CORREO) String accountEmail);

    @GET(ApiConsts.PATH_LOGOUT)
    Single<Void> doLogout();

    @GET(ApiConsts.PATH_USERS + ApiConsts.PATH_LOGIN)
    Single<LoginResultDTO> doLogin();
}
