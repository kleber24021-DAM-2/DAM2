package org.quevedo.secretkeeper.dao.utils;

import lombok.Getter;

import javax.inject.Singleton;
import java.security.KeyPair;

@Singleton
@Getter
public class UserCache {
    private String user;
    private KeyPair keyPair;

    public void setNewUser(String username, KeyPair keyPair) {
        this.user = username;
        this.keyPair = keyPair;
    }
}
