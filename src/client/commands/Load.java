package client.commands;

import common.Command;
import server.managers.CollectionManager;
import common.utility.Describable;
import common.utility.Executable;
import client.utility.ExecutionResponse;
import client.utility.console.Console;

/**
 * Команда 'load'. Перезагружает коллекцию из файла.
 * Реализует интерфейсы {@link Executable} и {@link Describable}.
 */
public class Load extends Command implements Executable, Describable {
    private final CollectionManager collectionManager;

    /**
     * Конструктор команды 'load'.
     *
     * @param ignoredConsole    консоль (не используется в этой команде)
     * @param collectionManager менеджер коллекции для загрузки данных
     */
    public Load(Console ignoredConsole, CollectionManager collectionManager) {
        super("load", "перезагрузить коллекцию из файла");
        this.collectionManager = collectionManager;
    }

    /**
     * Выполняет команду перезагрузки коллекции из файла.
     * Загружает коллекцию с помощью менеджера коллекции и обрабатывает возможные ошибки.
     *
     * @param arguments аргументы команды (не должны содержать значений)
     * @return результат выполнения команды ({@link ExecutionResponse}):
     * успешная загрузка или сообщение об ошибке
     */
    @Override
    public ExecutionResponse apply(String[] arguments) {
        if (!arguments[1].isEmpty()) {
            return new ExecutionResponse(false, "Неправильное количество аргументов!\nИспользование: '" + getName() + "'");
        }

        try {
            collectionManager.loadCollection();
            return new ExecutionResponse("Коллекция успешно перезагружена из файла!");
        } catch (Exception e) {
            return new ExecutionResponse(false, "Ошибка при загрузке коллекции: " + e.getMessage());
        }
    }
}
