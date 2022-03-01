package org.quevedo.server.dao.interfaces;


import io.vavr.control.Either;
import org.quevedo.common.models.Secreto;
import org.quevedo.common.models.SecretoDTO;

import java.util.List;

public interface DaoSecretos {
    Either<String, List<Secreto>> getAllSecretosByUsername(String usename);

    Either<String, SecretoDTO> saveSecret(SecretoDTO secreto);

    Either<String, String> getOwnPasswordBySecretIdAndUserId(int secretId, int userId);
}
