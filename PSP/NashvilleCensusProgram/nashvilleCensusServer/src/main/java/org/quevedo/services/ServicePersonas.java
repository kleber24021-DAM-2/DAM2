package org.quevedo.services;

import io.vavr.control.Either;
import jakarta.inject.Inject;
import org.quevedo.dao.DaoConstants;
import org.quevedo.dao.DaoPersonas;
import org.quevedo.errors.ApiError;
import org.quevedo.model.persona.EstadoCivil;
import org.quevedo.model.persona.Persona;
import org.quevedo.model.serverresponses.ServerResponse;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;

public class ServicePersonas {
    private final DaoPersonas dao;

    @Inject
    public ServicePersonas(DaoPersonas dao) {
        this.dao = dao;
    }

    public List<Persona> getAllPersonas() {
        return dao.getAllPersonas();
    }

    public List<Persona> getPersonasFilter(String lugarProcedencia, Integer birthYear, Integer numeroHijos, EstadoCivil estadoCivil) {
        return dao.filterPersonas(lugarProcedencia, birthYear, numeroHijos, estadoCivil);
    }

    public Persona createPersona(Persona toCreate) {
        return dao.createPersona(toCreate);
    }

    public Either<ApiError, Persona> updatePersona(Persona toUpdate) {
        Either<ApiError, Persona> result;
        if (dao.getPersonaById(toUpdate.getPersonalId()) != null) {
            result = (Either.right(dao.updatePersona(toUpdate)));
        } else {
            result = (Either.left(new ApiError(LocalDateTime.now(), DaoConstants.NOT_FOUND_MESSAGE)));
        }
        return result;
    }

    public Either<ApiError, ServerResponse> exiliarPersona(String toExileId) {
        Either<ApiError, ServerResponse> result;
        Persona persona = dao.getPersonaById(toExileId);
        if (persona != null) {
            dao.borrarPersona(persona);
            result = Either.right(new ServerResponse(DaoConstants.SUCCESFUL, LocalDate.now(), false));
        } else {
            result = Either.left(new ApiError(LocalDateTime.now(), DaoConstants.NOT_FOUND_MESSAGE));
        }
        return result;
    }
}
