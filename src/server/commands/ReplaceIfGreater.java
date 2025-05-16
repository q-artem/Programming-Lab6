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
 * Команда 'replace_if_greater'. Заменяет элемент по ключу, если новое значение больше старого.
 * Реализует интерфейсы {@link Executable} и {@link Describable}.
 */
public class ReplaceIfGreater extends Command implements Executable, Describable {
    private final Console console;
    private final CollectionManager collectionManager;

    /**
     * Конструктор команды 'replace_if_greater'.
     *
     * @param console           консоль для взаимодействия с пользователем
     * @param collectionManager менеджер коллекции для замены элементов
     */
    public ReplaceIfGreater(Console console, CollectionManager collectionManager) {
        super("replace_if_greater <key> {element}", "заменить значение по ключу, если новое значение больше старого");
        this.console = console;
        this.collectionManager = collectionManager;
    }

    /**
     * Выполняет команду замены элемента по ключу, если новое значение больше старого.
     * Проверяет корректность ключа, наличие элемента, создаёт новый элемент и сравнивает его со старым.
     * Замена производится только если новое значение больше старого по заданным критериям.
     *
     * @param arguments аргументы команды, где arguments[1] — ключ для замены
     * @return результат выполнения команды ({@link ExecutionResponse}):
     * успешная замена, сообщение об ошибке или некорректных данных
     */
    @Override
    public ExecutionResponse apply(String[] arguments) {
        if (arguments.length < 2) {
            return new ExecutionResponse(false, "Необходимо указать ключ!\nИспользование: '" + getName() + "'");
        }
        try {
            int key = Integer.parseInt(arguments[1]);
            HumanBeing oldHuman = collectionManager.getById(key);
            if (oldHuman == null) {
                return new ExecutionResponse(false, "Элемент с таким ключом не найден!");
            }
            HumanBeing newHuman = HumanBeingCreator.createHumanBeing(console, null);
            if (newHuman != null && newHuman.validate()) {
                if (compareHumanBeings(newHuman, oldHuman)) {
                    if (collectionManager.update(newHuman)) {
                        return new ExecutionResponse(true, "Элемент успешно заменён!");
                    } else {
                        return new ExecutionResponse(false, "Ошибка!");
                    }
                } else {
                    return new ExecutionResponse(false, "Новое значение не больше старого - замена не произведена.");
                }
            } else {
                return new ExecutionResponse(false, "Значения полей HumanBeing некорректны! Создание прервано.");
            }
        } catch (NumberFormatException e) {
            return new ExecutionResponse(false, "Ключ должен быть натуральным числом больше 0!");
        }
    }

    /**
     * Сравнивает два объекта HumanBeing по ImpactSpeed, MinutesOfWaiting и Name.
     * Сначала сравнивает ImpactSpeed, затем MinutesOfWaiting, затем Name.
     *
     * @param newHuman новый объект HumanBeing
     * @param oldHuman старый объект HumanBeing
     * @return true если newHuman больше oldHuman, false если меньше или если равны
     */
    private boolean compareHumanBeings(HumanBeing newHuman, HumanBeing oldHuman) {
        float speedCompare = newHuman.getImpactSpeed() - oldHuman.getImpactSpeed();
        if (speedCompare > 0) return true;

        double waitingCompare = (newHuman.getMinutesOfWaiting() != null ? newHuman.getMinutesOfWaiting() : 0) -
                (oldHuman.getMinutesOfWaiting() != null ? oldHuman.getMinutesOfWaiting() : 0);
        if (waitingCompare > 0) return true;

        return newHuman.getName().compareTo(oldHuman.getName()) > 0;
    }
}
