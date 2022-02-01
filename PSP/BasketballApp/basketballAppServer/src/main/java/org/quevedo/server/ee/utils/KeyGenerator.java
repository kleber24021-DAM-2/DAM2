package org.quevedo.server.ee.utils;

import jakarta.enterprise.inject.Produces;
import jakarta.inject.Named;
import jakarta.inject.Singleton;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.security.Keys;

import java.security.Key;

public class KeyGenerator {

    @Singleton
    @Produces
    @Named(EEConst.JWT)
    public Key key(){
        return Keys.secretKeyFor(SignatureAlgorithm.HS512);
    }
}
