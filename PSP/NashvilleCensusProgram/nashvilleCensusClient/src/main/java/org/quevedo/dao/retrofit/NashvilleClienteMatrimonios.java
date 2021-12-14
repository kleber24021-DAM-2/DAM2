package org.quevedo.dao.retrofit;

import org.quevedo.model.serverresponses.ServerResponse;
import org.quevedo.utils.RestPathConstants;
import retrofit2.Call;
import retrofit2.http.PUT;
import retrofit2.http.Query;

public interface NashvilleClienteMatrimonios {
    @PUT(ApiConstants.PATH_MATRIMONIOS)
    Call<ServerResponse> registrarMatrimonio(@Query(RestPathConstants.PARAM_PERSONA1) String idPersona1,
                                             @Query(RestPathConstants.PARAM_PERSONA2) String idPersona2);
}
