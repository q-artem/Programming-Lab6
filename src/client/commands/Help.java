package client.commands;

import common.Command;
import client.managers.CommandManager;
import common.utility.Describable;
import common.utility.Executable;
import common.utility.ExecutionResponse;
import client.utility.console.Console;

import java.util.stream.Collectors;

/**
 * Команда 'help'. Выводит справку по всем доступным командам.
 * Реализует интерфейсы {@link Executable} и {@link Describable}.
 */
public class Help extends Command implements Executable, Describable {
    private final CommandManager commandManager;

    /**
     * Конструктор команды 'help'.
     *
     * @param ignoredConsole консоль (не используется в этой команде)
     * @param commandManager менеджер команд, предоставляющий список доступных команд
     */
    public Help(Console ignoredConsole, CommandManager commandManager) {
        super("help", "вывести справку по доступным командам");
        this.commandManager = commandManager;
    }

    /**
     * Выполняет команду вывода справки.
     * Формирует и возвращает список всех доступных команд с их описанием.
     *
     * @param arguments аргументы команды (не должны содержать значений)
     * @return результат выполнения команды ({@link ExecutionResponse}) со справкой по командам
     */
    @Override
    public ExecutionResponse apply(String[] arguments) {
        if (!arguments[1].isEmpty())
            return new ExecutionResponse(false, "Неправильное количество аргументов!\nИспользование: '" + getName() + "'");

        return new ExecutionResponse(commandManager.getCommands().values().stream().map(command -> String.format(" %-35s%-1s%n", command.getName(), command.getDescription())).collect(Collectors.joining("\n")));
    }
}