package servlets;


import com.google.gson.Gson;
import engine.Engine;
import engine.attraction.Attraction;
import engine.planTrip.DayPlan;
import engine.planTrip.RouteTrip;
import javafx.util.Pair;
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
import java.util.ArrayList;
import java.util.Collection;
import java.util.stream.Collectors;

@WebServlet(name = "TripServlet", urlPatterns = {"/trip"})
public class TripServlet extends HttpServlet {
    Gson gson = new Gson();

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        //req.getCookies()
        Engine engine = ContextServletUtils.getEngine(req);
        ResponseJson responseJson = new ResponseJson();
        BufferedReader reader = req.getReader();
        String lines = reader.lines().collect(Collectors.joining());

        Collection<RouteTrip> userTrips = null;
        try {
            userTrips = engine.getUserTrips();
            responseJson.message = userTrips;
        } catch (SQLException e) {
            responseJson.status = "error";
            responseJson.message = "SQL error- " + e.getMessage();
        }catch (RouteTrip.NotFoundException e){
            responseJson.status = "error";
            responseJson.message = "Attraction not found";

        }
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

        try {
            engine.deleteTripFromUserTrips(lines);
        } catch (SQLException e) {
            responseJson.status = "error";
            responseJson.message = "SQL error- " + e.getMessage();
        }

        try (PrintWriter out = resp.getWriter()) {
            out.println(gson.toJson(responseJson));
        }
    }


    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws IOException {

        Engine engine = ContextServletUtils.getEngine(req);
        ResponseJson responseJson = new ResponseJson();
        BufferedReader reader = req.getReader();
        String lines = reader.lines().collect(Collectors.joining());
        TripDetails tripDetails = gson.fromJson(lines, TripDetails.class);
        ArrayList<DayPlan> trip = null;

        try {
            trip = engine.createTripForUser(tripDetails.destination,tripDetails.hotelID, tripDetails.arrivingDate,
                    tripDetails.leavingDate,tripDetails.mustSeenAttractionsID);
            responseJson.message = trip;
        } catch (SQLException e) {
            responseJson.status = "error";
            responseJson.message = "SQL error- " + e.getMessage();
        }
        try (PrintWriter out = resp.getWriter()) {
            out.println(gson.toJson(responseJson));
        }
    }

    static class TripDetails
    {
        String destination;
        String hotelID;
        String arrivingDate;
        String leavingDate;
        ArrayList<String> mustSeenAttractionsID;
        ArrayList<Pair<String,String>> hoursEveryday;
    }
}