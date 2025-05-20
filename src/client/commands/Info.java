package client.commands;

import common.Command;
import client.managers.CollectionManager;
import common.utility.Describable;
import common.utility.Executable;
import common.utility.ExecutionResponse;
import common.utility.Console;

import java.time.LocalDateTime;

/**
 * Команда 'info'. Выводит информацию о коллекции: тип, количество элементов, дата последней инициализации и сохранения.
 * Реализует интерфейсы {@link Executable} и {@link Describable}.
 */
public class Info extends Command implements Executable, Describable {
    private final CollectionManager collectionManager;

    /**
     * Конструктор команды 'info'.
     *
     * @param ignoredConsole    консоль (не используется в этой команде)
     * @param collectionManager менеджер коллекции для получения информации
     */
    public Info(Console ignoredConsole, CollectionManager collectionManager) {
        super("info", "вывести информацию о коллекции");
        this.collectionManager = collectionManager;
    }

    /**
     * Выполняет команду вывода информации о коллекции.
     * Формирует сведения о типе коллекции, количестве элементов,
     * дате последней инициализации и сохранения.
     *
     * @param arguments аргументы команды (не должны содержать значений)
     * @return результат выполнения команды ({@link ExecutionResponse}) со сведениями о коллекции
     */
    @Override
    public ExecutionResponse apply(String[] arguments) {
        if (!arguments[1].isEmpty())
            return new ExecutionResponse(false, "Неправильное количество аргументов!\nИспользование: '" + getName() + "'");

        LocalDateTime lastInitTime = collectionManager.getLastInitTime();
        String lastInitTimeString = (lastInitTime == null) ? "в данной сессии инициализации еще не происходило" :
                lastInitTime.toLocalDate().toString() + " " + lastInitTime.toLocalTime().toString();

        LocalDateTime lastSaveTime = collectionManager.getLastSaveTime();
        String lastSaveTimeString = (lastSaveTime == null) ? "в данной сессии сохранения еще не происходило" :
                lastSaveTime.toLocalDate().toString() + " " + lastSaveTime.toLocalTime().toString();

        return buildResponse(lastInitTimeString, lastSaveTimeString);
    }

    /**
     * Формирует строку с информацией о коллекции для вывода пользователю.
     *
     * @param lastInitTimeString строка с датой последней инициализации
     * @param lastSaveTimeString строка с датой последнего сохранения
     * @return объект {@link ExecutionResponse} с информацией о коллекции
     */
    private ExecutionResponse buildResponse(String lastInitTimeString, String lastSaveTimeString) {
        var s = "Сведения о коллекции:\n";
        s += " Тип: " + collectionManager.getCollection().getClass() + "\n";
        s += " Количество элементов: " + collectionManager.getCollection().size() + "\n";
        s += " Дата последнего сохранения: " + lastSaveTimeString + "\n";
        s += " Дата последней инициализации: " + lastInitTimeString;

        return new ExecutionResponse(s);
    }
}
