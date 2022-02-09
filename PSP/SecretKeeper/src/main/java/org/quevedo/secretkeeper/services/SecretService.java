package org.quevedo.secretkeeper.services;

import io.vavr.control.Either;
import org.quevedo.secretkeeper.dao.api.DaoSecrets;
import org.quevedo.secretkeeper.dao.spring.DaoSecretsSpring;
import org.quevedo.secretkeeper.model.Secret;

import javax.inject.Inject;
import java.util.List;

public class SecretService {
    private final DaoSecrets daoSecrets;

    @Inject
    public SecretService(DaoSecretsSpring daoSecrets) {
        this.daoSecrets = daoSecrets;
    }

    public Either<String, Secret> saveSecret(Secret toSave, String password) {
        return daoSecrets.saveSecret(toSave, password);
    }

    public Either<String, Secret> fetchSecret(Secret toFetch, String password) {
        return daoSecrets.fetchSecret(toFetch, password);
    }

    public Either<String, List<Secret>> getAllSecrets() {
        return daoSecrets.getAllUndecryptedSecrets();
    }

    public void closePool() {
        daoSecrets.closePool();
    }
}
