package org.quevedo.dao.implementaciones;

import com.google.gson.Gson;
import org.quevedo.dao.retrofit.NashvilleClienteDefunciones;
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
public class DaoDefunciones {
    private NashvilleClienteDefunciones apiDefunciones;
    private Gson jsonAdapter;

    @Inject
    public DaoDefunciones(NashvilleClienteDefunciones apiDefunciones, Gson jsonAdapter){
        this.apiDefunciones = apiDefunciones;
        this.jsonAdapter = jsonAdapter;
    }

    public Either<ApiError, ServerResponse> registrarDefuncion(String idPersonaMuerta){
        Either<ApiError,ServerResponse> result;
        try {
            Response<ServerResponse> response = apiDefunciones.registrarDefuncion(idPersonaMuerta).execute();
            if (response.isSuccessful()) {
                result = Either.right(response.body());
            }else {
                if (response.errorBody() != null && response.errorBody().contentType().toString().equals("application/json")){
                    result = Either.left(jsonAdapter.fromJson(response.errorBody().string(), ApiError.class));
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
}
