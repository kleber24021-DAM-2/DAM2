package org.quevedo.client.dao.implementations;

import org.quevedo.client.dao.retrofit.interfaces.UsersApi;

import javax.inject.Inject;

public class DaoSesion {
    private final UsersApi usersApi;

    @Inject
    public DaoSesion(UsersApi usersApi) {
        this.usersApi = usersApi;
    }

    public void doLogout() {
        usersApi.doLogout();
    }
}
