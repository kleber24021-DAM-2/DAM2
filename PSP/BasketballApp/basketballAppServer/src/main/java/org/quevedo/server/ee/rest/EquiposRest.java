package org.quevedo.server.ee.rest;

import io.vavr.control.Either;
import jakarta.inject.Inject;
import jakarta.ws.rs.*;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;
import org.quevedo.server.ee.filters.AdminFilt;
import org.quevedo.server.ee.utils.EEConst;
import org.quevedo.server.services.ServiceEquipos;
import org.quevedo.sharedmodels.Equipo;
import org.quevedo.sharedmodels.servererror.ServerError;
import org.quevedo.sharedmodels.servererror.TipoError;

import java.util.List;

@Path(EEConst.PATH_ADMIN_USERS + EEConst.PATH_REST_EQUIPOS)
@Produces(MediaType.APPLICATION_JSON)
@Consumes(MediaType.APPLICATION_JSON)
@AdminFilt
public class EquiposRest {
    private final ServiceEquipos serviceEquipos;

    @Inject
    public EquiposRest(ServiceEquipos serviceEquipos) {
        this.serviceEquipos = serviceEquipos;
    }

    @GET
    public Response getAllEquipos() {
        Either<ServerError, List<Equipo>> result = serviceEquipos.getAllEquipos();
        Response response;

        if (result.isRight()) {
            response = Response.ok(result.get()).build();
        } else {
            response = Response.status(Response.Status.INTERNAL_SERVER_ERROR).entity(result.getLeft()).build();
        }
        return response;
    }

    @POST
    public Response createNewEquipo(@QueryParam(EEConst.QUERY_PARAM_NOMBRE) String nombreEquipo) {
        Either<ServerError, Equipo> result = serviceEquipos.addEquipo(nombreEquipo);
        Response response;
        if (result.isRight()) {
            response = Response.ok(result.get()).build();
        } else {
            ServerError error = result.getLeft();
            if (error.getTipoError() == TipoError.BAD_INPUT) {
                response = Response.status(Response.Status.BAD_REQUEST).entity(error.getErrorMensaje()).build();
            } else {
                response = Response.status(Response.Status.INTERNAL_SERVER_ERROR).entity(error.getErrorMensaje()).build();
            }
        }
        return response;
    }
}