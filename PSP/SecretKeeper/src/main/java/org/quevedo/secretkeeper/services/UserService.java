package org.quevedo.secretkeeper.services;

import io.vavr.control.Either;
import org.quevedo.secretkeeper.dao.security.CertCreate;

import javax.inject.Inject;

public class UserService {

    private final CertCreate certCreate;

    @Inject
    public UserService(CertCreate certCreate){
        this.certCreate = certCreate;
    }

    public Either<String, String> createUser(String username, String password){
        return certCreate.createNewCertEntry(username, password);
    }

    public Either<String, String> doLogin(String username, String password){

        return certCreate.checkUserCertificate(username, password);
    }
}
