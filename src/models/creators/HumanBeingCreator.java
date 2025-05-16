package models.creators;

import models.HumanBeing;
import models.WeaponType;
import utility.console.Console;

import java.time.LocalDate;
import java.util.Map;

/**
 * Класс-утилита для создания объектов {@link HumanBeing} с помощью пользовательского ввода.
 * Использует консоль для пошагового ввода и валидации всех полей персонажа.
 */
public class HumanBeingCreator {
    /**
     * Карта для преобразования строкового ввода в булевы значения (1 — true, 2 — false).
     */
    static private final Map<String, Boolean> trueFalseMap = Map.of("1", true, "2", false);
    /**
     * Карта для выбора типа оружия по номеру.
     */
    static private final Map<String, WeaponType> weaponTypes = Map.of("1", WeaponType.HAMMER, "2", WeaponType.AXE, "3", WeaponType.KNIFE);

    /**
     * Запускает процесс создания объекта {@link HumanBeing} с помощью консоли.
     * Пользователь поэтапно вводит значения всех полей, включая вложенные объекты.
     *
     * @param console консоль для взаимодействия с пользователем
     * @param id      идентификатор (может быть null для автогенерации)
     * @return созданный объект HumanBeing
     */
    public static HumanBeing createHumanBeing(Console console, Integer id) {
        console.println("Инициализировано создание Персонажа (HumanBeing)");
        HumanBeing.Builder builder;
        if (id != null) {
            builder = new HumanBeing.Builder(id, LocalDate.now());
        } else {
            builder = new HumanBeing.Builder();
        }
        builder.name(askName(console))
                .coordinates(CoordinatesCreator.createCoordinates(console))
                .realHero(askRealHero(console))
                .hasToothpick(askHasToothpick(console))
                .impactSpeed(askImpactSpeed(console))
                .soundtrackName(askSoundtrackName(console))
                .minutesOfWaiting(askMinutesOfWaiting(console))
                .weaponType(askWeaponType(console))
                .car(CarCreator.createCar(console));
        return builder.build();
    }

    /**
     * Запрашивает у пользователя имя персонажа и валидирует его.
     * Повторяет запрос до тех пор, пока не будет введено корректное имя.
     *
     * @param console консоль для взаимодействия с пользователем
     * @return корректное имя
     */
    private static String askName(Console console) {
        String name = null;
        while (!HumanBeing.validateName(name)) {
            name = console.getUserValueString("Введите имя (name). Не может быть пустым");
            if (!HumanBeing.validateName(name)) {
                console.printError("Некорректный ввод! Попробуйте ещё раз");
            }
        }
        return name;
    }

    /**
     * Запрашивает у пользователя, является ли персонаж героем.
     *
     * @param console консоль для взаимодействия с пользователем
     * @return true, false или null, если не задано
     */
    private static Boolean askRealHero(Console console) {
        Boolean realHero = null;
        String userRequest;
        while (realHero == null) {
            userRequest = console.getUserValueString("Введите является ли героем (realHero)? 1 - да, 2 - нет. По умолчанию - нет значения");
            if (userRequest.isEmpty()) {
                break;
            }
            realHero = trueFalseMap.get(userRequest);
            if (realHero == null) {
                console.printError("Некорректный ввод! Попробуйте ещё раз");
            }
        }
        return realHero;
    }

    /**
     * Запрашивает у пользователя, есть ли у персонажа зубочистка.
     *
     * @param console консоль для взаимодействия с пользователем
     * @return true, false или null, если не задано
     */
    private static Boolean askHasToothpick(Console console) {
        Boolean hasToothpick = null;
        String userRequest;
        while (hasToothpick == null) {
            userRequest = console.getUserValueString("Введите есть ли зубочистка (hasToothpick)? 1 - да, 2 - нет. По умолчанию - нет значения");
            if (userRequest.isEmpty()) {
                break;
            }
            hasToothpick = trueFalseMap.get(userRequest);
            if (hasToothpick == null) {
                console.printError("Некорректный ввод! Попробуйте ещё раз");
            }
        }
        return hasToothpick;
    }

    /**
     * Запрашивает у пользователя скорость удара и валидирует её.
     * Повторяет запрос до тех пор, пока не будет введено корректное значение.
     *
     * @param console консоль для взаимодействия с пользователем
     * @return корректное значение impactSpeed
     */
    private static float askImpactSpeed(Console console) {
        Float impactSpeed = null;
        while (!HumanBeing.validateImpactSpeed(impactSpeed)) {
            impactSpeed = console.getUserValueFloat("Введите скорость удара (impactSpeed). Пример ввода: 3.14. Не может быть пустым");
            if (!HumanBeing.validateImpactSpeed(impactSpeed)) {
                console.printError("Некорректный ввод! Попробуйте ещё раз");
            }
        }
        return impactSpeed;
    }

    /**
     * Запрашивает у пользователя название саундтрека и валидирует его.
     * Повторяет запрос до тех пор, пока не будет введено корректное название.
     *
     * @param console консоль для взаимодействия с пользователем
     * @return корректное название саундтрека
     */
    private static String askSoundtrackName(Console console) {
        String soundtrackName = null;
        while (!HumanBeing.validateSoundtrackName(soundtrackName)) {
            soundtrackName = console.getUserValueString("Введите название саундтрека (soundtrackName). Не может быть пустым");
            if (!HumanBeing.validateSoundtrackName(soundtrackName)) {
                console.printError("Некорректный ввод! Попробуйте ещё раз");
            }
        }
        return soundtrackName;
    }

    /**
     * Запрашивает у пользователя время ожидания в минутах и валидирует его.
     * Повторяет запрос до тех пор, пока не будет введено корректное значение.
     *
     * @param console консоль для взаимодействия с пользователем
     * @return корректное значение времени ожидания
     */
    private static Double askMinutesOfWaiting(Console console) {
        Double minutesOfWaiting = -1.0;
        while (!HumanBeing.validateMinutesOfWaiting(minutesOfWaiting)) {
            minutesOfWaiting = console.getUserValueDouble("Введите время ожидания в минутах (minutesOfWaiting). Пример ввода: 2.71. По умолчанию - нет значения");
            if (!HumanBeing.validateMinutesOfWaiting(minutesOfWaiting)) {
                console.printError("Некорректный ввод! Попробуйте ещё раз");
            }
        }
        return minutesOfWaiting;
    }

    /**
     * Запрашивает у пользователя тип оружия и валидирует выбор.
     * Повторяет запрос до тех пор, пока не будет введён корректный номер типа оружия.
     *
     * @param console консоль для взаимодействия с пользователем
     * @return выбранный тип оружия
     */
    private static WeaponType askWeaponType(Console console) {
        WeaponType weaponType = null;
        String userRequest;
        while (weaponType == null) {
            userRequest = console.getUserValueString("Введите тип оружия (weaponType)? 1 - молот, 2 - топор, 3 - нож. Не может быть пустым");
            weaponType = weaponTypes.get(userRequest);
            if (weaponType == null) {
                console.printError("Некорректный ввод! Попробуйте ещё раз");
            }
        }
        return weaponType;
    }
}
