package servlets;

import common.DesiredHoursInDay;
import common.TripPlan;
import engine.Engine;
import engine.traveler.Traveler;
import engine.trip.DayPlan;
import engine.trip.RouteTrip;
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

@WebServlet(name = "TripServlet", urlPatterns = {"/trip"})
public class TripServlet extends HttpServlet {

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws IOException {

        ServletUtils servletUtils = new ServletUtils(req);
        Engine engine = ContextServletUtils.getEngine(req);

        try {
            ArrayList<TripPlan> userTrips = engine.getUserTrips();
            servletUtils.writeJsonResponse(userTrips);
        } catch (SQLException | Traveler.HasNoTripsException e) {
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
            engine.deleteTripFromUserTrips(tripsToDelete.tripsIdToDeleteList);
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
            int tripID = engine.saveUserTripOnDB(tripPlan);
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


        static class TripsToDelete{
            ArrayList<String> tripsIdToDeleteList;
        }
}