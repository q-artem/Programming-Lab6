package common.utility;

import client.utility.ExecutionResponse;

/**
 * Интерфейс для всех выполняемых команд.
 * Определяет метод для выполнения действия с передачей аргументов.
 */
public interface Executable {
    /**
     * Выполнить команду с указанными аргументами.
     *
     * @param arguments аргументы для выполнения
     * @return результат выполнения команды в виде {@link ExecutionResponse}
     */
    ExecutionResponse apply(String[] arguments);
}