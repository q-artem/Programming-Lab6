package client.commands;

import common.Command;
import client.managers.CollectionManager;
import common.HumanBeing;
import common.utility.Describable;
import common.utility.Executable;
import common.utility.ExecutionResponse;
import common.utility.Console;

/**
 * Команда 'remove_lower'. Удаляет из коллекции все элементы, меньшие, чем заданный по id элемент.
 * Реализует интерфейсы {@link Executable} и {@link Describable}.
 */
public class RemoveLower extends Command implements Executable, Describable {
    private final CollectionManager collectionManager;

    /**
     * Конструктор команды 'remove_lower'.
     *
     * @param ignoredConsole    консоль (не используется в этой команде)
     * @param collectionManager менеджер коллекции для удаления элементов
     */
    public RemoveLower(Console ignoredConsole, CollectionManager collectionManager) {
        super("remove_lower <key>", "удалить из коллекции все элементы, меньшие, чем заданный по id элемент");
        this.collectionManager = collectionManager;
    }

    /**
     * Выполняет команду удаления всех элементов, меньших, чем заданный по id элемент.
     * Проверяет корректность ключа, наличие элемента для сравнения и удаляет все элементы, меньшие заданного.
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

            HumanBeing comparisonHumanBeing = collectionManager.getById(key);
            if (comparisonHumanBeing == null) {
                return new ExecutionResponse(false, "Элемента с таким ключом не существует!");
            }

            int count = 0;
            for (HumanBeing h : collectionManager.getCollection().values()) {
                if (h.compareTo(comparisonHumanBeing) < 0) {
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
