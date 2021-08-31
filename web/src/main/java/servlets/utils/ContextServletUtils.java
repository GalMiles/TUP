package servlets.utils;

import engine.Engine;
import engine.traveler.Traveler;

import javax.servlet.http.HttpServletRequest;
import java.sql.SQLException;

public class ContextServletUtils {

    public static Engine getEngine(HttpServletRequest request) throws Traveler.NotFoundException, SQLException {

        System.out.println("ContextServletUtils");
        Engine engine = (Engine) request.getServletContext().getAttribute("engine");
        verifyTravelerId(request, engine);
        return engine;
    }
    private static void verifyTravelerId(HttpServletRequest request, Engine engine) throws Traveler.NotFoundException, SQLException {
        String stringId = request.getHeader("travelerID");
        if(stringId == null || stringId.isEmpty())
            throw new Traveler.NotFoundException("travelerID = null");

        if(stringId.equals("0")){
            if(!request.getMethod().equals("POST") || !request.getServletPath().endsWith("/traveler"))
                throw new Traveler.NotFoundException("traveler id equals 0 and not allowed!!");
        }
        else{
            engine.checkIfTravelerExistsInDB(stringId);
        }
        engine.setCurrentTravelerID(stringId);
    }


}
