package org.quevedo.server.ee.servlet;

import com.google.gson.Gson;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.ws.rs.core.Response;
import org.quevedo.server.ee.utils.EEConst;
import org.quevedo.sharedmodels.servererror.ServerError;
import org.quevedo.sharedmodels.servererror.TipoError;

import java.io.IOException;
import java.io.PrintWriter;

@WebServlet(name = EEConst.NAME_SERVLET_NO_ACCESS, value = EEConst.SERVLET_NO_ACCESS)
public class ServletNoAccess extends HttpServlet {

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        noAccess(request, response);
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        noAccess(request, response);
    }

    private void noAccess(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        response.setContentType(EEConst.CONTENT_TYPE_LOGIN);
        response.setCharacterEncoding(EEConst.ENCODING);
        response.setStatus(Response.Status.FORBIDDEN.getStatusCode());
        String serverError = new Gson().toJson(new ServerError(EEConst.MSG_NO_ACCESS, TipoError.BAD_INPUT));
        PrintWriter out = response.getWriter();
        out.print(serverError);
        out.flush();
    }
}
