package org.quevedo.server.ee.filters.utils;

import jakarta.ws.rs.container.ContainerRequestContext;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;
import org.quevedo.server.ee.utils.EEConst;
import org.quevedo.sharedmodels.servererror.ServerError;
import org.quevedo.sharedmodels.servererror.TipoError;

public class FilterSendError {
    public static void sendError(ContainerRequestContext containerRequestContext) {
        containerRequestContext.abortWith(Response.status(Response.Status.BAD_REQUEST)
                .entity(new ServerError(EEConst.MSG_NO_ACCESS, TipoError.BAD_INPUT))
                .type(MediaType.APPLICATION_JSON_TYPE)
                .build());
    }
}
