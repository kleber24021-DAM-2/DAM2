package org.quevedo.dao.retrofit;

import org.quevedo.model.serverresponses.ServerResponse;
import org.quevedo.utils.RestPathConstants;
import retrofit2.Call;
import retrofit2.http.PUT;
import retrofit2.http.Path;

public interface NashvilleClienteDefunciones {
    @PUT(ApiConstants.PATH_DECESOS)
    Call<ServerResponse> registrarDefuncion(@Path(RestPathConstants.PARAM_ID) String toDieId);
}
