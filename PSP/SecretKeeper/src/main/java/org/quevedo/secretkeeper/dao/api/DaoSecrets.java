package org.quevedo.secretkeeper.dao.api;

import io.vavr.control.Either;
import org.quevedo.secretkeeper.model.Secret;

import java.util.List;

public interface DaoSecrets {
    Either<String, Secret> saveSecret(Secret toSave, String password);

    Either<String, Secret> fetchSecret(Secret toFetch, String password);

    Either<String, List<Secret>> getAllUndecryptedSecrets();

    void closePool();
}
