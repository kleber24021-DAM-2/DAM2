package org.quevedo.client.dao.implementations;

import org.quevedo.client.dao.retrofit.interfaces.UsersApi;
import org.quevedo.client.dao.utils.CacheAuth;

import javax.inject.Inject;

public class DaoSesion {
    private final CacheAuth cacheAuth;
    @Inject
    public DaoSesion(CacheAuth cacheAuth) {
        this.cacheAuth = cacheAuth;
    }

    public void doLogout() {
        cacheAuth.setUser(null);
        cacheAuth.setPass(null);
        cacheAuth.setJwt(null);
    }
}
