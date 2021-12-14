package org.quevedo.ee.rest;

import jakarta.inject.Inject;
import jakarta.ws.rs.*;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;
import org.quevedo.model.persona.Persona;
import org.quevedo.services.ServiceNacimientos;
import org.quevedo.utils.RestPathConstants;

import java.util.concurrent.atomic.AtomicReference;

@Path(RestPathConstants.PATH_NACIMIENTOS)
@Produces(MediaType.APPLICATION_JSON)
@Consumes(MediaType.APPLICATION_JSON)
public class RestNacimientos {
    private final ServiceNacimientos services;

    @Inject
    public RestNacimientos(ServiceNacimientos services){
        this.services = services;
    }

    @PUT
    public Response addNacimiento(@QueryParam(RestPathConstants.PARAM_PERSONA1) String idProgenitor1,
                                  @QueryParam(RestPathConstants.PARAM_PERSONA2) String idProgenitor2,
                                  Persona hijo){
        AtomicReference<Response> atomicResponse = new AtomicReference<>();
        services.registrarNacimiento(idProgenitor1, idProgenitor2, hijo)
                .peek(serverResponse -> atomicResponse.set(Response.ok(serverResponse).build()))
                .peekLeft(apiError -> atomicResponse.set(Response.status(Response.Status.BAD_REQUEST).entity(apiError).build()));
        return atomicResponse.get();
    }
}
