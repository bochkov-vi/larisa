package larisa;

import org.h2.tools.Server;

import java.sql.SQLException;

public class H2Starter {
    Server server;

    public H2Starter(String... args) {
        try {
            server = Server.createTcpServer(args);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public Server start() throws SQLException {
        try {
            return server.start();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return server;
    }

    public void stop() {
        server.stop();
    }
}
