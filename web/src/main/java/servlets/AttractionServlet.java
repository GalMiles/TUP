package servlets;

import com.google.gson.Gson;
import engine.Engine;
import engine.attraction.Attraction;
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
import java.util.Collection;
import java.util.stream.Collectors;

@WebServlet(name = "AttractionServlet", urlPatterns = {"/activities"})
public class AttractionServlet extends HttpServlet {
    Gson gson = new Gson();

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        Engine engine = ContextServletUtils.getEngine(req);
        ResponseJson responseJson = new ResponseJson();
        BufferedReader reader = req.getReader();
        String lines = reader.lines().collect(Collectors.joining());

        Collection<Attraction> attractions = engine.getAttractions(lines);

        responseJson.message = attractions;

        try (PrintWriter out = resp.getWriter()) {
            out.println(gson.toJson(responseJson));
        }
    }

//        @Override
//        protected void doDelete(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
//
//            Engine engine = ContextServletUtils.getEngine(req);
//            ResponseJson responseJson = new ResponseJson();
//            BufferedReader reader = req.getReader();
//            String lines = reader.lines().collect(Collectors.joining());
//            Attraction jsonAttraction = gson.fromJson(lines, Attraction.class);
//
//            try {
//                engine.deleteAttraction(jsonAttraction.getId());
//            } catch (Attraction.NotFoundException e) {
//                responseJson.status = "error";
//                responseJson.message = "Activity not found";
//            }
//
//            try(PrintWriter out = resp.getWriter()) {
//                out.println(gson.toJson(responseJson));
//            }
//        }
//



}
