package org.quevedo.server.ee.rest;

import io.vavr.control.Either;
import jakarta.annotation.security.RolesAllowed;
import jakarta.inject.Inject;
import jakarta.ws.rs.*;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;
import lombok.extern.log4j.Log4j2;
import org.quevedo.common.models.Secreto;
import org.quevedo.common.models.SecretoDTO;
import org.quevedo.common.models.UserPassword;
import org.quevedo.server.ee.EEConsts;
import org.quevedo.server.services.ServiceSecretos;

import java.util.List;

@Log4j2
@Path(SecretoRest.SECRET)
@Consumes(MediaType.APPLICATION_JSON)
@Produces(MediaType.APPLICATION_JSON)
public class SecretoRest {
    public static final String SECRET = "/secret";
    public static final String PASSWORD = "/password";
    public static final String SECRET_ID = "secretId";
    public static final String USER_ID = "userId";
    private final ServiceSecretos serviceSecretos;

    @Inject
    public SecretoRest(ServiceSecretos serviceSecretos) {
        this.serviceSecretos = serviceSecretos;
    }

    @POST
    @RolesAllowed({EEConsts.USER})
    public Response createSecret(SecretoDTO toSave) {
        Either<String, SecretoDTO> result = serviceSecretos.createSecreto(toSave);
        Response response;
        if (result.isRight()) {
            response = Response.accepted(result.get()).build();
        } else {
            response = Response.status(Response.Status.BAD_REQUEST).entity(result.getLeft()).build();
        }
        return response;
    }

    @GET
    @RolesAllowed({EEConsts.USER})
    public Response getAllSecretsByUser(@QueryParam(EEConsts.QUERY_PARAM_USERNAME) String username) {
        Either<String, List<Secreto>> result = serviceSecretos.getAllSecretsByUser(username);
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
    @Path(PASSWORD)
    public Response getPasswordBySecretUser(@QueryParam(SECRET_ID) int secretId, @QueryParam(USER_ID) int userId) {
        Either<String, String> result = serviceSecretos.getPasswordBySecretUser(secretId, userId);
        Response response;
        if (result.isRight()) {
            response = Response.ok(new UserPassword(result.get())).build();
        } else {
            response = Response.status(Response.Status.BAD_REQUEST).entity(result.getLeft()).build();
        }
        return response;
    }

}
