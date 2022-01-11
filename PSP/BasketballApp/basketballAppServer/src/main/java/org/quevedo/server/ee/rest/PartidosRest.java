package org.quevedo.server.ee.rest;

import io.vavr.control.Either;
import jakarta.inject.Inject;
import jakarta.ws.rs.*;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;
import org.quevedo.server.ee.filters.RegularFilt;
import org.quevedo.server.ee.utils.EEConst;
import org.quevedo.server.services.ServicesPartidos;
import org.quevedo.sharedmodels.partido.Partido;
import org.quevedo.sharedmodels.servererror.ServerError;
import org.quevedo.sharedmodels.servererror.TipoError;

import java.util.List;

@Path(EEConst.PATH_REGULAR_USERS + EEConst.PATH_REST_PARTIDOS)
@Produces(MediaType.APPLICATION_JSON)
@Consumes(MediaType.APPLICATION_JSON)
@RegularFilt
public class PartidosRest {
    private final ServicesPartidos servicesPartidos;

    @Inject
    public PartidosRest(ServicesPartidos servicesPartidos) {
        this.servicesPartidos = servicesPartidos;
    }

    @GET
    public Response getAllPartidos() {
        Either<ServerError, List<Partido>> result = servicesPartidos.getAllPartidos();
        Response response;
        if (result.isRight()) {
            response = Response.ok(result.get()).build();
        } else {
            response = Response.status(Response.Status.INTERNAL_SERVER_ERROR).entity(result.getLeft()).build();
        }
        return response;
    }

    @GET
    @Path(EEConst.PATH_FILTER)
    public Response getFilteredPartidos(@QueryParam(EEConst.QUERY_PARAM_EQUIPO) String equipo, @QueryParam(EEConst.QUERY_PARAM_JORNADA) Integer idJornada) {
        Response response;
        Either<ServerError, List<Partido>> result = servicesPartidos.getPartidosByEquipoAndIdJornada(equipo, idJornada);
        if (result.isRight()) {
            response = Response.ok(result.get()).build();
        } else {
            if (result.getLeft().getTipoError().equals(TipoError.BAD_INPUT)) {
                response = Response.status(Response.Status.BAD_REQUEST).entity(result.getLeft()).build();
            } else {
                response = Response.status(Response.Status.INTERNAL_SERVER_ERROR).entity(result.getLeft()).build();
            }
        }

        return response;
    }
}