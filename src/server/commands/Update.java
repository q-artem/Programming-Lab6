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
 * Команда 'update'. Обновляет значение элемента коллекции, id которого равен заданному.
 * Реализует интерфейсы {@link Executable} и {@link Describable}.
 */
public class Update extends Command implements Executable, Describable {
    private final Console console;
    private final CollectionManager collectionManager;

    /**
     * Конструктор команды 'update'.
     *
     * @param console           консоль для взаимодействия с пользователем
     * @param collectionManager менеджер коллекции для обновления элементов
     */
    public Update(Console console, CollectionManager collectionManager) {
        super("update <key> {element}", "Обновить значение элемента коллекции, id которого равен заданному");
        this.console = console;
        this.collectionManager = collectionManager;
    }

    /**
     * Выполняет команду обновления значения элемента коллекции по заданному ключу.
     * Проверяет корректность ключа, наличие элемента, создаёт новый объект и обновляет его в коллекции.
     *
     * @param arguments аргументы команды, где arguments[1] — ключ для обновления
     * @return результат выполнения команды ({@link ExecutionResponse}):
     * успешное обновление, сообщение об ошибке или некорректных данных
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

            HumanBeing humanBeing = HumanBeingCreator.createHumanBeing(console, key);

            if (humanBeing != null && humanBeing.validate()) {
                if (collectionManager.update(humanBeing)) {
                    return new ExecutionResponse("HumanBeing по ключу " + key + " успешно обновлён!");
                } else {
                    return new ExecutionResponse(false, "Ошибка!");
                }
            } else {
                return new ExecutionResponse(false, "Значения полей HumanBeing некорректны! Создание прервано.");
            }
        } catch (NumberFormatException e) {
            return new ExecutionResponse(false, "Ключ должен быть натуральным числом больше 0!");
        }
    }
}
