package org.quevedo.client.dao.implementations;

import com.google.gson.Gson;
import io.vavr.control.Either;
import org.quevedo.client.dao.retrofit.interfaces.UsersApi;
import org.quevedo.sharedmodels.usuario.LoginResultDTO;
import org.quevedo.sharedmodels.usuario.UsuarioGetDTO;
import org.quevedo.sharedmodels.usuario.UsuarioRegisterDTO;

import javax.inject.Inject;
import java.util.List;

public class DaoUsuarios extends DaoGeneric {
    private final UsersApi usersApi;
    private final Gson gson;

    @Inject
    public DaoUsuarios(UsersApi usersApi, Gson gson) {
        this.usersApi = usersApi;
        this.gson = gson;
    }

    public Either<String, List<UsuarioGetDTO>> getAllUsers() {
        return safeApiCall(usersApi.getAllUsuarios(), gson);
    }

    public Either<String, UsuarioGetDTO> registrarUsuarioAdmin(UsuarioRegisterDTO userToRegister) {
        return safeApiCall(usersApi.registrarUsuarioAdmin(userToRegister), gson);
    }

    public Either<String, UsuarioGetDTO> registrarUsuarioNormal(UsuarioRegisterDTO userToRegister) {
        return safeApiCall(usersApi.registrarUsuarioNormal(userToRegister), gson);
    }

    public Either<String, Void> changePassword(String changePasswordEmail) {
        return safeApiCall(usersApi.changePassword(changePasswordEmail), gson);
    }

    public Either<String, LoginResultDTO> doLogin(String correo, String password) {
        return safeApiCall(usersApi.doLogin(correo, password), gson);
    }
}
