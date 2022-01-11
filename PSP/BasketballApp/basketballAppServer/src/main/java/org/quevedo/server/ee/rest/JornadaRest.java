package org.quevedo.server.ee.rest;

import io.vavr.control.Either;
import jakarta.inject.Inject;
import jakarta.ws.rs.*;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;
import org.quevedo.server.ee.filters.AdminFilt;
import org.quevedo.server.ee.utils.EEConst;
import org.quevedo.server.services.ServiceJornada;
import org.quevedo.sharedmodels.Jornada;
import org.quevedo.sharedmodels.servererror.ServerError;
import org.quevedo.sharedmodels.servererror.TipoError;

import java.util.List;

@Path(EEConst.PATH_ADMIN_USERS + EEConst.PATH_REST_JORNADAS)
@Produces(MediaType.APPLICATION_JSON)
@Consumes(MediaType.APPLICATION_JSON)
@AdminFilt
public class JornadaRest {
    private final ServiceJornada serviceJornada;

    @Inject
    public JornadaRest(ServiceJornada serviceJornada) {
        this.serviceJornada = serviceJornada;
    }

    @GET
    public Response getAllJornadas() {
        Either<ServerError, List<Jornada>> result = serviceJornada.getAllJornadas();
        Response response;
        if (result.isRight()) {
            response = Response.ok(result.get()).build();
        } else {
            ServerError serverError = result.getLeft();
            if (serverError.getTipoError().equals(TipoError.BAD_INPUT)) {
                response = Response.status(Response.Status.BAD_REQUEST).entity(serverError).build();
            } else {
                response = Response.status(Response.Status.INTERNAL_SERVER_ERROR).entity(serverError).build();
            }
        }
        return response;
    }

    @POST
    @Consumes(MediaType.APPLICATION_FORM_URLENCODED)
    public Response addJornada(@FormParam(EEConst.PARAM_FECHA) String fecha) {
        Either<ServerError, Jornada> result = serviceJornada.addJornada(fecha);
        Response response;
        if (result.isRight()) {
            response = Response.accepted(result.get()).build();
        } else {
            ServerError serverError = result.getLeft();
            if (serverError.getTipoError().equals(TipoError.BAD_INPUT)) {
                response = Response.status(Response.Status.BAD_REQUEST).entity(serverError).build();
            } else {
                response = Response.status(Response.Status.INTERNAL_SERVER_ERROR).entity(serverError).build();
            }
        }
        return response;
    }
}