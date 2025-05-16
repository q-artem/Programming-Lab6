package server.commands;

import HumanBeing;
import commands.utils.Command;
import models.creators.HumanBeingCreator;
import server.managers.CollectionManager;
import utility.Describable;
import utility.Executable;
import utility.ExecutionResponse;
import utility.console.Console;

/**
 * Серверная часть команды 'add'. Добавляет новый элемент в коллекцию.
 * Реализует интерфейсы {@link Executable} и {@link Describable}.
 */
public class Add extends Command implements Executable, Describable {
    private final Console console;
    private final CollectionManager collectionManager;

    /**
     * Конструктор команды 'add'.
     *
     * @param console           консоль для взаимодействия с пользователем
     * @param collectionManager менеджер коллекции для добавления элемента
     */
    public Add(Console console, CollectionManager collectionManager) {
        super("add {element}", "добавить новый элемент в коллекцию");
        this.console = console;
        this.collectionManager = collectionManager;
    }

    /**
     * Выполняет команду добавления элемента в коллекцию.
     * Создает новый объект HumanBeing через HumanBeingCreator,
     * проверяет его валидность и добавляет в коллекцию.
     *
     * @param argument аргументы команды (не используются)
     * @return результат выполнения команды ({@link ExecutionResponse})
     * с сообщением об успешности операции
     */
    @Override
    public ExecutionResponse apply(String[] argument) {
        if (!argument[1].isEmpty())
            return new ExecutionResponse(false, "Неправильное количество аргументов!\nИспользование: '" + getName() + "'");

        HumanBeing humanBeing = HumanBeingCreator.createHumanBeing(console, null);

        if (humanBeing != null && humanBeing.validate()) {
            if (collectionManager.add(humanBeing)) {
                return new ExecutionResponse("HumanBeing успешно добавлен!");
            }
            return new ExecutionResponse("HumanBeing уже содержится в коллекции (пересечение по id)!");
        }
        return new ExecutionResponse(false, "Поля HumanBeing не валидны! HumanBeing не создан!");
    }
}