package servlets;

import com.google.gson.Gson;
import engine.Engine;
import engine.traveler.Traveler;
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
import java.util.stream.Collectors;

@WebServlet(name = "RegisterServlet", urlPatterns = {"/register"})
public class RegisterServlet extends HttpServlet {
    Gson gson = new Gson();

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {

        Engine engine = ContextServletUtils.getEngine(req);
        ResponseJson responseJson = new ResponseJson();
        BufferedReader reader = req.getReader();
        String lines = reader.lines().collect(Collectors.joining());

        Traveler jsonMember = gson.fromJson(lines, Traveler.class);

//        try {
//            Traveler newMember = TravelerFromJson(jsonMember, req);
//            engine.addMember(newMember);
//        } catch (Member.AlreadyExistsException e) {
//            responseJson.status = "error";
//            responseJson.message = "Member Already Exists";
//        } catch (Member.IllegalValueException e) {
//            responseJson.status = "error";
//            responseJson.message = e.getMessage();
//        }
//
//        try(PrintWriter out = resp.getWriter()) {
//            out.println(gson.toJson(responseJson));
//        }
//    }


    }
}
