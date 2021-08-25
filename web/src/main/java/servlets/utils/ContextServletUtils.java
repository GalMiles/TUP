package servlets.utils;

import engine.Engine;
import engine.traveler.Traveler;

import javax.servlet.http.HttpServletRequest;

public class ContextServletUtils {

    public static Engine getEngine(HttpServletRequest request) throws Traveler.NotFoundException {

        System.out.println("ContextServletUtils");
        Engine engine = (Engine) request.getServletContext().getAttribute("engine");
        String stringId = request.getHeader("travelerID");
        if(stringId == null)
            throw new Traveler.NotFoundException("travelerID = null");

        engine.setCurrentTravelerID(stringId);
        return engine;
    }



}
