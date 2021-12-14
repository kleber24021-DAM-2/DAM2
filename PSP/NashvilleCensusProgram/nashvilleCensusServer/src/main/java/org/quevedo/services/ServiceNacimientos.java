package org.quevedo.services;

import io.vavr.control.Either;
import jakarta.ejb.Local;
import jakarta.inject.Inject;
import org.quevedo.dao.DaoConstants;
import org.quevedo.dao.DaoPersonas;
import org.quevedo.errors.ApiError;
import org.quevedo.model.persona.EstadoCivil;
import org.quevedo.model.persona.Persona;
import org.quevedo.model.serverresponses.ServerResponse;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.concurrent.atomic.AtomicReference;

public class ServiceNacimientos {
    private final DaoPersonas dao;

    @Inject
    public ServiceNacimientos(DaoPersonas dao) {
        this.dao = dao;
    }

    public Either<ApiError, ServerResponse> registrarNacimiento(String idProgenitor1, String idProgenitor2, Persona hijo) {
        AtomicReference<Either<ApiError, ServerResponse>> atomicEither = new AtomicReference<>();
        comprobarNacimiento(idProgenitor1, idProgenitor2)
                .peek(void1 ->{
                    dao.registrarHijo(idProgenitor1, idProgenitor2, hijo);
                    atomicEither.set(Either.right(new ServerResponse(DaoConstants.BIRTH_REGISTERED, LocalDate.now(), false)));
                })
                .peekLeft(serverResponse -> {
                    if (serverResponse.getMessage().equals(DaoConstants.NOT_FOUND_MESSAGE)){
                        atomicEither.set(Either.left(new ApiError(LocalDateTime.now(), DaoConstants.NOT_FOUND_MESSAGE)));
                    }else {
                        atomicEither.set(Either.right(serverResponse));
                    }
                });
        return atomicEither.get();
    }

    private Either<ServerResponse, Void> comprobarNacimiento(String idProgenitor1, String idProgenitor2) {
        Either<ServerResponse, Void> result;
        Persona progenitor1 = dao.getPersonaById(idProgenitor1);
        Persona progenitor2 = dao.getPersonaById(idProgenitor2);
        if (progenitor1 == null || progenitor2 == null) {
            result = Either.left(new ServerResponse(DaoConstants.NOT_FOUND_MESSAGE, LocalDate.now(), false));
        } else {
            if (progenitor1.getEstadoCivil() != EstadoCivil.CASADO || progenitor2.getEstadoCivil() != EstadoCivil.CASADO || !(progenitor1.getIdPersonaCasada().equals(progenitor2.getPersonalId()))) {
                result = Either.left(new ServerResponse(DaoConstants.NOT_MARRIED, LocalDate.now(), true));
            } else {
                result = Either.right(null);
            }
        }
        return result;
    }
}
