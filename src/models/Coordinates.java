package models;

import utility.Validatable;

import java.util.Objects;

/**
 * Класс, представляющий координаты в двумерном пространстве.
 * Реализует интерфейс Validatable для проверки полей.
 * Координата x должна быть больше -167, координата y не может быть null.
 */
public class Coordinates implements Validatable {
    private long x; //Значение поля должно быть больше -167, Поле не может быть null
    private Float y;

    /**
     * Получает значение координаты X.
     *
     * @return значение координаты X
     */
    public long getX() {
        return x;
    }

    /**
     * Получает значение координаты Y.
     *
     * @return значение координаты Y (может быть null)
     */
    public Float getY() {
        return y;
    }

    /**
     * Проверяет валидность координаты X.
     *
     * @param x значение для проверки
     * @return true если значение больше -167, false в противном случае
     */
    public static boolean validateX(Long x) {
        return x != null && x > -167;
    }

    /**
     * Проверяет валидность координат.
     *
     * @return true если координаты валидны, false в противном случае
     */
    public boolean validate() {
        return validateX(x);
    }

    /**
     * Возвращает строковое представление координат.
     *
     * @return строка в формате (X; Y)
     */
    @Override
    public String toString() {
        return "(" + x + "; " + y + ")";
    }

    /**
     * Сравнивает этот объект координат с другим объектом.
     *
     * @param obj объект для сравнения
     * @return true если объекты равны, false в противном случае
     */
    @Override
    public boolean equals(Object obj) {
        if (this == obj) return true;
        if (obj == null || getClass() != obj.getClass()) return false;
        Coordinates that = (Coordinates) obj;
        return Objects.equals(x, that.x) && Objects.equals(y, that.y);
    }

    /**
     * Возвращает хэш-код для этих координат.
     *
     * @return значение хэш-кода
     */
    @Override
    public int hashCode() {
        return Objects.hash(x, y);
    }

    /**
     * Билдер для создания экземпляров класса Coordinates.
     */
    public static class Builder {
        private long x;
        private Float y;

        /**
         * Устанавливает значение координаты X.
         *
         * @param x значение координаты X
         * @return этот экземпляр билдера
         */
        public Builder x(long x) {
            this.x = x;
            return this;
        }

        /**
         * Устанавливает значение координаты Y.
         *
         * @param y значение координаты Y
         * @return этот экземпляр билдера
         */
        public Builder y(Float y) {
            this.y = y;
            return this;
        }

        /**
         * Создает новый экземпляр Coordinates с установленными значениями.
         *
         * @return новый экземпляр Coordinates
         */
        public Coordinates build() {
            Coordinates coordinates = new Coordinates();
            coordinates.x = this.x;
            coordinates.y = this.y;
            return coordinates;
        }
    }
}
