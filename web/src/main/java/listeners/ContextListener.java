package listeners;

import engine.Engine;

import javax.servlet.ServletContextEvent;
import javax.servlet.ServletContextListener;
import javax.servlet.annotation.WebListener;
import java.sql.SQLException;

@WebListener
public class ContextListener implements ServletContextListener {
    @Override
    public void contextInitialized(ServletContextEvent servletContextEvent) {
        System.out.println("contextInitialized");

        Engine engine = null;
        try {
            engine = new Engine();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        servletContextEvent.getServletContext().setAttribute("engine", engine);
    }
}
