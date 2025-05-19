package client.commands;

import common.Command;
import client.managers.CollectionManager;
import common.utility.Describable;
import common.utility.Executable;
import common.utility.ExecutionResponse;
import client.utility.console.Console;

/**
 * Команда 'show'. Выводит все элементы коллекции в строковом представлении.
 * Реализует интерфейсы {@link Executable} и {@link Describable}.
 */
public class Show extends Command implements Executable, Describable {
    private final CollectionManager collectionManager;

    /**
     * Конструктор команды 'show'.
     *
     * @param ignoredConsole    консоль (не используется в этой команде)
     * @param collectionManager менеджер коллекции для вывода элементов
     */
    public Show(Console ignoredConsole, CollectionManager collectionManager) {
        super("show", "вывести в стандартный поток вывода все элементы коллекции в строковом представлении");
        this.collectionManager = collectionManager;
    }

    /**
     * Выполняет команду вывода всех элементов коллекции в строковом представлении.
     * Проверяет количество аргументов и возвращает строковое представление коллекции.
     *
     * @param arguments аргументы команды (не должны содержать значений)
     * @return результат выполнения команды ({@link ExecutionResponse}):
     * строковое представление коллекции или сообщение об ошибке
     */
    @Override
    public ExecutionResponse apply(String[] arguments) {
        if (!arguments[1].isEmpty())
            return new ExecutionResponse(false, "Неправильное количество аргументов!\nИспользование: '" + getName() + "'");

        return new ExecutionResponse(collectionManager.toString());
    }
}