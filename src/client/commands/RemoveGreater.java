package client.commands;

import common.Command;
import client.managers.CollectionManager;
import common.HumanBeing;
import common.utility.Describable;
import common.utility.Executable;
import common.utility.ExecutionResponse;
import client.utility.console.Console;

/**
 * Команда 'remove_greater'. Удаляет из коллекции все элементы, превышающие заданный по id элемент.
 * Реализует интерфейсы {@link Executable} и {@link Describable}.
 */
public class RemoveGreater extends Command implements Executable, Describable {
    private final CollectionManager collectionManager;

    /**
     * Конструктор команды 'remove_greater'.
     *
     * @param ignoredConsole    консоль (не используется в этой команде)
     * @param collectionManager менеджер коллекции для удаления элементов
     */
    public RemoveGreater(Console ignoredConsole, CollectionManager collectionManager) {
        super("remove_greater <key>", "удалить из коллекции все элементы, превышающие заданный по id элемент");
        this.collectionManager = collectionManager;
    }

    /**
     * Выполняет команду удаления всех элементов, превышающих заданный по id элемент.
     * Проверяет корректность ключа, наличие элемента для сравнения и удаляет все элементы, большие заданного.
     *
     * @param arguments аргументы команды, где arguments[1] — ключ для сравнения
     * @return результат выполнения команды ({@link ExecutionResponse}):
     * количество удалённых элементов или сообщение об ошибке
     */
    @Override
    public ExecutionResponse apply(String[] arguments) {
        if (arguments.length < 2) {
            return new ExecutionResponse(false, "Ключ должен быть указан!\nИспользование: '" + getName() + "'");
        }

        try {
            int key = Integer.parseInt(arguments[1]);
            if (key < 1) throw new NumberFormatException();

            HumanBeing comparationHumanBeing = collectionManager.getById(key);
            if (collectionManager.getById(key) == null) {
                return new ExecutionResponse(false, "Элемента с таким ключом не существует!");
            }

            int count = 0;
            for (HumanBeing h : collectionManager.getCollection().values()) {
                if (h.compareTo(comparationHumanBeing) > 0) {
                    if (collectionManager.remove(h.getId())) {
                        count++;
                    } else {
                        return new ExecutionResponse(false, "Ошибка!");
                    }
                }
            }
            return new ExecutionResponse("Удалено элементов: " + count);

        } catch (NumberFormatException e) {
            return new ExecutionResponse(false, "Ключ должен быть натуральным числом больше 0!");
        }
    }
}
