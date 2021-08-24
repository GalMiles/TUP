package servlets;


import com.google.gson.Gson;
import common.DesiredHoursInDay;
import engine.Engine;
import engine.planTrip.DayPlan;
import engine.planTrip.RouteTrip;
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
import java.util.List;

@WebServlet(name = "TripServlet", urlPatterns = {"/trip"})
public class TripServlet extends HttpServlet {

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {

        ServletUtils servletUtils = new ServletUtils(req);
        Engine engine = ContextServletUtils.getEngine(req);

        try {
            ArrayList<ArrayList<DayPlan>> userTrips = engine.getUserTrips();
            servletUtils.writeJsonResponse(userTrips);
        } catch (SQLException | RouteTrip.NotFoundException e) {
            servletUtils.writeJsonResponse("error", e.getMessage());
        }

        try (PrintWriter out = resp.getWriter()) {
            out.println(servletUtils.createOutResponse());
        }
    }

    //delete trip from my trips
    protected void doDelete(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {

        ServletUtils servletUtils = new ServletUtils(req);
        Engine engine = ContextServletUtils.getEngine(req);
        TripsToDelete tripsToDelete = (TripsToDelete)servletUtils.gsonFromJson(TripsToDelete.class);

        try {
            engine.deleteTripFromUserTrips(tripsToDelete.tripsToDeleteList);
        } catch (SQLException e) {
            servletUtils.writeJsonResponse("error", e.getMessage());
        }

        try (PrintWriter out = resp.getWriter()) {
            out.println(servletUtils.createOutResponse());
        }
    }


    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws IOException {

        ServletUtils servletUtils = new ServletUtils(req);
        Engine engine = ContextServletUtils.getEngine(req);

        TripDetails tripDetails = (TripDetails)servletUtils.gsonFromJson(TripDetails.class);

        try {
            ArrayList<DayPlan> trip = engine.createTripForUser(tripDetails.destination,tripDetails.hotelID,tripDetails.mustSeenAttractionsID,tripDetails.hoursEveryDay);
            servletUtils.writeJsonResponse(trip);
        } catch (SQLException | RouteTrip.AlreadyExistException e) {
            servletUtils.writeJsonResponse("error", e.getMessage());
        }

        try (PrintWriter out = resp.getWriter()) {
            out.println(servletUtils.createOutResponse());
        }
    }

    @Override
    protected void doPut(HttpServletRequest req, HttpServletResponse resp) throws IOException {

        ServletUtils servletUtils = new ServletUtils(req);
        Engine engine = ContextServletUtils.getEngine(req);

        TripPlan tripPlan = (TripPlan)servletUtils.gsonFromJson(TripPlan.class);

        try {
            int tripID = engine.saveTripForUser(tripPlan.name, tripPlan.plans);
            servletUtils.writeJsonResponse(tripID);
        } catch (SQLException | RouteTrip.NotFoundException | RouteTrip.AlreadyExistException e) {
            servletUtils.writeJsonResponse("error", e.getMessage());
        }

        try (PrintWriter out = resp.getWriter()) {
            out.println(servletUtils.createOutResponse());
        }
    }


        static class TripDetails {
            String destination;
            String hotelID;
            ArrayList<String> mustSeenAttractionsID = new ArrayList<>();
            ArrayList<DesiredHoursInDay> hoursEveryDay = new ArrayList<>();
        }

        static class TripPlan{
            String name;
            ArrayList<DayPlan> plans = new ArrayList<>();
        }

        static class TripsToDelete{
            ArrayList<String> tripsToDeleteList;
        }
}