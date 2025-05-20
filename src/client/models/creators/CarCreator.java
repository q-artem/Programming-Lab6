package client.models.creators;

import common.Car;
import common.utility.Console;

/**
 * Класс-утилита для создания объектов {@link Car} с помощью пользовательского ввода.
 * Использует консоль для пошагового ввода и валидации полей машины.
 */
public class CarCreator {
    /**
     * Запускает процесс создания объекта {@link Car} с помощью консоли.
     * Пользователю предлагается создать машину или отказаться. Если выбрано создание, происходит ввод и валидация названия.
     *
     * @param console консоль для взаимодействия с пользователем
     * @return созданный объект Car или null, если создание отменено
     */
    public static Car createCar(Console console) {
        Car car = null;
        Car.Builder builder = new Car.Builder();
        console.println("Инициализировано создание машины (car)");

        boolean correctField = true;
        while (correctField) {
            switch (console.getUserValueString("Создать машину? 1 - да, 2 - нет")) {
                case ("1"):
                    builder.name(askName(console));
                    car = builder.build();
                    correctField = false;
                    break;
                case ("2"):
                    return null;
            }
        }
        return car;
    }

    /**
     * Запрашивает у пользователя название машины и валидирует его.
     * Повторяет запрос до тех пор, пока не будет введено корректное название.
     *
     * @param console консоль для взаимодействия с пользователем
     * @return корректное название машины
     */
    private static String askName(Console console) {
        String name = null;
        while (!Car.validateName(name)) {
            name = console.getUserValueString("Введите название машины (name). Не может быть пустым");
            if (!Car.validateName(name)) {
                console.printError("Некорректный ввод! Попробуйте ещё раз");
            }
        }
        return name;
    }
}
