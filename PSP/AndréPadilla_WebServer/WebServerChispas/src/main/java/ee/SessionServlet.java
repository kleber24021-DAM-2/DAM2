package ee;

import jakarta.servlet.*;
import jakarta.servlet.http.*;
import jakarta.servlet.annotation.*;

import java.io.IOException;

@WebServlet(name = EEConstants.SERVLET_SESSION_NAME, value = EEConstants.SERVLET_SESSION_VALUE)
public class SessionServlet extends HttpServlet {
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        registerSession(request);
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        registerSession(request);
    }

    private void registerSession(HttpServletRequest request){
        request.getSession().setAttribute(EEConstants.COLOR_PARAMETER, EEConstants.COLOR_VALUE);
    }
}
