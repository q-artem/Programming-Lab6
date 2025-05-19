package client.managers;

import common.Command;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Класс-менеджер для управления командами приложения и историей их выполнения.
 * Позволяет регистрировать команды, получать их список и историю, а также добавлять команды в историю.
 */
public class CommandManager {
    /**
     * Словарь зарегистрированных команд (имя команды — объект команды).
     */
    private final Map<String, Command> commands = new HashMap<>();
    /**
     * История выполненных команд (список имён команд).
     */
    private final List<String> commandHistory = new ArrayList<>();

    /**
     * Добавляет команду.
     *
     * @param commandName Название команды.
     * @param command     Команда.
     */
    public void register(String commandName, Command command) {
        commands.put(commandName, command);
    }

    /**
     * Возвращает словарь зарегистрированных команд.
     *
     * @return Словарь команд.
     */
    public Map<String, Command> getCommands() {
        return commands;
    }

    /**
     * Возвращает историю выполненных команд.
     *
     * @return История команд.
     */
    public List<String> getCommandHistory() {
        return commandHistory;
    }

    /**
     * Добавляет команду в историю выполнения.
     *
     * @param command Имя выполненной команды.
     */
    public void addToHistory(String command) {
        commandHistory.add(command);
    }
}