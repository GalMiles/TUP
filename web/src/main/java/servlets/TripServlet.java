package servlets;


import com.google.gson.Gson;
import engine.Engine;
import engine.attraction.Attraction;
import engine.planTrip.RouteTrip;
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
import java.util.Collection;
import java.util.stream.Collectors;

//choose a single trip from "my trips" collection
@WebServlet(name = "TripServlet", urlPatterns = {"/trip"})
public class TripServlet extends HttpServlet {
    Gson gson = new Gson();

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws IOException {
        Engine engine = ContextServletUtils.getEngine(req);
        ResponseJson responseJson = new ResponseJson();
        BufferedReader reader = req.getReader();
        String lines = reader.lines().collect(Collectors.joining());

        RouteTrip trip = null;

        try {
            trip = engine.getTrip(lines);
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }

        responseJson.message = trip;

        try (PrintWriter out = resp.getWriter()) {
            out.println(gson.toJson(responseJson));
        }


    }

    }