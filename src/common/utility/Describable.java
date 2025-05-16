package common.utility;

/**
 * Интерфейс для объектов, которые можно назвать и описать.
 * Определяет методы для получения имени и описания объекта.
 */
public interface Describable {
    /**
     * Получить имя объекта.
     *
     * @return имя
     */
    String getName();

    /**
     * Получить описание объекта.
     *
     * @return описание
     */
    String getDescription();
}