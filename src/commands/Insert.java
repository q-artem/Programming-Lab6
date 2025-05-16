package commands;

import commands.utils.Command;
import managers.CollectionManager;
import models.HumanBeing;
import models.creators.HumanBeingCreator;
import utility.Describable;
import utility.Executable;
import utility.ExecutionResponse;
import utility.console.Console;

/**
 * Команда 'insert'. Добавляет новый элемент в коллекцию по указанному ключу.
 * Реализует интерфейсы {@link Executable} и {@link Describable}.
 */
public class Insert extends Command implements Executable, Describable {
    private final Console console;
    private final CollectionManager collectionManager;

    /**
     * Конструктор команды 'insert'.
     *
     * @param console           консоль для взаимодействия с пользователем
     * @param collectionManager менеджер коллекции для вставки элемента
     */
    public Insert(Console console, CollectionManager collectionManager) {
        super("insert <key> {element}", "Добавить в коллекцию новый элемент с заданным ключом");
        this.console = console;
        this.collectionManager = collectionManager;
    }

    /**
     * Выполняет команду вставки нового элемента по заданному ключу.
     * Проверяет корректность ключа, отсутствие дубликата и валидность нового элемента.
     *
     * @param arguments аргументы команды, где arguments[1] — ключ для вставки
     * @return результат выполнения команды ({@link ExecutionResponse}):
     * успешное добавление, сообщение о дубликате или ошибке формата/валидации
     */
    @Override
    public ExecutionResponse apply(String[] arguments) {
        if (arguments.length < 2) {
            return new ExecutionResponse(false, "Ключ должен быть указан!\nИспользование: '" + getName() + "'");
        }

        try {
            int key = Integer.parseInt(arguments[1]);
            if (key < 1) throw new NumberFormatException();

            if (collectionManager.getById(key) != null) {
                return new ExecutionResponse(false, "Элемент с таким ключом уже существует!");
            }

            HumanBeing humanBeing = HumanBeingCreator.createHumanBeing(console, key);

            if (humanBeing != null && humanBeing.validate()) {
                collectionManager.getCollection().put(key, humanBeing);
                return new ExecutionResponse("HumanBeing успешно добавлен с ключом " + key + "!");
            } else {
                return new ExecutionResponse(false, "Значения полей HumanBeing некорректны! Создание прервано.");
            }
        } catch (NumberFormatException e) {
            return new ExecutionResponse(false, "Ключ должен быть натуральным числом больше 0!");
        }
    }
}
