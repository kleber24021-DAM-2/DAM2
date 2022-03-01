package org.quevedo.server.dao.interfaces;

import io.vavr.control.Either;
import org.quevedo.common.models.Usuario;

import java.util.List;

public interface DaoUsuario {
    Either<String, Usuario> registerUser(String username, String userCertificate);

    Either<String, Boolean> userExists(String username);

    Either<String, Usuario> getUsuarioByUsername(String username);

    Either<String, List<Usuario>> getAllUsers();

    Either<String, Usuario> getUsuarioById(int id);
}
