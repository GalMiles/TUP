package servlets;

import com.google.gson.Gson;
import com.google.gson.JsonObject;
import database.DBManager;
import engine.traveler.Traveler;

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

@WebServlet(name="registerServlet", urlPatterns = "/register")
public class registerServlet extends HttpServlet {
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {

        Gson gson = new Gson();
        ResponseJson responseJson = new ResponseJson();
        responseJson.status = "GOOD";
        responseJson.message = "";

        try {
            DBManager db = new DBManager("jdbc:mysql://localhost:3306/attractions","root","742!GDFMP");
            BufferedReader reader = req.getReader();
            String lines = reader.lines().collect(Collectors.joining());
            JsonObject json = gson.fromJson(lines, JsonObject.class);
            Traveler newUser = db.getTravelerFromB(json.get("email").getAsString(), json.get("password").getAsString());
            if(newUser != null)
            {
                responseJson.message = "this email is already used";
                responseJson.status = "BAD";
            }
            else
            {
                db.insertTravelerToDataBase(newUser);
                resp.setContentType("application/json");
                resp.setCharacterEncoding("UTF-8");
                responseJson.message = gson.toJson(newUser);

            }
            db.closeConnection();
        } catch (SQLException throwable) {
            throwable.printStackTrace();

        }

        try (PrintWriter out = resp.getWriter()) {
            out.println(gson.toJson(responseJson));

        }
    }

}
