package org.quevedo.server.ee.rest;

import io.vavr.control.Either;
import jakarta.annotation.security.PermitAll;
import jakarta.annotation.security.RolesAllowed;
import jakarta.inject.Inject;
import jakarta.ws.rs.*;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;
import lombok.extern.log4j.Log4j2;
import org.quevedo.common.models.Usuario;
import org.quevedo.server.ee.EEConsts;
import org.quevedo.server.services.ServiceUsuario;

import java.util.List;

@Log4j2
@Path(EEConsts.USER_PATH)
@Consumes(MediaType.APPLICATION_JSON)
@Produces(MediaType.APPLICATION_JSON)
public class UsuarioRest {

    public static final String ID = "/{id}";
    public static final String QUERY_PARAM_ID = "id";
    private final ServiceUsuario serviceUsuario;

    @Inject
    public UsuarioRest(ServiceUsuario serviceUsuario) {
        this.serviceUsuario = serviceUsuario;
    }

    @POST
    @PermitAll
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
    @PermitAll
    @Path(EEConsts.CHECK_PATH)
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

    @GET
    @PermitAll
    @Path(EEConsts.GET_USER_PATH)
    public Response getUserByUsername(@QueryParam(EEConsts.USERNAME) String username) {
        Either<String, Usuario> result = serviceUsuario.getUserByUsername(username);
        Response response;
        if (result.isRight()) {
            response = Response.ok(result.get()).build();
        } else {
            response = Response.status(Response.Status.BAD_REQUEST).entity(result.getLeft()).build();
        }
        return response;
    }

    @GET
    @Path(ID)
    @RolesAllowed({EEConsts.USER})
    public Response getUserById(@PathParam(QUERY_PARAM_ID) int id) {
        Either<String, Usuario> result = serviceUsuario.getUserById(id);
        Response response;
        if (result.isRight()) {
            response = Response.ok(result.get()).build();
        } else {
            response = Response.status(Response.Status.BAD_REQUEST).entity(result.getLeft()).build();
        }
        return response;
    }

    @GET
    @RolesAllowed({EEConsts.USER})
    public Response getAllUsers() {
        Either<String, List<Usuario>> result = serviceUsuario.getAllUsers();
        Response response;
        if (result.isRight()) {
            response = Response.ok(result.get()).build();
        } else {
            response = Response.status(Response.Status.BAD_REQUEST).entity(result.getLeft()).build();
        }
        return response;
    }
}
