package org.quevedo.dao.retrofit;

import org.quevedo.model.persona.EstadoCivil;
import org.quevedo.model.persona.Persona;
import org.quevedo.model.serverresponses.ServerResponse;
import org.quevedo.utils.RestPathConstants;
import retrofit2.Call;
import retrofit2.http.*;

import java.util.List;

public interface NashvilleClientPersonas {
    @GET(ApiConstants.PATH_PERSONA)
    Call<List<Persona>> getAllPersonas();

    @GET(ApiConstants.PATH_FILTER)
    Call<List<Persona>> filterPersonas(@Query(RestPathConstants.PARAM_ESTADO_CIVIL) EstadoCivil estadoCivil,
                                       @Query(RestPathConstants.PARAM_NACIMIENTO) String birthYear,
                                       @Query(RestPathConstants.PARAM_PROCEDENCIA) String lugarProcedencia,
                                       @Query(RestPathConstants.PARAM_NUM_HIJOS) String numeroHijos);

    @PUT(ApiConstants.PATH_PERSONA)
    Call<Persona> createPersona(@Body Persona toCreate);

    @POST(ApiConstants.PATH_PERSONA)
    Call<Persona> updatePersona(@Body Persona toUpdate);

    @DELETE(ApiConstants.PATH_PERSONA_ID)
    Call<ServerResponse> deletePersona(@Path(RestPathConstants.PARAM_ID) String toDeleteId);
}
