package org.quevedo.server.ee.security;

import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.security.Keys;
import jakarta.enterprise.inject.Produces;
import jakarta.inject.Named;
import jakarta.inject.Singleton;
import org.quevedo.server.ee.EEConsts;

import java.security.Key;

public class KeyProvider {
    @Produces
    @Singleton
    @Named(EEConsts.KEY_PROVIDER)
    public Key key() {
        return Keys.secretKeyFor(SignatureAlgorithm.HS512);
    }
}

