package client.commands;

import common.Command;
import common.utility.Describable;
import common.utility.Executable;
import common.utility.ExecutionResponse;
import common.utility.Console;

/**
 * Команда 'execute_script'. Выполняет скрипт из указанного файла.
 * Реализует интерфейсы {@link Executable} и {@link Describable}.
 */
public class ExecuteScript extends Command implements Executable, Describable {

    /**
     * Конструктор команды 'execute_script'.
     *
     * @param ignoredConsole консоль (не используется в этой команде)
     */
    public ExecuteScript(Console ignoredConsole) {
        super("execute_script <file_name>", "выполнить скрипт из указанного файла");
    }

    /**
     * Выполняет команду запуска скрипта.
     * Проверяет наличие аргумента с именем файла.
     *
     * @param arguments аргументы команды (ожидается имя файла скрипта)
     * @return результат выполнения команды ({@link ExecutionResponse})
     * с сообщением о начале выполнения скрипта
     */
    @Override
    public ExecutionResponse apply(String[] arguments) {
        if (arguments[1].isEmpty())
            return new ExecutionResponse(false, "Неправильное количество аргументов!\nИспользование: '" + getName() + "'");

        return new ExecutionResponse("Выполнение скрипта '" + arguments[1] + "'...");
    }
}