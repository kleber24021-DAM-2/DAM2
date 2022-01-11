package org.quevedo.server.ee.servlet;

import jakarta.inject.Inject;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.quevedo.server.ee.utils.EEConst;
import org.quevedo.server.services.ServiceUsuarios;

import java.io.IOException;

@WebServlet(name = EEConst.NAME_SERVLET_ACTIVATE, value = EEConst.PATH_ACTIVATE)
public class ServletActivateUser extends HttpServlet {

    private final ServiceUsuarios serviceUsuarios;

    @Inject
    public ServletActivateUser(ServiceUsuarios serviceUsuarios) {
        this.serviceUsuarios = serviceUsuarios;
    }

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        activateUser(request, response);
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        activateUser(request, response);
    }

    private void activateUser(HttpServletRequest request, HttpServletResponse response) throws IOException, ServletException {
        String codActivacion = request.getParameter(EEConst.COD_ACTIVACION);
        String activationResult = serviceUsuarios.activateUsuario(codActivacion);
        request.setAttribute(EEConst.ATTRIBUTE_RESULT, activationResult);
        request.getRequestDispatcher(EEConst.ACTIVATION_JSP).forward(request, response);
    }
}
