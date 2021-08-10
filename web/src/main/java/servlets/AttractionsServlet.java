package servlets;


import com.google.gson.Gson;
import engine.Engine;
import engine.attraction.Attraction;
import servlets.utils.ContextServletUtils;
import servlets.utils.ResponseJson;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.PrintWriter;
import java.sql.SQLException;
import java.text.ParseException;
import java.util.ArrayList;
import java.util.Collection;
import java.util.stream.Collectors;

//favorite attractions
@WebServlet(name = "AttractionsServlet", urlPatterns = {"/attractions/favorites", "/attractions/all"})
public class AttractionsServlet extends HttpServlet {
    Gson gson = new Gson();

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        if(req.getServletPath().endsWith("/all"))
            processGetRequestAllAttractions(req, resp);

        if(req.getServletPath().endsWith("/favorites"))
            processGetRequestFavoritesAttractions(req, resp);


    }

    private void processGetRequestAllAttractions(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException{
        Engine engine = ContextServletUtils.getEngine(req);
        ResponseJson responseJson = new ResponseJson();
        BufferedReader reader = req.getReader();
        String lines = reader.lines().collect(Collectors.joining()); ///destination

        Collection<Attraction> attractions = null;
        try {
            attractions = engine.getAttractions(lines);
            responseJson.message = attractions;
        } catch (SQLException e) {
            responseJson.status = "error";
            responseJson.message = "SQL error- " + e.getMessage();
        }

        try (PrintWriter out = resp.getWriter()) {
            out.println(gson.toJson(responseJson));
        }
    }

    private void processGetRequestFavoritesAttractions(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException{
        Engine engine = ContextServletUtils.getEngine(req);
        ResponseJson responseJson = new ResponseJson();
        BufferedReader reader = req.getReader();
        String lines = reader.lines().collect(Collectors.joining()); ///destination

        Collection<Attraction> favAttractions = null;

        try {
            favAttractions = engine.getAttractions(lines);
            responseJson.message = favAttractions;

        } catch (SQLException e) {
            responseJson.status = "error";
            responseJson.message = "SQL error- " + e.getMessage();
        }

        try (PrintWriter out = resp.getWriter()) {
            out.println(gson.toJson(responseJson));
        }
    }

    //delete attraction from favorite attraction
    @Override
    protected void doDelete(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        Engine engine = ContextServletUtils.getEngine(req);
        ResponseJson responseJson = new ResponseJson();
        BufferedReader reader = req.getReader();
        String lines = reader.lines().collect(Collectors.joining()); /// id

        try {
            engine.deleteFavoriteAttractionsById(lines);
        } catch (SQLException e) {
            responseJson.status = "error";
            responseJson.message = "SQL error- " + e.getMessage();
        } catch (Attraction.NotFoundException e) {
            responseJson.status = "error";
            responseJson.message = "Attraction not found";

            try (PrintWriter out = resp.getWriter()) {
                out.println(gson.toJson(responseJson));
            }
        }
    }

    //add attraction to favorite attraction
    @Override
    protected void doPut(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        Engine engine = ContextServletUtils.getEngine(req);
        ResponseJson responseJson = new ResponseJson();
        BufferedReader reader = req.getReader();
        String lines = reader.lines().collect(Collectors.joining());

        Collection<Attraction> favAttractions = null;
        //in lines we hava the attraction name the user want to add
        try {
            favAttractions = engine.addToFavAttractions(lines);
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        } catch (ParseException e) {
            e.printStackTrace();
        }

        //creating response message
        responseJson.message = favAttractions;

        PrintWriter out = resp.getWriter();
        out.println(gson.toJson(responseJson));
    }
        }

