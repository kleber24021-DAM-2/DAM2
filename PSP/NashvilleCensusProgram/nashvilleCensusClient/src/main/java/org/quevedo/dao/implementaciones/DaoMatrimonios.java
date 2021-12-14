package org.quevedo.dao.implementaciones;

import com.google.gson.Gson;
import org.quevedo.dao.retrofit.NashvilleClienteMatrimonios;
import org.quevedo.gui.di.UserMessages;
import io.vavr.control.Either;
import lombok.extern.log4j.Log4j2;
import org.quevedo.errors.ApiError;
import org.quevedo.model.serverresponses.ServerResponse;
import retrofit2.Response;

import javax.inject.Inject;
import java.io.IOException;
import java.time.LocalDateTime;

@Log4j2
public class DaoMatrimonios {
    private final NashvilleClienteMatrimonios apiMatrimonios;
    private final Gson jsonAdapter;

    @Inject
    public DaoMatrimonios(NashvilleClienteMatrimonios apiMatrimonios, Gson jsonAdapter) {
        this.apiMatrimonios = apiMatrimonios;
        this.jsonAdapter = jsonAdapter;
    }

    public Either<ApiError, ServerResponse> registrarMatrimonio(String idPersona1, String idPersona2) {
        Either<ApiError, ServerResponse> result;
        try {
            Response<ServerResponse> response = apiMatrimonios.registrarMatrimonio(idPersona1, idPersona2).execute();
            if (response.isSuccessful()) {
                result = Either.right(response.body());
            } else {
                if (response.errorBody().contentType().toString().equals("application/json")){
                    result = Either.left(jsonAdapter.fromJson(response.errorBody().string(), ApiError.class));
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
}
