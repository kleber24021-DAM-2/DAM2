package org.quevedo.dao.retrofit;

import org.quevedo.model.persona.Persona;
import org.quevedo.model.serverresponses.ServerResponse;
import org.quevedo.utils.RestPathConstants;
import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.PUT;
import retrofit2.http.Query;

public interface NashvilleClienteNacimientos {
    @PUT(ApiConstants.PATH_NACIMIENTOS)
    Call<ServerResponse>registrarNacimiento(@Query(RestPathConstants.PARAM_PERSONA1) String idProgenitor1,
                                            @Query(RestPathConstants.PARAM_PERSONA2) String idProgenitor2,
                                            @Body Persona hijo);
}
