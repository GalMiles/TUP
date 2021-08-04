package servlets.utils;

import engine.Engine;

import javax.servlet.http.HttpServletRequest;

public class ContextServletUtils {

    public static Engine getEngine(HttpServletRequest request) {
        Engine engine = (Engine) request.getServletContext().getAttribute("engine");
        return engine;
    }

}
