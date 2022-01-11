package org.quevedo.client.services;

import org.quevedo.client.dao.implementations.DaoSesion;

import javax.inject.Inject;

public class ServiceLogout {
    private final DaoSesion daoSesion;

    @Inject
    public ServiceLogout(DaoSesion daoSesion) {
        this.daoSesion = daoSesion;
    }

    public void doLogout() {
        daoSesion.doLogout();
    }
}
