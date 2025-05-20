package client;

import client.utility.Engine;

import java.io.IOException;

/**
 * Главный класс приложения.
 * Отвечает за инициализацию всех менеджеров, регистрацию команд и запуск основного цикла обработки команд пользователя.
 */
public class Main {
    /**
     * Точка входа в приложение.
     * <p>
     * Ожидает имя файла с коллекцией в качестве аргумента командной строки.
     * Инициализирует консоль, менеджеры коллекции, команд и дампа, регистрирует все поддерживаемые команды.
     * Запускает интерактивный режим работы с пользователем.
     * </p>
     *
     * @param args аргументы командной строки (args[0] — имя файла с коллекцией)
     */
    public static void main(String[] args) {
        try {
            Engine engine = new Engine();
            engine.run(args);
        } catch (IOException e) {
            System.out.println(e.getMessage());
        }
    }
}
