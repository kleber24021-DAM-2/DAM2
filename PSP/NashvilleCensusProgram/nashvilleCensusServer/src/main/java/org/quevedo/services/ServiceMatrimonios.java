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
import java.util.concurrent.atomic.AtomicReference;

public class ServiceMatrimonios {
    private final DaoPersonas dao;
    @Inject
    public ServiceMatrimonios(DaoPersonas dao){
        this.dao = dao;
    }

    public Either<ApiError, ServerResponse> registrarCasamiento(String idPersona1, String idPersona2){
        Either<String, Void> resultadoValidez = comprobarCasamiento(idPersona1, idPersona2);
        AtomicReference<Either<ApiError, ServerResponse>> response = new AtomicReference<>();
        resultadoValidez
                .peek(void1 ->{
                    dao.casarPersona(idPersona1, idPersona2);
                    response.set(Either.right(new ServerResponse(DaoConstants.MARRIED_PERSON, LocalDate.now(), false)));
                })
                .peekLeft(s -> response.set(Either.left(new ApiError(LocalDateTime.now(), s))));
        return response.get();
    }

    private Either<String, Void> comprobarCasamiento(String idPersona1, String idPersona2) {
        Either<String, Void> response;
        Persona persona1 = dao.getPersonaById(idPersona1);
        Persona persona2 = dao.getPersonaById(idPersona2);
        if (persona1 != null && persona2 != null){
            if (persona1.getEstadoCivil() != EstadoCivil.CASADO && persona2.getEstadoCivil() != EstadoCivil.CASADO) {
                if (persona1.getSexo().equals(persona2.getSexo())) {
                    response = Either.left(DaoConstants.SAME_SEX_PERSON);
                } else {
                    response = Either.right(null);
                }
            } else {
                response = Either.left(DaoConstants.ALREADY_MARRIED);
            }
        }else {
            response = Either.left(DaoConstants.NOT_FOUND_MESSAGE);
        }
        return response;
    }
}
