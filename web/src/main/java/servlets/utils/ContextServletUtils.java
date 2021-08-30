package servlets.utils;

import engine.Engine;
import engine.traveler.Traveler;

import javax.servlet.http.HttpServletRequest;
import java.sql.SQLException;

public class ContextServletUtils {

    public static Engine getEngine(HttpServletRequest request) throws Traveler.NotFoundException, SQLException {

        System.out.println("ContextServletUtils");
        Engine engine = (Engine) request.getServletContext().getAttribute("engine");
        String stringId = request.getHeader("travelerID");
        if(stringId == null)
            throw new Traveler.NotFoundException("travelerID = null");
        if(!stringId.equals("0"))
            engine.checkIfTravelerExistsInDB(stringId);
        engine.setCurrentTravelerID(stringId);
        return engine;
    }



}
