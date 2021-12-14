package org.quevedo.services;

import org.quevedo.dao.implementaciones.DaoDefunciones;
import io.vavr.control.Either;
import org.quevedo.errors.ApiError;
import org.quevedo.model.serverresponses.ServerResponse;

import javax.inject.Inject;

public class ServiceDefunciones {
    private final DaoDefunciones dao;
    @Inject
    public ServiceDefunciones(DaoDefunciones dao){
        this.dao = dao;
    }

    public Either<ApiError, ServerResponse> registrarDefuncion(String idPersonaMuerta){
        return dao.registrarDefuncion(idPersonaMuerta);
    }
}
