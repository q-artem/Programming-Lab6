package commands;

import commands.utils.Command;
import managers.CollectionManager;
import utility.Describable;
import utility.Executable;
import utility.ExecutionResponse;
import utility.console.Console;

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
