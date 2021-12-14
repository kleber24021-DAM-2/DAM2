package org.quevedo.ee.rest;

import jakarta.inject.Inject;
import jakarta.ws.rs.*;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;
import org.quevedo.services.ServiceMatrimonios;
import org.quevedo.utils.RestPathConstants;

import java.util.concurrent.atomic.AtomicReference;

@Path(RestPathConstants.PATH_MATRIMONIOS)
@Produces(MediaType.APPLICATION_JSON)
@Consumes(MediaType.APPLICATION_JSON)
public class RestMatrimonios {
    private final ServiceMatrimonios serviceMatrimonios;

    @Inject
    public RestMatrimonios(ServiceMatrimonios serviceMatrimonios) {
        this.serviceMatrimonios = serviceMatrimonios;
    }

    @PUT
    public Response registrarMatrimonio(@QueryParam(RestPathConstants.PARAM_PERSONA1) String idPersona1, @QueryParam(RestPathConstants.PARAM_PERSONA2) String idPersona2) {
        AtomicReference<Response> atomicResponse = new AtomicReference<>();
        serviceMatrimonios.registrarCasamiento(idPersona1, idPersona2)
                .peek(serverResponse -> atomicResponse.set(Response.ok(serverResponse).build()))
                .peekLeft(apiError -> atomicResponse.set(Response.status(Response.Status.BAD_REQUEST).entity(apiError).build()));
        return atomicResponse.get();
    }

}
