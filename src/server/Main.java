package server;

import server.utils.Engine;
import server.utils.ServerLogger;

import java.util.logging.Level;
import java.util.logging.Logger;

/**
 * Главный класс приложения.
 * Отвечает за инициализацию всех менеджеров, регистрацию команд и запуск основного цикла обработки команд пользователя.
 */
public class Main {
    private static final Logger logger = ServerLogger.getInstance();
    public static void main(String[] args) {
        Engine engine = new Engine(args);
        logger.log(Level.INFO, "Запуск программы");
        engine.run(args);
    }
}
