package client.models.creators;

import common.Coordinates;
import client.utility.console.Console;

/**
 * Класс-утилита для создания объектов {@link Coordinates} с помощью пользовательского ввода.
 * Использует консоль для пошагового ввода и валидации координат.
 */
public class CoordinatesCreator {
    /**
     * Запускает процесс создания объекта {@link Coordinates} с помощью консоли.
     * Пользователь вводит значения x и y с валидацией.
     *
     * @param console консоль для взаимодействия с пользователем
     * @return созданный объект Coordinates
     */
    public static Coordinates createCoordinates(Console console) {
        Coordinates.Builder builder = new Coordinates.Builder();
        console.println("Инициализировано создание координат (coordinates). Координаты обязательны");
        builder.x(askX(console));
        builder.y(askY(console));
        return builder.build();
    }

    /**
     * Запрашивает у пользователя координату x и валидирует её.
     * Повторяет запрос до тех пор, пока не будет введено корректное значение (> -167).
     *
     * @param console консоль для взаимодействия с пользователем
     * @return корректное значение x
     */
    private static long askX(Console console) {
        Long x = null;
        while (!Coordinates.validateX(x)) {
            x = console.getUserValueLong("Введите координату x (x). Пример ввода: 314. Должно быть больше -167. Не может быть пустым");
            if (!Coordinates.validateX(x)) {
                console.printError("Некорректный ввод! Попробуйте ещё раз");
            }
        }
        return x;
    }

    /**
     * Запрашивает у пользователя координату y.
     * Значение может быть не задано (null).
     *
     * @param console консоль для взаимодействия с пользователем
     * @return значение y или null, если не задано
     */
    private static Float askY(Console console) {
        return console.getUserValueFloat("Введите координату y (y). Пример ввода: 3.14. По умолчанию - нет значения");
    }
}
