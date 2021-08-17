package servlets.utils;

import engine.Engine;

import javax.servlet.http.HttpServletRequest;

public class ContextServletUtils {

    public static Engine getEngine(HttpServletRequest request)  {

        System.out.println("ContextServletUtils");
        Engine engine = (Engine) request.getServletContext().getAttribute("engine");
        String stringId = request.getHeader("travelerID");

        engine.setCurrentTravelerID(stringId);
        return engine;
    }



}
