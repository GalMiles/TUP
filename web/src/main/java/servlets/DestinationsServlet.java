package servlets;

import engine.Engine;
import servlets.utils.ContextServletUtils;
import servlets.utils.ServletUtils;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;

@WebServlet(name = "DestinationsServlet", urlPatterns = {"/destinations"})

public class DestinationsServlet extends HttpServlet {
    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {

        ServletUtils servletUtils = new ServletUtils(req);
        Engine engine = ContextServletUtils.getEngine(req);
        ArrayList<String> destinations = engine.getDestinations();
        servletUtils.writeJsonResponse(destinations);

        try (PrintWriter out = resp.getWriter()) {
            out.println(servletUtils.createOutResponse());
        }

    }

}
