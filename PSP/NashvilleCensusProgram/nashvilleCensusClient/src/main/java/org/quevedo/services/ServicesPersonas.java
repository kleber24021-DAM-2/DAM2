package org.quevedo.services;

import org.quevedo.dao.implementaciones.DaoPersonas;
import io.vavr.control.Either;
import org.quevedo.errors.ApiError;
import org.quevedo.model.persona.EstadoCivil;
import org.quevedo.model.persona.Persona;
import org.quevedo.model.serverresponses.ServerResponse;

import javax.inject.Inject;
import java.util.List;

public class ServicesPersonas {
    private final DaoPersonas dao;
    @Inject
    public ServicesPersonas(DaoPersonas dao){
        this.dao = dao;
    }

    public Either<ApiError, List<Persona>> getAllPersonas(){
        return dao.getAllPersonas();
    }

    public Either<ApiError, List<Persona>> filterPersonas(EstadoCivil estadoCivil, String birthYear, String lugarProcedencia, String numeroHijos){
        return dao.getPersonasByFilter(estadoCivil, birthYear, lugarProcedencia, numeroHijos);
    }

    public Either<ApiError, Persona> createPersona(Persona toCreate){
        return dao.createPersona(toCreate);
    }

    public Either<ApiError, Persona> updatePersona(Persona toUpdate){
        return dao.updatePersona(toUpdate);
    }

    public Either<ApiError, ServerResponse> mudarPersona(String idToMudar){
        return dao.mandarPersonaMudar(idToMudar);
    }
}
