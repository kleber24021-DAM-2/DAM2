package org.quevedo.services;

import org.quevedo.dao.implementaciones.DaoMatrimonios;
import io.vavr.control.Either;
import org.quevedo.errors.ApiError;
import org.quevedo.model.serverresponses.ServerResponse;

import javax.inject.Inject;

public class ServiceMatrimonios {
    private final DaoMatrimonios dao;

    @Inject
    public ServiceMatrimonios(DaoMatrimonios dao){
        this.dao = dao;
    }

    public Either<ApiError, ServerResponse> registrarMatrimonio(String idPersona1, String idPersona2){
        return dao.registrarMatrimonio(idPersona1, idPersona2);
    }
}
