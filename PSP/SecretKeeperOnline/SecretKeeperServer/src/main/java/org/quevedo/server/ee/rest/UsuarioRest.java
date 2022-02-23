package org.quevedo.server.ee.rest;

import io.vavr.control.Either;
import jakarta.inject.Inject;
import jakarta.ws.rs.*;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;
import lombok.extern.log4j.Log4j2;
import org.quevedo.common.models.Usuario;
import org.quevedo.server.ee.EEConsts;
import org.quevedo.server.services.ServiceUsuario;

@Log4j2
@Path("/user")
@Consumes(MediaType.APPLICATION_JSON)
@Produces(MediaType.APPLICATION_JSON)
public class UsuarioRest {

    private final ServiceUsuario serviceUsuario;

    @Inject
    public UsuarioRest(ServiceUsuario serviceUsuario) {
        this.serviceUsuario = serviceUsuario;
    }

    @POST
    public Response registerUser(@QueryParam(EEConsts.QUERY_PARAM_USERNAME) String username, @QueryParam(EEConsts.QUERY_PARAM_PUBLICKEY) String publicKey) {
        Either<String, Usuario> result = serviceUsuario.registerUser(username, publicKey);
        Response response;
        if (result.isRight()) {
            response = Response.accepted(result.get()).build();
        } else {
            response = Response.status(Response.Status.BAD_REQUEST).entity(result.getLeft()).build();
        }
        return response;
    }

    @GET
    @Path("/check")
    public Response checkIfUserExists(@QueryParam(EEConsts.QUERY_PARAM_USERNAME) String username) {
        Either<String, Boolean> result = serviceUsuario.checkUserExists(username);
        Response response;
        if (result.isRight()) {
            response = Response.ok(result.get()).build();
        } else {
            response = Response.status(Response.Status.BAD_REQUEST).entity(result.getLeft()).build();
        }
        return response;

    }
}
