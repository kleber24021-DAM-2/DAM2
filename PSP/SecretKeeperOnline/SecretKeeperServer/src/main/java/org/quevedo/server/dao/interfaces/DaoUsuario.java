package org.quevedo.server.dao.interfaces;

import io.vavr.control.Either;
import org.quevedo.common.models.Usuario;

import java.util.List;

public interface DaoUsuario {
    Either<String, Usuario> registerUser(String username, String publicKey);

    Either<String, Boolean> userExists(String username);

    Either<String, Boolean> loginUser(String userCertificate);

    Either<String, List<Usuario>> getAllUsers();
}
