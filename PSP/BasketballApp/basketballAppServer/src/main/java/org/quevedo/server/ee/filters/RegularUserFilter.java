package org.quevedo.server.ee.filters;

import io.vavr.control.Either;
import jakarta.inject.Inject;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.ws.rs.container.ContainerRequestContext;
import jakarta.ws.rs.container.ContainerRequestFilter;
import jakarta.ws.rs.core.Context;
import jakarta.ws.rs.ext.Provider;
import org.quevedo.server.ee.utils.EEConst;
import org.quevedo.server.services.ServiceUsuarios;
import org.quevedo.sharedmodels.servererror.ServerError;
import org.quevedo.sharedmodels.usuario.TipoUsuario;

import java.io.IOException;

import static org.quevedo.server.ee.filters.utils.FilterSendError.sendError;

@Provider
@RegularFilt
public class RegularUserFilter implements ContainerRequestFilter {
    private final ServiceUsuarios serviceUsuarios;
    @Context
    HttpServletRequest request;

    @Inject

    public RegularUserFilter(ServiceUsuarios serviceUsuarios) {
        this.serviceUsuarios = serviceUsuarios;
    }


    @Override
    public void filter(ContainerRequestContext containerRequestContext) throws IOException {
        String email = (String) request.getSession().getAttribute(EEConst.ATTRIBUTE_LOGGED);
        if (email == null) {
            sendError(containerRequestContext);
        } else {
            Either<ServerError, TipoUsuario> tipoUsuario = serviceUsuarios.getUserLevelByMail(email);
            if (!tipoUsuario.isRight() || (!tipoUsuario.get().equals(TipoUsuario.NORMAL) && !tipoUsuario.get().equals(TipoUsuario.ADMIN))) {
                sendError(containerRequestContext);
            }
        }
    }
}
