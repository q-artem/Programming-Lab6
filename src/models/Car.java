package models;

import utility.Validatable;

import java.util.Objects;

/**
 * Класс, представляющий автомобиль.
 * Реализует интерфейс Validatable для проверки полей.
 */
public class Car implements Validatable {
    private String name; //Поле не может быть null

    /**
     * Получает имя автомобиля.
     *
     * @return имя автомобиля
     */
    public String getName() {
        return name;
    }

    /**
     * Проверяет, что имя не равно null и не пустое.
     *
     * @param name имя для проверки
     * @return true если имя валидно, false в противном случае
     */
    public static boolean validateName(String name) {
        return name != null && !name.isEmpty();
    }

    /**
     * Проверяет валидность имени этого автомобиля.
     *
     * @return true если имя валидно, false в противном случае
     */
    public boolean validate() {
        return validateName(name);
    }

    /**
     * Возвращает строковое представление автомобиля (его имя).
     *
     * @return имя автомобиля или "null", если имя равно null
     */
    @Override
    public String toString() {
        return name == null ? "null" : name;
    }

    /**
     * Сравнивает этот автомобиль с другим объектом на равенство.
     *
     * @param o объект для сравнения
     * @return true если объекты равны, false в противном случае
     */
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Car car = (Car) o;
        return Objects.equals(name, car.name);
    }

    /**
     * Возвращает хэш-код для этого автомобиля.
     *
     * @return значение хэш-кода
     */
    @Override
    public int hashCode() {
        return Objects.hash(name);
    }

    /**
     * Билдер для создания экземпляров класса Car.
     */
    public static class Builder {
        private String name;

        /**
         * Устанавливает имя для создаваемого автомобиля.
         *
         * @param name имя для установки
         * @return этот экземпляр строителя
         */
        public Builder name(String name) {
            this.name = name;
            return this;
        }

        /**
         * Создает новый экземпляр Car с установленным именем.
         *
         * @return новый экземпляр Car
         */
        public Car build() {
            Car car = new Car();
            car.name = this.name;
            return car;
        }
    }
}