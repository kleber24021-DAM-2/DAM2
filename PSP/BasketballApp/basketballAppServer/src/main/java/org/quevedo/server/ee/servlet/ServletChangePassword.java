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

@WebServlet(name = EEConst.NAME_SERVLET_PASSWORD_CHANGE, value = EEConst.PATH_SERVLET_PASSWORD_CHANGE)
public class ServletChangePassword extends HttpServlet {
    private final ServiceUsuarios serviceUsuarios;

    @Inject
    public ServletChangePassword(ServiceUsuarios serviceUsuarios) {
        this.serviceUsuarios = serviceUsuarios;
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        changeUserPassword(request, response);
    }

    private void changeUserPassword(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String codCambioPass = request.getParameter(EEConst.COD_PASS);
        String newPassword = request.getParameter(EEConst.PARAM_PASSWORD);
        String newPasswordConfirmation = request.getParameter(EEConst.PARAM_PASSWORD_CONFIRM);
        String changeResult = serviceUsuarios.confirmPasswordChange(codCambioPass, newPassword, newPasswordConfirmation);
        request.setAttribute(EEConst.ATTRIBUTE_RESULT, changeResult);
        request.getRequestDispatcher(EEConst.ACTIVATION_JSP).forward(request, response);
    }
}
