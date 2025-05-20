package server;

import client.commands.*;
import client.managers.CollectionManager;
import client.managers.CommandManager;
import client.managers.DumpManager;
import client.utility.Runner;
import client.utility.console.Console;
import client.utility.console.StandartConsole;
import server.server.Server;
import server.utils.Engine;
import server.utils.ServerLogger;

import java.io.IOException;
import java.util.Scanner;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 * Главный класс приложения.
 * Отвечает за инициализацию всех менеджеров, регистрацию команд и запуск основного цикла обработки команд пользователя.
 */
public class Main {
    private static final Logger logger = ServerLogger.getInstance();
    public static void main(String[] args) {
        Engine engine = new Engine();
        logger.log(Level.INFO, "Запуск программы");
        engine.run(args);
    }
}
