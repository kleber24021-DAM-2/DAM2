package org.quevedo.client.dao.implementations;

import com.google.gson.Gson;
import io.vavr.control.Either;
import org.quevedo.client.dao.di.retrofit.interfaces.UsuariosApi;
import org.quevedo.client.dao.interfaces.DaoUsuario;
import org.quevedo.common.models.Usuario;

import javax.inject.Inject;
import java.util.List;

public class DaoUsuarioHttp extends DaoGeneric implements DaoUsuario{

    private final UsuariosApi usuariosApi;
    private final Gson gson;

    @Inject
    public DaoUsuarioHttp(
            UsuariosApi usuariosApi,
            Gson gson
    ){
        this.usuariosApi = usuariosApi;
        this.gson = gson;
    }

    @Override
    public Either<String, Usuario> registerUser(String username, String publicKey) {
        return safeApiCall(usuariosApi.registerUser(username, publicKey), gson);
    }

    @Override
    public Either<String, Boolean> userExists(String username) {
        return safeApiCall(usuariosApi.checkIfUserExists(username), gson);
    }

    @Override
    public Either<String, Boolean> loginUser(String userCertificate) {
        return null;
    }

    @Override
    public Either<String, List<Usuario>> getAllUsers() {
        return null;
    }
}
