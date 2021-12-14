package org.quevedo.services;

import io.vavr.control.Either;
import org.quevedo.dao.implementaciones.DaoNacimientos;
import org.quevedo.errors.ApiError;
import org.quevedo.model.persona.Persona;
import org.quevedo.model.serverresponses.ServerResponse;

import javax.inject.Inject;

public class ServiceNacimientos {
    private final DaoNacimientos dao;

    @Inject
    public ServiceNacimientos(DaoNacimientos dao) {
        this.dao = dao;
    }

    public Either<ApiError, ServerResponse> registrarNacimiento(String progenitor1, String progenitor2, Persona hijo) {
        return dao.registrarNacimiento(progenitor1, progenitor2, hijo);
    }
}
