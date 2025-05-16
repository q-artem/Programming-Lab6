package server.commands;

import commands.utils.Command;
import server.managers.CollectionManager;
import utility.Describable;
import utility.Executable;
import utility.ExecutionResponse;
import utility.console.Console;

/**
 * Команда 'remove'. Удаляет элемент из коллекции по указанному ключу.
 * Реализует интерфейсы {@link Executable} и {@link Describable}.
 */
public class RemoveKey extends Command implements Executable, Describable {
    private final CollectionManager collectionManager;

    /**
     * Конструктор команды 'remove'.
     *
     * @param ignoredConsole    консоль (не используется в этой команде)
     * @param collectionManager менеджер коллекции для удаления элемента
     */
    public RemoveKey(Console ignoredConsole, CollectionManager collectionManager) {
        super("remove_key <key>", "Удалить из коллекции элемент с заданным ключом");
        this.collectionManager = collectionManager;
    }

    /**
     * Выполняет команду удаления элемента по заданному ключу.
     * Проверяет корректность ключа, наличие элемента и удаляет его из коллекции.
     *
     * @param arguments аргументы команды, где arguments[1] — ключ для удаления
     * @return результат выполнения команды ({@link ExecutionResponse}):
     * успешное удаление, сообщение об ошибке или некорректном ключе
     */
    @Override
    public ExecutionResponse apply(String[] arguments) {
        if (arguments.length < 2) {
            return new ExecutionResponse(false, "Ключ должен быть указан!\nИспользование: '" + getName() + "'");
        }

        try {
            int key = Integer.parseInt(arguments[1]);
            if (key < 1) throw new NumberFormatException();

            if (collectionManager.getById(key) == null) {
                return new ExecutionResponse(false, "Элемента с таким ключом не существует!");
            }

            if (collectionManager.remove(key)) {
                return new ExecutionResponse("HumanBeing с ключом " + key + " успешно удалён из коллекции!");
            } else {
                return new ExecutionResponse(false, "Ошибка!");
            }
        } catch (NumberFormatException e) {
            return new ExecutionResponse(false, "Ключ должен быть натуральным числом больше 0!");
        }
    }
}
