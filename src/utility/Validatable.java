package utility;

/**
 * Интерфейс для объектов, которые можно проверить на корректность.
 */
public interface Validatable {
    /**
     * Проверить корректность объекта.
     *
     * @return true, если объект корректен, иначе false
     */
    boolean validate();
}