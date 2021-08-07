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

@WebServlet(name = "MyTripsServlet", urlPatterns = {"/my_trips"})
public class MyTripsServlet extends HttpServlet {
    Gson gson = new Gson();

    @Override
    //get collection of my trips
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        Engine engine = ContextServletUtils.getEngine(req);
        ResponseJson responseJson = new ResponseJson();
        BufferedReader reader = req.getReader();
        String lines = reader.lines().collect(Collectors.joining());

        //creating RouteTrips collection
        Collection<RouteTrip> myTrips = null;
        try {
            myTrips = engine.getMyTrips();
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
        responseJson.message = myTrips;
        try (PrintWriter out = resp.getWriter()) {
            out.println(gson.toJson(responseJson));
        }
    }
    //delete trip from my trips
    protected void doDelete(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        Engine engine = ContextServletUtils.getEngine(req);
        ResponseJson responseJson = new ResponseJson();
        BufferedReader reader = req.getReader();

        //here there is the routeTrip we want to delete
        String lines = reader.lines().collect(Collectors.joining());

        //creating new RouteTrips collection
        Collection<RouteTrip> myTrips = null;
        try {
            myTrips = engine.DeleteFromMyTrips(lines);
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
        responseJson.message = myTrips;
        try (PrintWriter out = resp.getWriter()) {
            out.println(gson.toJson(responseJson));
        }


    }

}