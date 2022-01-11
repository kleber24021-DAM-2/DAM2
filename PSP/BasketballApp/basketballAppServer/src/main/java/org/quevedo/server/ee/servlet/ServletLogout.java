package org.quevedo.server.ee.servlet;

import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.quevedo.server.ee.utils.EEConst;

@WebServlet(name = EEConst.SERVLET_LOGOUT_NAME, value = EEConst.SERVLET_LOGOUT_PATH)
public class ServletLogout extends HttpServlet {
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) {
        doLogout(request);
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) {
        doLogout(request);
    }

    private void doLogout(HttpServletRequest request) {
        request.getSession().setAttribute(EEConst.ATTRIBUTE_LOGGED, null);
    }
}
