package common.utility;

/**
 * Класс-результат выполнения команды.
 * Содержит код завершения и сообщение о результате выполнения.
 */
public class ExecutionResponse {
    /**
     * Код завершения выполнения (true — успешно, false — ошибка).
     */
    private final boolean exitCode;
    /**
     * Сообщение о результате выполнения.
     */
    private final String message;

    /**
     * Конструктор результата выполнения с указанием кода завершения и сообщения.
     *
     * @param code код завершения (true — успешно, false — ошибка)
     * @param s    сообщение о результате
     */
    public ExecutionResponse(boolean code, String s) {
        exitCode = code;
        message = s;
    }

    /**
     * Конструктор результата выполнения с успешным кодом завершения.
     *
     * @param s сообщение о результате
     */
    public ExecutionResponse(String s) {
        this(true, s);
    }

    /**
     * Получить код завершения выполнения.
     *
     * @return true — успешно, false — ошибка
     */
    public boolean getExitCode() {
        return exitCode;
    }

    /**
     * Получить сообщение о результате выполнения.
     *
     * @return сообщение
     */
    public String getMessage() {
        return message;
    }

    /**
     * Возвращает строковое представление результата выполнения.
     *
     * @return строка с кодом завершения и сообщением
     */
    @Override
    public String toString() {
        return exitCode + "; " + message;
    }
}