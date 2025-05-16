package client.utility.console;

import java.util.NoSuchElementException;
import java.util.Scanner;

/**
 * Реализация интерфейса {@link Console} для стандартной работы с консолью.
 * Позволяет выводить и считывать данные, обрабатывать ошибки, работать с файлами и стандартным вводом.
 */
public class StandartConsole implements Console {
    /**
     * Строка-приглашение для ввода команды.
     */
    private static final String prompt = "-> ";
    /**
     * Сканер для чтения из файла (если выбран режим работы с файлом).
     */
    private static Scanner fileScanner = null;
    /**
     * Стандартный сканер для чтения из консоли.
     */
    private static final Scanner defScanner = new Scanner(System.in);

    /**
     * Выводит объект в стандартный поток вывода без перехода на новую строку.
     *
     * @param obj объект для вывода
     */
    @Override
    public void print(Object obj) {
        System.out.print(obj);
    }

    /**
     * Выводит объект в стандартный поток вывода с переходом на новую строку.
     *
     * @param obj объект для вывода
     */
    @Override
    public void println(Object obj) {
        System.out.println(obj);
    }

    /**
     * Выводит сообщение об ошибке в поток ошибок с префиксом "Error:".
     *
     * @param obj объект для вывода ошибки
     */
    public void printError(Object obj) {
        System.err.println("Error: " + obj);
    }

    /**
     * Считывает строку, введённую пользователем или считанную из файла.
     *
     * @return введённая строка без начальных и конечных пробелов
     * @throws NoSuchElementException если ввод недоступен
     * @throws IllegalStateException  если сканер закрыт
     */
    @Override
    public String readln() throws NoSuchElementException, IllegalStateException {
        return (fileScanner != null ? fileScanner : defScanner).nextLine().trim();
    }

    /**
     * Проверяет, возможно ли считать строку из текущего источника ввода.
     *
     * @return true, если есть следующая строка для чтения
     * @throws IllegalStateException если сканер закрыт
     */
    @Override
    public boolean isCanReadln() throws IllegalStateException {
        return (fileScanner != null ? fileScanner : defScanner).hasNextLine();
    }

    /**
     * Запрашивает строку у пользователя с выводом сообщения и приглашения.
     *
     * @param mess сообщение-подсказка
     * @return введённая строка
     */
    private String getString(String mess) {
        this.println(mess);
        this.print(prompt);
        return this.readln();
    }

    /**
     * Выводит таблицу, используя два объекта (например, для форматированного вывода данных).
     *
     * @param elementLeft  первый элемент
     * @param elementRight второй элемент
     */
    @Override
    public void printTable(Object elementLeft, Object elementRight) {
        System.out.printf(" %-35s%-1s%n", elementLeft, elementRight);
    }

    /**
     * Запрашивает у пользователя строковое значение с сообщением-подсказкой.
     *
     * @param mess сообщение-подсказка
     * @return введённая строка
     */
    @Override
    public String getUserValueString(String mess) {
        return getString(mess);
    }

    /**
     * Запрашивает у пользователя значение типа Float с сообщением-подсказкой и валидацией.
     *
     * @param mess сообщение-подсказка
     * @return корректное значение Float или null, если не задано
     */
    @Override
    public Float getUserValueFloat(String mess) {
        float val = 0f;
        boolean floatInput = true;
        while (floatInput) {
            try {
                String input = getString(mess).replace(",", ".");
                if (input.isEmpty()) {
                    return null;
                }
                val = Float.parseFloat(input);
                floatInput = false;
            } catch (NumberFormatException exception) {
                this.println("Ошибочный формат числа! Ошибка: " + exception.getMessage());
            }
        }
        return val;
    }

    /**
     * Запрашивает у пользователя значение типа Double с сообщением-подсказкой и валидацией.
     *
     * @param mess сообщение-подсказка
     * @return корректное значение Double или null, если не задано
     */
    @Override
    public Double getUserValueDouble(String mess) {
        double val = 0.0;
        boolean doubleInput = true;
        while (doubleInput) {
            try {
                String input = getString(mess).replace(",", ".");
                if (input.isEmpty()) {
                    return null;
                }
                val = Double.parseDouble(input);
                doubleInput = false;
            } catch (NumberFormatException exception) {
                this.println("Ошибочный формат числа! Ошибка: " + exception.getMessage());
            }
        }
        return val;
    }

    /**
     * Запрашивает у пользователя значение типа Long с сообщением-подсказкой и валидацией.
     *
     * @param mess сообщение-подсказка
     * @return корректное значение Long или null, если не задано
     */
    @Override
    public Long getUserValueLong(String mess) {
        long val = 0L;
        boolean longInput = true;
        while (longInput) {
            try {
                String input = getString(mess);
                if (input.isEmpty()) {
                    return null;
                }
                val = Long.parseLong(input);
                longInput = false;
            } catch (NumberFormatException exception) {
                this.println("Ошибочный формат числа! Ошибка: " + exception.getMessage());
            }
        }
        return val;
    }

    /**
     * Выводит приглашение к вводу команды.
     */
    @Override
    public void prompt() {
        System.out.print(prompt);
    }

    /**
     * Возвращает строку-приглашение для ввода команды.
     *
     * @return строка-приглашение
     */
    @Override
    public String getPrompt() {
        return prompt;
    }

    /**
     * Переключает источник ввода на сканер файла.
     *
     * @param scanner объект Scanner для чтения из файла
     */
    @Override
    public void selectFileScanner(Scanner scanner) {
        fileScanner = scanner;
    }

    /**
     * Переключает источник ввода на стандартную консоль.
     */
    @Override
    public void selectConsoleScanner() {
        fileScanner = null;
    }

    /**
     * Закрывает используемый сканер файла, если он был открыт.
     */
    public void close() {
        if (fileScanner != null) {
            fileScanner.close();
        }
    }
}