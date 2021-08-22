package servlets;

import engine.Engine;
import engine.traveler.Traveler;
import servlets.utils.ContextServletUtils;
import servlets.utils.ResponseJson;
import servlets.utils.ServletUtils;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;
import java.sql.SQLException;

//the use of JsonMember is needed because we want to make sure that the field we got are right.
//In order to create JsonMember we activate the setters who are responsible for validations.

@WebServlet(name = "TravelerServlet", urlPatterns = {"/traveler/register", "/traveler/edit-profile"})
public class TravelerServlet extends HttpServlet {
    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        ServletUtils servletUtils = new ServletUtils(req);
        Engine engine = ContextServletUtils.getEngine(req);
        Traveler jsonTraveler = (Traveler) servletUtils.gsonFromJson(Traveler.class);

        try {
            Traveler newTraveler = ResponseJson.travelerFromJson(jsonTraveler, req);
            String idString = engine.Register(newTraveler);
            resp.setHeader("travelerID", idString);
            servletUtils.writeJsonResponse(newTraveler);

        } catch (SQLException | Traveler.IllegalValueException | Traveler.AlreadyExistsException e) {
            servletUtils.writeJsonResponse("error", e.getMessage());
        }

        try (PrintWriter out = resp.getWriter()) {
            out.println(servletUtils.createOutResponse());
        }
    }

    @Override
    protected void doPut(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        ServletUtils servletUtils = new ServletUtils(req);
        Engine engine = ContextServletUtils.getEngine(req);
        Traveler jsonTraveler = (Traveler) servletUtils.gsonFromJson(Traveler.class);

        try {
            Traveler newTraveler = ResponseJson.travelerFromJson(jsonTraveler, req);
            engine.updateTravelerDetails(newTraveler);
            servletUtils.writeJsonResponse(newTraveler);

        } catch (SQLException | Traveler.IllegalValueException | Traveler.AlreadyExistsException e) {
            servletUtils.writeJsonResponse("error", e.getMessage());
        }
        try (PrintWriter out = resp.getWriter()) {
            out.println(servletUtils.createOutResponse());
        }
    }

}

