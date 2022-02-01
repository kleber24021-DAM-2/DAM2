package org.quevedo.server.ee.rest;

import io.vavr.control.Either;
import jakarta.annotation.security.RolesAllowed;
import jakarta.inject.Inject;
import jakarta.ws.rs.*;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;
import org.quevedo.server.ee.utils.EEConst;
import org.quevedo.server.services.ServicesPartidos;
import org.quevedo.sharedmodels.partido.Partido;
import org.quevedo.sharedmodels.partido.RegisterPartidoDTO;
import org.quevedo.sharedmodels.partido.UpdateResultPartidoDTO;
import org.quevedo.sharedmodels.servererror.ServerError;
import org.quevedo.sharedmodels.servererror.TipoError;

import java.util.List;

@Path(EEConst.PATH_REST_PARTIDOS)
@Produces(MediaType.APPLICATION_JSON)
@Consumes(MediaType.APPLICATION_JSON)
public class PartidosRest {

    private final ServicesPartidos servicesPartidos;

    @Inject
    public PartidosRest(ServicesPartidos servicesPartidos) {
        this.servicesPartidos = servicesPartidos;
    }

    @POST
    @Path(EEConst.PATH_ADD)
    @RolesAllowed({EEConst.ADMIN})
    public Response addPartido(RegisterPartidoDTO registerPartidoDTO) {
        Response response;
        if (registerPartidoDTO == null) {
            response = Response.status(Response.Status.BAD_REQUEST).entity(EEConst.MSG_BAD_INPUT).build();
        } else {
            Either<ServerError, Partido> result = servicesPartidos.addPartido(registerPartidoDTO);
            if (result.isRight()) {
                response = Response.ok(result.get()).build();
            } else {
                response = Response.status(Response.Status.INTERNAL_SERVER_ERROR).entity(result.getLeft()).build();
            }
        }
        return response;
    }

    @POST
    @Path(EEConst.PATH_RESULT)
    @RolesAllowed({EEConst.ADMIN})
    public Response registerResult(UpdateResultPartidoDTO partidoResult) {
        Response response;
        if (partidoResult == null) {
            response = Response.status(Response.Status.BAD_REQUEST).entity(EEConst.MSG_BAD_INPUT).build();
        } else {
            Either<ServerError, Partido> result = servicesPartidos.registerPartidoResult(partidoResult);
            if (result.isRight()) {
                response = Response.ok(result.get()).build();
            } else {
                response = Response.status(Response.Status.INTERNAL_SERVER_ERROR).entity(result.getLeft()).build();
            }
        }
        return response;
    }

    @GET
    @RolesAllowed({EEConst.ADMIN, EEConst.NORMAL})
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
    @RolesAllowed({EEConst.ADMIN, EEConst.NORMAL})
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