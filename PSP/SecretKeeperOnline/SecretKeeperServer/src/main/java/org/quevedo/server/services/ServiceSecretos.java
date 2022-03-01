package org.quevedo.server.services;

import io.vavr.control.Either;
import jakarta.inject.Inject;
import org.quevedo.common.models.Secreto;
import org.quevedo.common.models.SecretoDTO;
import org.quevedo.server.dao.interfaces.DaoSecretos;

import java.util.List;

public class ServiceSecretos {
    private final DaoSecretos daoSecretos;

    @Inject
    public ServiceSecretos(DaoSecretos daoSecretos) {
        this.daoSecretos = daoSecretos;
    }

    public Either<String, SecretoDTO> createSecreto(SecretoDTO toSave) {
        return daoSecretos.saveSecret(toSave);
    }

    public Either<String, List<Secreto>> getAllSecretsByUser(String username) {
        return daoSecretos.getAllSecretosByUsername(username);
    }

    public Either<String, String> getPasswordBySecretUser(int secretId, int userId) {
        return daoSecretos.getOwnPasswordBySecretIdAndUserId(secretId, userId);
    }
}
