package org.quevedo.client.dao.implementations;

import com.google.gson.Gson;
import io.vavr.control.Either;
import lombok.extern.log4j.Log4j2;
import org.quevedo.client.dao.utils.StringConstants;
import org.quevedo.sharedmodels.servererror.ServerError;
import retrofit2.Call;
import retrofit2.Response;

import java.io.IOException;
import java.util.Objects;

@Log4j2
abstract class DaoGeneric {
    public <T> Either<String, T> safeApiCall(Call<T> call, Gson gson) {
        Either<String, T> result;
        try {
            Response<T> response = call.execute();
            if (response.isSuccessful()) {
                result = Either.right(response.body());
            } else {
                if (response.errorBody() != null) {
                    if (Objects.requireNonNull(response.errorBody().contentType()).toString().equals(StringConstants.APPLICATION_JSON)) {
                        ServerError serverError = gson.fromJson(response.errorBody().string(), ServerError.class);
                        result = Either.left(serverError.getErrorMensaje());
                    } else {
                        result = Either.left(StringConstants.MSG_INTERNAL_SERVER_ERROR);
                    }
                } else {
                    result = Either.left(StringConstants.MSG_ERROR);
                }
            }
        } catch (IOException ioEx) {
            log.error(ioEx.getMessage(), ioEx);
            result = Either.left(StringConstants.MSG_CONNECTION_ERROR);
        }
        return result;
    }
}
