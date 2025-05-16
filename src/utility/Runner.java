package utility;

import managers.CommandManager;
import utility.console.Console;

import java.io.File;
import java.io.FileNotFoundException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;
import java.util.NoSuchElementException;
import java.util.Scanner;

/**
 * Класс-обёртка для запуска приложения и обработки пользовательских команд.
 * Позволяет работать в интерактивном режиме, выполнять скрипты, контролировать рекурсию и историю команд.
 */
public class Runner {
    /**
     * Консоль для взаимодействия с пользователем.
     */
    private final Console console;
    /**
     * Менеджер команд приложения.
     */
    private final CommandManager commandManager;
    /**
     * Стек выполняемых скриптов для контроля рекурсии.
     */
    private final List<String> scriptStack = new ArrayList<>();
    /**
     * Максимальная глубина рекурсии (по умолчанию -1, не ограничено).
     */
    private int lengthRecursion = -1;

    /**
     * Конструктор Runner.
     *
     * @param console        консоль для взаимодействия с пользователем
     * @param commandManager менеджер команд
     */
    public Runner(Console console, CommandManager commandManager) {
        this.console = console;
        this.commandManager = commandManager;
    }

    /**
     * Запускает приложение в интерактивном режиме (работа с пользователем через консоль).
     * Ожидает ввод команд, выполняет их и выводит результат.
     * Обрабатывает ошибки ввода и завершения программы.
     */
    public void interactiveMode() {
        try {
            ExecutionResponse commandStatus;
            String[] userCommand;

            while (true) {
                console.prompt();
                userCommand = (console.readln().trim() + " ").split(" ", 2);
                userCommand[1] = userCommand[1].trim();

                commandManager.addToHistory(userCommand[0]);
                commandStatus = launchCommand(userCommand);

                if (commandStatus.getMessage().equals("exit")) break;
                console.println(commandStatus.getMessage());
            }
        } catch (NoSuchElementException exception) {
            console.printError("Пользовательский ввод не обнаружен!");
        } catch (IllegalStateException exception) {
            console.printError("Непредвиденная ошибка!");
        }
    }

    /**
     * Проверяет рекурсивность выполнения скриптов и ограничивает глубину рекурсии.
     * При необходимости запрашивает у пользователя максимальную глубину рекурсии.
     *
     * @param argument      имя запускаемого скрипта
     * @param scriptScanner сканер для чтения скрипта
     * @return true, если выполнение скрипта разрешено, иначе false
     */
    private boolean checkRecursion(String argument, Scanner scriptScanner) {
        var recStart = -1;
        var i = 0;
        for (String script : scriptStack) {
            i++;
            if (argument.equals(script)) {
                if (recStart < 0) recStart = i;
                if (lengthRecursion < 0) {
                    console.selectConsoleScanner();
                    console.println("Была замечена рекурсия! Введите максимальную глубину рекурсии (0..500)");
                    while (lengthRecursion < 0 || lengthRecursion > 500) {
                        try {
                            console.print(console.getPrompt());
                            lengthRecursion = Integer.parseInt(console.readln().trim());
                        } catch (NumberFormatException e) {
                            console.println("длина не распознана");
                        }
                    }
                    console.selectFileScanner(scriptScanner);
                }
                if (i > recStart + lengthRecursion || i > 500)
                    return false;
            }
        }
        return true;
    }

    /**
     * Запускает выполнение команд из скрипта.
     * Контролирует рекурсию, собирает вывод выполнения и возвращает результат.
     *
     * @param argument имя файла скрипта
     * @return результат выполнения скрипта
     */
    private ExecutionResponse scriptMode(String argument) {
        String[] userCommand;
        StringBuilder executionOutput = new StringBuilder();

        if (!new File(argument).exists()) return new ExecutionResponse(false, "Файл не существует!");
        if (!Files.isReadable(Paths.get(argument))) return new ExecutionResponse(false, "Прав для чтения нет!");

        scriptStack.add(argument);
        try (Scanner scriptScanner = new Scanner(new File(argument))) {

            ExecutionResponse commandStatus;

            if (!scriptScanner.hasNext()) throw new NoSuchElementException();
            console.selectFileScanner(scriptScanner);
            do {
                userCommand = (console.readln().trim() + " ").split(" ", 2);
                userCommand[1] = userCommand[1].trim();
                while (console.isCanReadln() && userCommand[0].isEmpty()) {
                    userCommand = (console.readln().trim() + " ").split(" ", 2);
                    userCommand[1] = userCommand[1].trim();
                }
                executionOutput.append(console.getPrompt()).append(String.join(" ", userCommand)).append("\n");
                var needLaunch = true;
                if (userCommand[0].equals("execute_script")) {
                    needLaunch = checkRecursion(userCommand[1], scriptScanner);
                }

                commandStatus = needLaunch ? launchCommand(userCommand) : new ExecutionResponse("Превышена максимальная глубина рекурсии");
                if (userCommand[0].equals("execute_script")) console.selectFileScanner(scriptScanner);
                executionOutput.append(commandStatus.getMessage()).append("\n");
            } while (commandStatus.getExitCode() && !commandStatus.getMessage().equals("exit") && console.isCanReadln());

            console.selectConsoleScanner();
            if (!commandStatus.getExitCode() && !(userCommand[0].equals("execute_script") && !userCommand[1].isEmpty())) {
                executionOutput.append("Проверьте скрипт на корректность введенных данных!\n");
            }

            return new ExecutionResponse(commandStatus.getExitCode(), executionOutput.toString());
        } catch (FileNotFoundException exception) {
            return new ExecutionResponse(false, "Файл со скриптом не найден!");
        } catch (NoSuchElementException exception) {
            return new ExecutionResponse(false, "Файл со скриптом пуст!");
        } catch (IllegalStateException exception) {
            console.printError("Непредвиденная ошибка!");
            System.exit(0);
        } finally {
            scriptStack.remove(scriptStack.size() - 1);
        }
        return new ExecutionResponse("");
    }

    /**
     * Выполняет команду по её названию и аргументам.
     * Обрабатывает специальные случаи (execute_script), возвращает результат выполнения.
     *
     * @param userCommand массив с названием команды и аргументами
     * @return результат выполнения команды
     */
    private ExecutionResponse launchCommand(String[] userCommand) {
        if (userCommand[0].isEmpty()) return new ExecutionResponse("");
        var command = commandManager.getCommands().get(userCommand[0]);

        if (command == null)
            return new ExecutionResponse(false, "Команда '" + userCommand[0] + "' не найдена. Наберите 'help' для справки");

        if (userCommand[0].equals("execute_script")) {
            ExecutionResponse tmp = commandManager.getCommands().get("execute_script").apply(userCommand);
            if (!tmp.getExitCode()) return tmp;
            ExecutionResponse tmp2 = scriptMode(userCommand[1]);
            return new ExecutionResponse(tmp2.getExitCode(), tmp.getMessage() + "\n" + tmp2.getMessage().trim());
        } else {
            return command.apply(userCommand);
        }
    }
}