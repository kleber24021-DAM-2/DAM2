package org.quevedo.client.dao.interfaces;

import io.vavr.Tuple2;
import io.vavr.control.Either;
import org.quevedo.common.models.Usuario;

import java.security.KeyPair;
import java.util.List;

public interface DaoUsuario {
    //Returns the certificate generated by the server
    Either<String, Usuario> registerUser(String username, String publicKey);

    Either<String, Boolean> userExists(String username);

    Either<String, Usuario> loginUser(String username, Tuple2<String, KeyPair> certificateAndUserKeyPair);

    Either<String, List<Usuario>> getAllUsers();

    Either<String, Usuario> getUserByUsername(String username);
}
