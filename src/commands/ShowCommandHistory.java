package commands;

import commands.utils.Command;
import managers.CommandManager;
import utility.Describable;
import utility.Executable;
import utility.ExecutionResponse;
import utility.console.Console;

import java.util.List;

/**
 * Команда 'command_history'. Выводит историю выполненных команд.
 * Реализует интерфейсы {@link Executable} и {@link Describable}.
 */
public class ShowCommandHistory extends Command implements Executable, Describable {
    private final CommandManager commandManager;

    /**
     * Конструктор команды 'command_history'.
     *
     * @param ignoredConsole консоль (не используется в этой команде)
     * @param commandManager менеджер команд для получения истории
     */
    public ShowCommandHistory(Console ignoredConsole, CommandManager commandManager) {
        super("command_history", "вывести последние выполненные команды");
        this.commandManager = commandManager;
    }

    /**
     * Выполняет команду вывода истории выполненных команд.
     * Формирует строку с последними выполненными командами из истории.
     *
     * @param arguments аргументы команды (не используются)
     * @return результат выполнения команды ({@link ExecutionResponse}):
     * строка с историей команд
     */
    @Override
    public ExecutionResponse apply(String[] arguments) {
        List<String> history = commandManager.getCommandHistory();

        StringBuilder result = new StringBuilder("Последние выполненные команды:\n");
        for (int i = 0; i < history.size(); i++) {
            result.append(i + 1).append(". ").append(history.get(i)).append("\n");
        }

        return new ExecutionResponse(result.toString());
    }
}
