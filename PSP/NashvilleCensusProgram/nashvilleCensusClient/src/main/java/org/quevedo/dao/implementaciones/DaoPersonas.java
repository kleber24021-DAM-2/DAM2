package org.quevedo.dao.implementaciones;

import com.google.gson.Gson;
import org.quevedo.dao.retrofit.NashvilleClientPersonas;
import org.quevedo.gui.di.UserMessages;
import io.vavr.control.Either;
import lombok.extern.log4j.Log4j2;
import org.quevedo.errors.ApiError;
import org.quevedo.model.persona.EstadoCivil;
import org.quevedo.model.persona.Persona;
import org.quevedo.model.serverresponses.ServerResponse;
import retrofit2.Response;

import javax.inject.Inject;
import java.io.IOException;
import java.time.LocalDateTime;
import java.util.List;

@Log4j2
public class DaoPersonas {
    private final NashvilleClientPersonas apiInterfacePersonas;
    private final Gson jsonParser;

    @Inject
    public DaoPersonas(NashvilleClientPersonas apiInterfacePersonas, Gson jsonParser) {
        this.apiInterfacePersonas = apiInterfacePersonas;
        this.jsonParser = jsonParser;
    }

    public Either<ApiError, List<Persona>> getAllPersonas() {
        Either<ApiError, List<Persona>> result;
        try {
            Response<List<Persona>> response = apiInterfacePersonas.getAllPersonas().execute();
            if (response.isSuccessful()) {
                result = Either.right(response.body());
            }  else {
                if (response.errorBody() != null && response.errorBody().contentType().toString().equals("application/json")) {
                    result = Either.left(jsonParser.fromJson(response.errorBody().string(), ApiError.class));
                } else {
                    result = Either.left(new ApiError(LocalDateTime.now(), UserMessages.SERVER_INTERNAL_ERROR));
                }
            }
        } catch (Exception exception) {
            log.error(exception.getMessage(), exception);
            result = Either.left(new ApiError(LocalDateTime.now(), UserMessages.UNEXPECTED_ERROR));
        }
        return result;
    }

    public Either<ApiError, List<Persona>> getPersonasByFilter(EstadoCivil estadoCivil, String birthYear, String lugarProcedencia, String numeroHijos){
        Either<ApiError,List<Persona>> result;
        try {
            Response<List<Persona>> response = apiInterfacePersonas.filterPersonas(estadoCivil, birthYear, lugarProcedencia, numeroHijos).execute();
            if (response.isSuccessful()) {
                result = Either.right(response.body());
            } else {
                if (response.errorBody() != null && response.errorBody().contentType().toString().equals("application/json")) {
                        result = Either.left(jsonParser.fromJson(response.errorBody().string(), ApiError.class));
                } else {
                    result = Either.left(new ApiError(LocalDateTime.now(), UserMessages.SERVER_INTERNAL_ERROR));
                }
            }
        } catch (IOException | NullPointerException exception) {
            log.error(exception.getMessage(), exception);
            result = Either.left(new ApiError(LocalDateTime.now(), UserMessages.UNEXPECTED_ERROR));
        }
        return result;
    }

    public Either<ApiError, Persona> createPersona(Persona toCreate){
        Either<ApiError, Persona> result;
        try {
            Response<Persona> response = apiInterfacePersonas.createPersona(toCreate).execute();
            if (response.isSuccessful()){
                result = Either.right(response.body());
            }else {
                if (response.errorBody() !=null && response.errorBody().contentType().toString().equals("application/json")){
                    result = Either.left(jsonParser.fromJson(response.errorBody().string(), ApiError.class));
                }else {
                    result = Either.left(new ApiError(LocalDateTime.now(), UserMessages.SERVER_INTERNAL_ERROR));
                }
            }
        }catch (IOException | NullPointerException exception){
            log.error(exception.getMessage(), exception);
            result = Either.left(new ApiError(LocalDateTime.now(), UserMessages.UNEXPECTED_ERROR));
        }
        return result;
    }

    public Either<ApiError, Persona> updatePersona(Persona toUpdate){
        Either<ApiError, Persona> result;
        try {
            Response<Persona> response = apiInterfacePersonas.updatePersona(toUpdate).execute();
            if (response.isSuccessful()){
                result = Either.right(response.body());
            }else {
                if (response.errorBody() !=null && response.errorBody().contentType().toString().equals("application/json")){
                    result = Either.left(jsonParser.fromJson(response.errorBody().string(), ApiError.class));
                }else {
                    result = Either.left(new ApiError(LocalDateTime.now(), UserMessages.SERVER_INTERNAL_ERROR));
                }
            }
        }catch (IOException | NullPointerException exception){
            log.error(exception.getMessage(), exception);
            result = Either.left(new ApiError(LocalDateTime.now(), UserMessages.UNEXPECTED_ERROR));
        }
        return result;
    }
    //Para "mudar" personas. Quizás de forma forzosa
    public Either<ApiError, ServerResponse> mandarPersonaMudar(String idToMudar){
        Either<ApiError, ServerResponse> result;
        try {
            Response<ServerResponse> response = apiInterfacePersonas.deletePersona(idToMudar).execute();
            if (response.isSuccessful()){
                result = Either.right(response.body());
            }else {
                if (response.errorBody() != null && response.errorBody().contentType().toString().equals("application/json")){
                    result = Either.left(jsonParser.fromJson(response.errorBody().string(), ApiError.class));
                }else {
                    result = Either.left(new ApiError(LocalDateTime.now(), UserMessages.SERVER_INTERNAL_ERROR));
                }
            }
        }catch(IOException|NullPointerException exception){
            log.error(exception.getMessage(), exception);
            result = Either.left(new ApiError(LocalDateTime.now(), UserMessages.UNEXPECTED_ERROR));
        }
        return result;
    }

}
