package client.commands;

import common.Command;
import client.managers.CollectionManager;
import common.Car;
import common.HumanBeing;
import common.utility.Describable;
import common.utility.Executable;
import common.utility.ExecutionResponse;
import common.utility.Console;

/**
 * Команда 'filter_less_than_car'. Выводит элементы коллекции, значение поля car которых меньше заданного.
 * Реализует интерфейсы {@link Executable} и {@link Describable}.
 */
public class FilterLessThanCar extends Command implements Executable, Describable {
    private final CollectionManager collectionManager;

    /**
     * Конструктор команды 'filter_less_than_car'.
     *
     * @param ignoredConsole    консоль (не используется в этой команде)
     * @param collectionManager менеджер коллекции для фильтрации
     */
    public FilterLessThanCar(Console ignoredConsole, CollectionManager collectionManager) {
        super("filter_less_than_car <car>", "вывести элементы, значение поля car которых меньше заданного");
        this.collectionManager = collectionManager;
    }

    /**
     * Выполняет команду фильтрации элементов по полю car.
     * Сравнивает значения поля car каждого элемента коллекции с заданным значением.
     *
     * @param arguments аргументы команды, где arguments[1] — значение car для сравнения
     * @return результат выполнения команды ({@link ExecutionResponse}) с найденными элементами
     * или сообщением, если таких элементов нет
     */
    @Override
    public ExecutionResponse apply(String[] arguments) {
        if (arguments.length < 2) {
            return new ExecutionResponse(false, "Необходимо указать значение car!\nИспользование: '" + getName() + "'");
        }
        String carValue = arguments[1];

        Car compareCar = new Car.Builder().name(carValue).build();

        StringBuilder result = new StringBuilder();
        for (HumanBeing h : collectionManager.getCollection().values()) {
            if (h.getCar() != null && h.getCar().getName().compareTo(compareCar.getName()) < 0) {
                result.append(h).append("\n");
            }
        }
        String output = result.isEmpty() ? "Нет элементов, car которых меньше " + carValue : result.toString();
        return new ExecutionResponse(output);
    }
}
