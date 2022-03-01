package org.quevedo.client.dao.interfaces;

import io.vavr.control.Either;
import org.quevedo.common.models.Secreto;
import org.quevedo.common.models.SecretoDTO;
import org.quevedo.common.models.UserPassword;

import java.util.List;

public interface DaoSecretos {
    Either<String, SecretoDTO> saveSecret(Secreto toSave);

    Either<String, List<Secreto>> getAllSecretsByLoggedUser();

    Either<String , UserPassword> getPasswordBySecretUserId(int secretId, int userId);
}
