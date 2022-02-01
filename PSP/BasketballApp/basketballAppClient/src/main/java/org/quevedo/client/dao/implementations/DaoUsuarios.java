package org.quevedo.client.dao.implementations;

import com.google.gson.Gson;
import io.reactivex.rxjava3.core.Single;
import io.vavr.control.Either;
import org.quevedo.client.dao.retrofit.interfaces.UsersApi;
import org.quevedo.client.dao.utils.CacheAuth;
import org.quevedo.sharedmodels.usuario.LoginResultDTO;
import org.quevedo.sharedmodels.usuario.UsuarioGetDTO;
import org.quevedo.sharedmodels.usuario.UsuarioRegisterDTO;

import javax.inject.Inject;
import java.util.List;

public class DaoUsuarios extends DaoGeneric {
    private final UsersApi usersApi;
    private final Gson gson;
    private final CacheAuth cacheAuth;

    @Inject
    public DaoUsuarios(UsersApi usersApi, Gson gson, CacheAuth cacheAuth) {
        this.usersApi = usersApi;
        this.gson = gson;
        this.cacheAuth = cacheAuth;
    }

    public Single<Either<String, List<UsuarioGetDTO>>> getAllUsers() {
        return safeSingleApiCall(usersApi.getAllUsuarios(), gson);
    }

    public Single<Either<String, UsuarioGetDTO>> registrarUsuarioAdmin(UsuarioRegisterDTO userToRegister) {
        return safeSingleApiCall(usersApi.registrarUsuarioAdmin(userToRegister), gson);
    }

    public Single<Either<String, UsuarioGetDTO>> registrarUsuarioNormal(UsuarioRegisterDTO userToRegister) {
        return safeSingleApiCall(usersApi.registrarUsuarioNormal(userToRegister), gson);
    }

    public Single<Either<String, Void>> changePassword(String changePasswordEmail) {
        return safeSingleApiCall(usersApi.changePassword(changePasswordEmail), gson);
    }

    public Single<Either<String, LoginResultDTO>> doLogin(String correo, String password) {
        cacheAuth.setUser(correo);
        cacheAuth.setPass(password);
        return safeSingleApiCall(usersApi.doLogin(), gson);
    }
}
