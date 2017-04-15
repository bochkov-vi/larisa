package web; /**
 * Created by bochkov on 09.04.17.
 */

import org.h2.tools.Server;

import javax.servlet.ServletContextEvent;
import javax.servlet.ServletContextListener;
import java.sql.SQLException;


public class H2Starter implements ServletContextListener {
    Server server;


    public void contextInitialized(ServletContextEvent sce) {
        try {
            Server server = Server.createTcpServer().start();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    public void contextDestroyed(ServletContextEvent sce) {
        server.stop();
    }
}

