package org.quevedo.secretkeeper.dao.api;

import io.vavr.control.Either;

import java.util.List;

public interface DaoUser {
    Either<String, Boolean> loginUser(String username, String password);

    Either<String, Boolean> registerUser(String username, String password);

    Either<String, List<String>> getAllUsers();
}
