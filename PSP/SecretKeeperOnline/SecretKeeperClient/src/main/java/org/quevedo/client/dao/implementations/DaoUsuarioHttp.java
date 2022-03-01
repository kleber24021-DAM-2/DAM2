package org.quevedo.client.dao.implementations;

import com.google.gson.Gson;
import io.vavr.Tuple2;
import io.vavr.control.Either;
import org.quevedo.client.dao.di.retrofit.interfaces.UsuariosApi;
import org.quevedo.client.dao.interfaces.DaoUsuario;
import org.quevedo.client.dao.utils.CacheAuth;
import org.quevedo.common.models.Usuario;

import javax.inject.Inject;
import java.nio.charset.StandardCharsets;
import java.security.KeyPair;
import java.util.Base64;
import java.util.List;

public class DaoUsuarioHttp extends DaoGeneric implements DaoUsuario {

    private final UsuariosApi usuariosApi;
    private final CacheAuth cacheAuth;
    private final Gson gson;

    @Inject
    public DaoUsuarioHttp(
            UsuariosApi usuariosApi,
            Gson gson,
            CacheAuth cacheAuth
    ) {
        this.usuariosApi = usuariosApi;
        this.gson = gson;
        this.cacheAuth = cacheAuth;
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
    public Either<String, Usuario> loginUser(String username, Tuple2<String, KeyPair> certificateAndUserKeyPair) {
        String certificateBase64 = Base64.getUrlEncoder().encodeToString(certificateAndUserKeyPair._1().getBytes(StandardCharsets.UTF_8));
        Either<String, Usuario> loginResult = safeApiCall(usuariosApi.getUserByUsername(username), gson);
        if (loginResult.isRight()) {
            cacheAuth.setPersonalCert(certificateBase64);
            cacheAuth.setLoggedUserId(loginResult.get().getId());
            cacheAuth.setLoggedUsername(username);
            cacheAuth.setLoggedKeyPair(certificateAndUserKeyPair._2());
        }
        return loginResult;
    }

    @Override
    public Either<String, List<Usuario>> getAllUsers() {
        return safeApiCall(usuariosApi.getAllUsers(), gson);
    }

    @Override
    public Either<String, Usuario> getUserByUsername(String username) {
        return safeApiCall(usuariosApi.getUserByUsername(username), gson);
    }
}
