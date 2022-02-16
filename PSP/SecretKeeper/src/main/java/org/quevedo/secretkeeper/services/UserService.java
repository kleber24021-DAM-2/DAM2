package org.quevedo.secretkeeper.services;

import io.vavr.control.Either;
import org.quevedo.secretkeeper.dao.api.DaoUser;

import javax.inject.Inject;
import java.util.List;
import java.util.stream.Collectors;

public class UserService {

    private final DaoUser daoUser;

    @Inject
    public UserService(DaoUser daoUser) {
        this.daoUser = daoUser;
    }

    public Either<String, Boolean> createUser(String username, String password) {
        Either<String, List<String>> verifyUsers = daoUser.getAllUsers();
        Either<String, Boolean> result;
        if (verifyUsers.isRight()) {
            if (verifyUsers.get().stream()
                    .filter(s -> s.equals(username))
                    .collect(Collectors.toList()).isEmpty()) {
                result = daoUser.registerUser(username, password);
            } else {
                result = Either.right(false);
            }
        } else {
            result = Either.left(verifyUsers.getLeft());
        }
        return result;
    }

    public Either<String, Boolean> doLogin(String username, String password) {
        return daoUser.loginUser(username, password);
    }

    public Either<String, List<String>> getAllUsers() {
        return daoUser.getAllUsers();
    }
}
