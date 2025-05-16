package utility;

/**
 * Абстрактный базовый класс для элементов коллекции.
 * Реализует интерфейсы {@link Comparable} и {@link Validatable}.
 * Все элементы должны иметь уникальный идентификатор.
 */
public abstract class Element implements Comparable<Element>, Validatable {
    /**
     * Получить идентификатор элемента.
     *
     * @return уникальный идентификатор элемента
     */
    abstract public Integer getId();
}
