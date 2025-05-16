package client.commands;

import common.Command;
import server.managers.CollectionManager;
import common.utility.Describable;
import common.utility.Executable;
import client.utility.ExecutionResponse;
import client.utility.console.Console;

/**
 * Команда 'save'. Сохраняет коллекцию в файл.
 * Реализует интерфейсы {@link Executable} и {@link Describable}.
 */
public class Save extends Command implements Executable, Describable {
    private final CollectionManager collectionManager;

    /**
     * Конструктор команды 'save'.
     *
     * @param ignoredConsole    консоль (не используется в этой команде)
     * @param collectionManager менеджер коллекции для сохранения
     */
    public Save(Console ignoredConsole, CollectionManager collectionManager) {
        super("save", "сохранить коллекцию в файл");
        this.collectionManager = collectionManager;
    }

    /**
     * Выполняет команду сохранения коллекции в файл.
     * Проверяет количество аргументов, вызывает метод сохранения коллекции и обрабатывает возможные ошибки.
     *
     * @param arguments аргументы команды (не должны содержать значений)
     * @return результат выполнения команды ({@link ExecutionResponse}):
     * успешное сохранение или сообщение об ошибке
     */
    @Override
    public ExecutionResponse apply(String[] arguments) {
        if (!arguments[1].isEmpty()) {
            return new ExecutionResponse(false, "Неправильное количество аргументов!\nИспользование: '" + getName() + "'");
        }

        try {
            collectionManager.saveCollection();
            return new ExecutionResponse("Коллекция успешно сохранена в файл!");
        } catch (Exception e) {
            return new ExecutionResponse(false, "Ошибка при сохранении коллекции: " + e.getMessage());
        }
    }
}
