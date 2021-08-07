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
@WebServlet(name = "FavAttractionsServlet", urlPatterns = {"/favorite_attractions"})
public class FavAttractionsServlet extends HttpServlet {
    Gson gson = new Gson();

    //getting favorite attraction
    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        Engine engine = ContextServletUtils.getEngine(req);
        ResponseJson responseJson = new ResponseJson();
        BufferedReader reader = req.getReader();
        String lines = reader.lines().collect(Collectors.joining());

        Collection<Attraction> favAttractions = null;

        try {
            favAttractions = engine.getFavAttractions();
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }

        responseJson.message = favAttractions;

        PrintWriter out = resp.getWriter();
        out.println(gson.toJson(responseJson));
    }

    //delete attraction from favorite attraction
    @Override
    protected void doDelete(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        Engine engine = ContextServletUtils.getEngine(req);
        ResponseJson responseJson = new ResponseJson();
        BufferedReader reader = req.getReader();

        //getting here which attraction want to delete
        String lines = reader.lines().collect(Collectors.joining());

        //creating new favAttraction after deleting
        Collection<Attraction> favAttractions = null;

        try {
            favAttractions = engine.deleteFromFavAttractions(lines);
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        } catch (ParseException e) {
            e.printStackTrace();
        }
        responseJson.message = favAttractions;

        PrintWriter out = resp.getWriter();
        out.println(gson.toJson(responseJson));
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

