package server.utils;

import java.util.logging.Logger;

public class ServerLogger {
    private static Logger instance;

    private ServerLogger() {
    }

    public static Logger getInstance() {
        if (instance == null) instance = Logger.getLogger(ServerLogger.class.getName());
        return instance;
    }

}
