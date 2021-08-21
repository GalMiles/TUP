package servlets;


import engine.Engine;
import engine.attraction.Attraction;
import servlets.utils.ContextServletUtils;
import servlets.utils.ServletUtils;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Collection;


@WebServlet(name = "AttractionsServlet", urlPatterns = {"/attractions/favorites", "/attractions/all"})
public class AttractionsServlet extends HttpServlet {

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        if (req.getServletPath().endsWith("/all"))
            processGetRequestAllAttractions(req, resp);


        if (req.getServletPath().endsWith("/favorites"))
            processGetRequestFavoritesAttractions(req, resp);
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        doGet(req, resp);
    }

    private void processGetRequestAllAttractions(HttpServletRequest req, HttpServletResponse resp) throws IOException {
        ServletUtils servletUtils = new ServletUtils(req);
        Engine engine = ContextServletUtils.getEngine(req);
        //resp.setHeader("travelerId","1");
        Collection<Attraction> attractions;
        try {
            attractions = engine.getAttractions(servletUtils.lines); //destination
            servletUtils.writeJsonResponse(attractions);
        } catch (SQLException e) {
            servletUtils.writeJsonResponse("error", e.getMessage());
        }

        try (PrintWriter out = resp.getWriter()) {
            out.println(servletUtils.createOutResponse());
        }
    }

    private void processGetRequestFavoritesAttractions(HttpServletRequest req, HttpServletResponse resp) throws IOException {

        ServletUtils servletUtils = new ServletUtils(req);
        Engine engine = ContextServletUtils.getEngine(req);

        ArrayList <Attraction> favoriteAttractions;

       try {
            favoriteAttractions = engine.getFavoriteAttractions();
            servletUtils.writeJsonResponse(favoriteAttractions);

        } catch (SQLException e) {
            servletUtils.writeJsonResponse("error", e.getMessage());
        }

        try (PrintWriter out = resp.getWriter()) {
            out.println(servletUtils.createOutResponse());
        }
    }

    //delete attraction from favorite attraction
    @Override
    protected void doDelete(HttpServletRequest req, HttpServletResponse resp) throws  IOException {

        ServletUtils servletUtils = new ServletUtils(req);
        Engine engine = ContextServletUtils.getEngine(req);

        try {
            engine.deleteFromFavoriteAttractions(servletUtils.lines);
        } catch (SQLException | Attraction.NotFoundException e) {
            servletUtils.writeJsonResponse("error", e.getMessage());
        }

        try (PrintWriter out = resp.getWriter()) {
            out.println(servletUtils.createOutResponse());
        }
    }

    //add attraction to favorite attraction
    @Override
    protected void doPut(HttpServletRequest req, HttpServletResponse resp) throws IOException {
        ServletUtils servletUtils = new ServletUtils(req);
        Engine engine = ContextServletUtils.getEngine(req);

        try {
            engine.addToFavoriteAttractions(servletUtils.lines);
        } catch (SQLException e) {
            servletUtils.writeJsonResponse("error", e.getMessage());
        }

        try (PrintWriter out = resp.getWriter()) {
            out.println(servletUtils.createOutResponse());
        }
    }
}

