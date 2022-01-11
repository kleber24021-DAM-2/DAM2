package org.quevedo.server.ee.rest;

import io.vavr.control.Either;
import jakarta.inject.Inject;
import jakarta.ws.rs.Consumes;
import jakarta.ws.rs.POST;
import jakarta.ws.rs.Path;
import jakarta.ws.rs.Produces;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;
import org.quevedo.server.ee.filters.AdminFilt;
import org.quevedo.server.ee.utils.EEConst;
import org.quevedo.server.services.ServicesPartidos;
import org.quevedo.sharedmodels.partido.Partido;
import org.quevedo.sharedmodels.partido.RegisterPartidoDTO;
import org.quevedo.sharedmodels.partido.UpdateResultPartidoDTO;
import org.quevedo.sharedmodels.servererror.ServerError;

@Path(EEConst.PATH_ADMIN_USERS + EEConst.PATH_REST_PARTIDOS)
@Produces(MediaType.APPLICATION_JSON)
@Consumes(MediaType.APPLICATION_JSON)
@AdminFilt
public class PartidosAdminRest {

    private final ServicesPartidos servicesPartidos;

    @Inject
    public PartidosAdminRest(ServicesPartidos servicesPartidos) {
        this.servicesPartidos = servicesPartidos;
    }

    @POST
    @Path(EEConst.PATH_ADD)
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


}