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

        try {
            engine.deleteTripFromUserTrips(servletUtils.lines);
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
        } catch (SQLException e) {
            servletUtils.writeJsonResponse("error", e.getMessage());
        }

        try (PrintWriter out = resp.getWriter()) {
            out.println(servletUtils.createOutResponse());
        }
    }

        public static class TripDetails {
        String destination;
        String hotelID;
        ArrayList<String> mustSeenAttractionsID = new ArrayList<>();
        ArrayList<DesiredHoursInDay> hoursEveryDay = new ArrayList<>();

    }
}