package common;

import common.utility.Describable;
import common.utility.Executable;

/**
 * Абстрактный класс для реализации команды приложения.
 * Определяет основные свойства и методы для описания и идентификации команды.
 * Реализует интерфейсы {@link Executable} и {@link Describable}.
 */
public abstract class Command implements Executable, Describable {
    private final String name;
    private final String description;

    /**
     * Конструктор команды.
     *
     * @param name        название и синтаксис использования команды
     * @param description описание команды
     */
    public Command(String name, String description) {
        this.name = name;
        this.description = description;
    }

    /**
     * Возвращает название и использование команды.
     *
     * @return название команды
     */
    public String getName() {
        return name;
    }

    /**
     * Возвращает описание команды.
     *
     * @return описание команды
     */
    public String getDescription() {
        return description;
    }

    /**
     * Проверяет равенство команд по имени и описанию.
     *
     * @param obj объект для сравнения
     * @return true, если команды совпадают по имени и описанию
     */
    @Override
    public boolean equals(Object obj) {
        if (this == obj) return true;
        if (obj == null || getClass() != obj.getClass()) return false;
        Command command = (Command) obj;
        return name.equals(command.name) && description.equals(command.description);
    }

    /**
     * Возвращает хэш-код команды на основе имени и описания.
     *
     * @return хэш-код
     */
    @Override
    public int hashCode() {
        return name.hashCode() + description.hashCode();
    }

    /**
     * Возвращает строковое представление команды.
     *
     * @return строка с именем и описанием команды
     */
    @Override
    public String toString() {
        return "Command{" +
                "name='" + name + '\'' +
                ", description='" + description + '\'' +
                '}';
    }
}