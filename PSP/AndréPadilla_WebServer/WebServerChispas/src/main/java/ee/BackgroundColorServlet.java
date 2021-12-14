package ee;

import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import java.io.IOException;

@WebServlet(name = EEConstants.SERVLET_BACKGROUND_COLOR_NAME, value = EEConstants.SERVLET_BACKGROUND_COLOR_VALUE)
public class BackgroundColorServlet extends HttpServlet {
    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws IOException {
        mostrarWeb(req, resp);
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws IOException {
        mostrarWeb(req, resp);
    }

    private void mostrarWeb(HttpServletRequest request, HttpServletResponse response) throws IOException{
        String color = (String) request.getSession().getAttribute(EEConstants.COLOR_PARAMETER);
        response.getWriter().println(EEConstants.HTML_OPEN_TAG);
        response.getWriter().println(EEConstants.HTML_PART_1+ color + EEConstants.HTML_PART_2);
        response.getWriter().println(EEConstants.HTML_CLOSE_TAG);
    }
}
