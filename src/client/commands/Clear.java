package client.commands;

import common.Command;
import client.managers.CollectionManager;
import common.utility.Describable;
import common.utility.Executable;
import common.utility.ExecutionResponse;
import client.utility.console.Console;

/**
 * Команда 'clear'. Очищает коллекцию.
 * Реализует интерфейсы {@link Executable} и {@link Describable}.
 */
public class Clear extends Command implements Executable, Describable {
    private final CollectionManager collectionManager;

    /**
     * Конструктор команды 'clear'.
     *
     * @param ignoredConsole    консоль (не используется в этой команде)
     * @param collectionManager менеджер коллекции для очистки
     */
    public Clear(Console ignoredConsole, CollectionManager collectionManager) {
        super("clear", "очистить коллекцию");
        this.collectionManager = collectionManager;
    }

    /**
     * Выполняет команду очистки коллекции.
     * Удаляет все элементы из коллекции.
     *
     * @param arguments аргументы команды (не используются)
     * @return результат выполнения команды ({@link ExecutionResponse})
     * с сообщением об успешности операции
     */
    @Override
    public ExecutionResponse apply(String[] arguments) {
        if (!arguments[1].isEmpty()) {
            return new ExecutionResponse(false, "Неправильное количество аргументов!\nИспользование: '" + getName() + "'");
        }
        collectionManager.getCollection().clear();

        return new ExecutionResponse("Коллекция успешно очищена!");
    }
}
