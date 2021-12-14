package org.quevedo.ee.rest;

import jakarta.inject.Inject;
import jakarta.ws.rs.*;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;
import org.quevedo.errors.ApiError;
import org.quevedo.model.persona.EstadoCivil;
import org.quevedo.model.persona.Persona;
import org.quevedo.services.ServicePersonas;
import org.quevedo.utils.RestPathConstants;

import java.time.LocalDateTime;
import java.util.List;
import java.util.concurrent.atomic.AtomicReference;

@Path(RestPathConstants.PATH_PERSONAS)
@Produces(MediaType.APPLICATION_JSON)
@Consumes(MediaType.APPLICATION_JSON)
public class RestPersonas {
    private final ServicePersonas services;

    @Inject
    public RestPersonas(ServicePersonas services) {
        this.services = services;
    }

    @GET
    public Response getAllPersonas() {
        try {
            List<Persona> personas = services.getAllPersonas();
            return Response.ok(personas).build();
        }catch (Exception exception){
            return Response
                    .status(Response.Status.INTERNAL_SERVER_ERROR)
                    .entity(new ApiError(LocalDateTime.now(), exception.getMessage()))
                    .build();
        }
    }

    @GET
    @Path(RestPathConstants.PATH_FILTER)
    public Response getFilterPersonas(@QueryParam(RestPathConstants.PARAM_PROCEDENCIA) String lugarProcedencia,
                                      @QueryParam(RestPathConstants.PARAM_NACIMIENTO) String birthYear,
                                      @QueryParam(RestPathConstants.PARAM_NUM_HIJOS) String numeroHijos,
                                      @QueryParam(RestPathConstants.PARAM_ESTADO_CIVIL) EstadoCivil estadoCivil
                                      ) {
//      Cojo los parámetros como String, porque si los cojo como int, si no están presentes en la consulta se ponen como 0
        try {
            Integer escapedBirthYear;
            Integer escapedNumeroHijos;

            if (birthYear == null){
                escapedBirthYear = null;
            }else {
                escapedBirthYear =  Integer.valueOf(birthYear);
            }

            if(numeroHijos == null){
                escapedNumeroHijos = null;
            }else {
                escapedNumeroHijos = Integer.valueOf(numeroHijos);
            }

            List<Persona> resultPersonas = services.getPersonasFilter(lugarProcedencia, escapedBirthYear, escapedNumeroHijos, estadoCivil);
            return Response.ok(resultPersonas).build();
        }catch (Exception exception){
            return Response
                    .status(Response.Status.INTERNAL_SERVER_ERROR)
                    .entity(new ApiError(LocalDateTime.now(), exception.toString()))
                    .build();
        }
    }

    @PUT
    public Response createPersona(Persona persona){
        try {
            return Response.status(Response.Status.CREATED).entity(services.createPersona(persona)).build();
        }catch (Exception exception){
            return Response
                    .status(Response.Status.INTERNAL_SERVER_ERROR)
                    .entity(new ApiError(LocalDateTime.now(), exception.getMessage()))
                    .build();
        }
    }

    @POST
    public Response updatePersona(Persona persona){
        AtomicReference<Response> atomicResponse = new AtomicReference<>();
        services.updatePersona(persona)
                .peek(persona1 -> atomicResponse.set(Response.status(Response.Status.ACCEPTED).build()))
                .peekLeft(apiError -> atomicResponse
                        .set(Response.status(Response.Status.NOT_FOUND)
                        .entity(apiError)
                        .build()));
        return atomicResponse.get();
    }

    //Exiliar o mudar persona fuera de Nashville
    @DELETE
    @Path(RestPathConstants.PATH_ID)
    public Response deletePersona(@PathParam(RestPathConstants.PARAM_ID) String personaId){
        AtomicReference<Response> atomicResponse = new AtomicReference<>();
        services.exiliarPersona(personaId)
                .peek(serverResponse -> atomicResponse
                        .set(Response.ok(serverResponse).build()))
                .peekLeft(apiError -> atomicResponse
                        .set(Response.status(Response.Status.NOT_FOUND).entity(apiError).build()));
        return atomicResponse.get();
    }
}
