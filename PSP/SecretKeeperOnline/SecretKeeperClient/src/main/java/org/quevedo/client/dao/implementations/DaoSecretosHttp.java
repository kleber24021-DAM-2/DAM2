package org.quevedo.client.dao.implementations;

import com.google.gson.Gson;
import io.vavr.control.Either;
import org.quevedo.client.dao.di.retrofit.interfaces.SecretosApi;
import org.quevedo.client.dao.interfaces.DaoSecretos;
import org.quevedo.client.dao.utils.CacheAuth;
import org.quevedo.common.models.Secreto;
import org.quevedo.common.models.SecretoDTO;
import org.quevedo.common.models.UserPassword;

import javax.inject.Inject;
import java.util.List;

public class DaoSecretosHttp extends DaoGeneric implements DaoSecretos {

    private final SecretosApi secretosApi;
    private final Gson gson;
    private final CacheAuth cacheAuth;

    @Inject
    public DaoSecretosHttp(
            SecretosApi secretosApi,
            CacheAuth cacheAuth,
            Gson gson
    ) {
        this.secretosApi = secretosApi;
        this.gson = gson;
        this.cacheAuth = cacheAuth;
    }

    @Override
    public Either<String, SecretoDTO> saveSecret(Secreto toSave) {
        return safeApiCall(secretosApi.createSecret(toSave.toSecretoDTO()), gson);
    }

    @Override
    public Either<String, List<Secreto>> getAllSecretsByLoggedUser() {
        return safeApiCall(secretosApi.getAllSecretosByUsername(cacheAuth.getLoggedUsername()), gson);
    }

    @Override
    public Either<String, UserPassword> getPasswordBySecretUserId(int secretId, int userId) {
        return safeApiCall(secretosApi.getPasswordBySecretUserId(secretId, userId), gson);
    }
}
