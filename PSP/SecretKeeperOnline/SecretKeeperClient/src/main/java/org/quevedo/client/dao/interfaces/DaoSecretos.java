package org.quevedo.client.dao.interfaces;

import io.vavr.control.Either;
import org.quevedo.common.models.Secreto;

import java.util.List;

public interface DaoSecretos {
    Either<String, Secreto> saveSecret(Secreto toSave);

    Either<String, Secreto> fetchSecreto(Secreto toFetch);

    Either<String, Secreto> shareSecretoWithUsers(Secreto toShare);

    Either<String, List<Secreto>> getAllUndecryptedSecrets();

    void closePool();
}
