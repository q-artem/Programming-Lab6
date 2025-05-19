package client.commands;

import common.Command;
import common.utility.Describable;
import common.utility.Executable;
import common.utility.ExecutionResponse;
import client.utility.console.Console;

/**
 * Команда 'exit'. Завершает выполнение программы без сохранения данных.
 * Реализует интерфейсы {@link Executable} и {@link Describable}.
 */
public class Exit extends Command implements Executable, Describable {

    /**
     * Конструктор команды 'exit'.
     *
     * @param ignoredConsole консоль (не используется в этой команде)
     */
    public Exit(Console ignoredConsole) {
        super("exit", "завершить программу (без сохранения в файл)");
    }

    /**
     * Выполняет команду завершения программы.
     * Проверяет отсутствие лишних аргументов и возвращает специальный ответ для завершения.
     *
     * @param arguments аргументы команды (не должны содержать значений)
     * @return результат выполнения команды ({@link ExecutionResponse})
     */
    @Override
    public ExecutionResponse apply(String[] arguments) {
        if (!arguments[1].isEmpty())
            return new ExecutionResponse(false, "Неправильное количество аргументов!\nИспользование: '" + getName() + "'");

        return new ExecutionResponse("exit");
    }
}