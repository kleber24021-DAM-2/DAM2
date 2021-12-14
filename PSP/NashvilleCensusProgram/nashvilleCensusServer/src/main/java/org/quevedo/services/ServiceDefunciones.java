package org.quevedo.services;

import io.vavr.control.Either;
import jakarta.inject.Inject;
import org.quevedo.dao.DaoConstants;
import org.quevedo.dao.DaoPersonas;
import org.quevedo.errors.ApiError;
import org.quevedo.model.persona.Persona;
import org.quevedo.model.serverresponses.ServerResponse;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.concurrent.atomic.AtomicReference;

public class ServiceDefunciones {
    private final DaoPersonas dao;
    @Inject
    public ServiceDefunciones(DaoPersonas dao){
        this.dao = dao;
    }

    public Either<ApiError, ServerResponse> registrarDefuncion(String personaMuertaId){
        AtomicReference<Either<ApiError, ServerResponse>> result = new AtomicReference<>();
        revisarDatos(personaMuertaId)
                .peek(persona -> {
                    if (dao.comprobarSiEsHijo(persona)){
                        result.set(Either.right(new ServerResponse(DaoConstants.RESURRECTED_PERSON, LocalDate.now(), true)));
                    }else {
                        dao.matarPersona(persona);
                       result.set(Either.right(new ServerResponse(DaoConstants.KILLED_PERSON, LocalDate.now(), false)));
                    }
                })
                .peekLeft(apiError -> result.set(Either.left(apiError)));

        return result.get();
    }

    private Either<ApiError, Persona> revisarDatos(String idPersona){
        Persona persona = dao.getPersonaById(idPersona);
        if (persona == null) {
            return Either.left(new ApiError(LocalDateTime.now(), DaoConstants.NOT_FOUND_MESSAGE));
        }else {
            return Either.right(persona);
        }
    }
}
