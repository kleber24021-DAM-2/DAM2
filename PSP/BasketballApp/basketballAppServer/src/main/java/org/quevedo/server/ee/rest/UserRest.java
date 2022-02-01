package org.quevedo.server.ee.rest;

import io.vavr.control.Either;
import jakarta.annotation.security.RolesAllowed;
import jakarta.inject.Inject;
import jakarta.security.enterprise.SecurityContext;
import jakarta.ws.rs.*;
import jakarta.ws.rs.core.Context;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;
import lombok.extern.log4j.Log4j2;
import org.quevedo.server.dao.model.Usuario;
import org.quevedo.server.ee.utils.EEConst;
import org.quevedo.server.services.ServiceMandarMail;
import org.quevedo.server.services.ServiceUsuarios;
import org.quevedo.sharedmodels.servererror.ServerError;
import org.quevedo.sharedmodels.servererror.TipoError;
import org.quevedo.sharedmodels.usuario.LoginResultDTO;
import org.quevedo.sharedmodels.usuario.TipoUsuario;
import org.quevedo.sharedmodels.usuario.UsuarioGetDTO;
import org.quevedo.sharedmodels.usuario.UsuarioRegisterDTO;

import java.util.List;

@Log4j2
@Path(EEConst.PATH_USERS_REST)
@Consumes(MediaType.APPLICATION_JSON)
@Produces(MediaType.APPLICATION_JSON)

public class UserRest {

    private final ServiceUsuarios serviceUsuarios;
    private final ServiceMandarMail serviceMandarMail;
    private final SecurityContext securityContext;

    @Inject
    public UserRest(ServiceUsuarios serviceUsuarios,
                    ServiceMandarMail serviceMandarMail,
                    @Context SecurityContext securityContext) {
        this.serviceUsuarios = serviceUsuarios;
        this.serviceMandarMail = serviceMandarMail;
        this.securityContext = securityContext;
    }

    @GET
    @Path(EEConst.LOGIN_PATH)
    @RolesAllowed({EEConst.ADMIN, EEConst.NORMAL})
    public Response getCurrentUser() {
        LoginResultDTO loginResult = new LoginResultDTO();
        loginResult.setLogged(true);
        loginResult.setCorreo(securityContext.getCallerPrincipal().getName());
        loginResult.setMessage(EEConst.LOGUEADO_CORRECTAMENTE);
        if (securityContext.isCallerInRole(EEConst.ADMIN)) {
            loginResult.setNivelAcceso(TipoUsuario.ADMIN);
        } else {
            loginResult.setNivelAcceso(TipoUsuario.NORMAL);
        }
        return Response.ok(loginResult).build();
    }

    @GET
    @RolesAllowed({EEConst.ADMIN})
    public Response getAllUsers() {
        Either<String, List<UsuarioGetDTO>> result = serviceUsuarios.getAllUsuarios();
        Response response;

        if (result.isRight()) {
            response = Response.ok(result.get()).build();
        } else {
            response = Response.status(Response.Status.INTERNAL_SERVER_ERROR).entity(new ServerError(result.getLeft(), TipoError.INTERNAL_ERROR)).build();
        }
        return response;
    }

    @POST
    @Path(EEConst.PATH_ADMIN_USERS)
    @RolesAllowed({EEConst.ADMIN})
    public Response registerUser(UsuarioRegisterDTO receivedUser) {
        return registrarUsuario(receivedUser);
    }

    @POST
    public Response registerRegularUser(UsuarioRegisterDTO receivedUser) {
        Response response;
        if (receivedUser.getTipoUsuario().equals(TipoUsuario.ADMIN)) {
            response = Response.status(Response.Status.NOT_ACCEPTABLE).entity(new ServerError(EEConst.MSG_NOT_AUTHORIZED_ADMIN_USERS, TipoError.BAD_INPUT)).build();
        } else {
            response = registrarUsuario(receivedUser);
        }
        return response;
    }

    private Response registrarUsuario(UsuarioRegisterDTO receivedUser) {
        Response response;
        Either<ServerError, Usuario> conversionResult = serviceUsuarios.registerDTOToUsuario(receivedUser);
        Either<ServerError, UsuarioGetDTO> registerResult;
        String correo = null;
        String codigoActivacion = null;

        if (conversionResult.isRight()) {
            registerResult = serviceUsuarios.registerUsuario(conversionResult.get());
            correo = conversionResult.get().getCorreo();
            codigoActivacion = conversionResult.get().getCodigoActivacion();
        } else {
            registerResult = Either.left(conversionResult.getLeft());
        }

        if (registerResult.isRight()) {
            serviceMandarMail.sendConfirmationMail(correo, codigoActivacion);
            response = Response.accepted(registerResult.get()).build();
        } else {
            response = Response.status(Response.Status.NOT_ACCEPTABLE).entity(registerResult.getLeft()).build();
        }
        return response;
    }

    @POST
    @Path(EEConst.PATH_PASSWORD_CHANGE)
    public Response changePassword(@QueryParam(EEConst.PARAM_CORREO) String changePasswordEmail) {
        Response response;
        if (!changePasswordEmail.isEmpty()) {
            serviceUsuarios.startPasswordChange(changePasswordEmail);
            response = Response.accepted(EEConst.MSG_PASSWORD_CHANGE).build();
        } else {
            response = Response.status(Response.Status.BAD_REQUEST).entity(new ServerError(EEConst.MSG_INTRODUCE_MAIL, TipoError.BAD_INPUT)).build();
        }
        return response;
    }
}