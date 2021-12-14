package org.quevedo.ee.rest;

import jakarta.inject.Inject;
import jakarta.ws.rs.*;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;
import org.quevedo.services.ServiceDefunciones;
import org.quevedo.utils.RestPathConstants;

import java.util.concurrent.atomic.AtomicReference;

@Path(RestPathConstants.PATH_DECESOS)
@Produces(MediaType.APPLICATION_JSON)
@Consumes(MediaType.APPLICATION_JSON)
public class RestDefunciones {
    private final ServiceDefunciones services;

    @Inject
    public RestDefunciones(ServiceDefunciones services) {
        this.services = services;
    }

    @PUT
    @Path(RestPathConstants.PATH_ID)
    public Response addDefuncion(@PathParam(RestPathConstants.PARAM_ID) String id) {
        AtomicReference<Response> atomicResponse = new AtomicReference<>();
        services.registrarDefuncion(id)
                .peek(serverResponse -> atomicResponse.set(Response.ok(serverResponse).build()))
                .peekLeft(apiError -> atomicResponse
                        .set(Response.status(Response.Status.BAD_REQUEST)
                                .entity(apiError)
                                .build()));
        return atomicResponse.get();
    }
}
