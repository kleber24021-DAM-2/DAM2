package org.quevedo.server.ee.servlet;

import com.google.gson.Gson;
import io.vavr.control.Either;
import jakarta.inject.Inject;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.quevedo.server.ee.utils.EEConst;
import org.quevedo.server.services.ServiceUsuarios;
import org.quevedo.sharedmodels.usuario.LoginResultDTO;
import org.quevedo.sharedmodels.usuario.TipoUsuario;

import java.io.IOException;
import java.io.PrintWriter;

@WebServlet(name = EEConst.SERVLET_LOGIN_NAME, value = EEConst.SERVLET_LOGIN_PATH)
public class ServletLogin extends HttpServlet {
    private final ServiceUsuarios serviceUsuarios;

    @Inject
    public ServletLogin(ServiceUsuarios serviceUsuarios) {
        this.serviceUsuarios = serviceUsuarios;
    }

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        doLogin(request, response);
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        doLogin(request, response);
    }

    private void doLogin(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        response.setContentType(EEConst.CONTENT_TYPE_LOGIN);
        response.setCharacterEncoding(EEConst.ENCODING);
        PrintWriter out = response.getWriter();


        String correo = request.getParameter(EEConst.PARAM_LOGIN_CORREO);
        String pass = request.getParameter(EEConst.PARAM_LOGIN_PASSWORD);

        LoginResultDTO loginResultDTO = new LoginResultDTO();
        loginResultDTO.setCorreo(correo);

        Either<String, TipoUsuario> passwordCheckResult = serviceUsuarios.checkLogin(correo, pass);
        if (passwordCheckResult.isRight()) {
            TipoUsuario passwordVerification = passwordCheckResult.get();
            if (passwordVerification != null) {
                loginResultDTO.setLogged(true);
                loginResultDTO.setNivelAcceso(passwordVerification);
            }
            if (loginResultDTO.isLogged()) {
                loginResultDTO.setMessage(EEConst.MSG_CORRECT_LOGIN);
                request.getSession().setAttribute(EEConst.ATTRIBUTE_LOGGED, correo);
            } else {
                loginResultDTO.setMessage(EEConst.MSG_WRONG_EMAIL_PASSWORD);
            }
        } else {
            loginResultDTO.setMessage(EEConst.MSG_NOT_LOGGED_INTERNAL_ERROR);
        }
        String loginResultJson = new Gson().toJson(loginResultDTO);
        out.print(loginResultJson);
        out.flush();
    }
}
