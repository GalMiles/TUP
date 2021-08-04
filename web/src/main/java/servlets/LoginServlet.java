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
import java.sql.SQLException;
import java.util.stream.Collectors;

@WebServlet(name="LoginServlet", urlPatterns = "/login")
public class LoginServlet extends HttpServlet {
    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {

        Gson gson = new Gson();
        BufferedReader reader = req.getReader();
        String lines = reader.lines().collect(Collectors.joining());
        User newUser = gson.fromJson(lines, User.class);
        ResponseJson responseJson = new ResponseJson();
        Engine engine = ContextServletUtils.getEngine(req);

        try {
            Traveler user = engine.getTraveler(newUser.email,newUser.password);
            responseJson.message = gson.toJson(user);
        }catch (SQLException e){
            responseJson.status = "error";
            responseJson.message = "SQL error--" + e.getMessage();

        } catch (Traveler.NotFoundException e) {
            responseJson.status = "error";
            responseJson.message = "Invalid email or password";
        }

        try(PrintWriter out = resp.getWriter()) {
            out.println(gson.toJson(responseJson));
        }
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        doGet(req, resp);
    }

    static class User {
        String email;
        String password;

    }


}
