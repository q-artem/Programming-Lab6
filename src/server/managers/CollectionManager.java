package server.managers;

import common.HumanBeing;
import common.IdGenerator;

import java.time.LocalDateTime;
import java.util.TreeMap;

/**
 * Класс-менеджер для управления коллекцией объектов {@link HumanBeing}.
 * Позволяет добавлять, обновлять, удалять элементы, а также сохранять и загружать коллекцию из файла.
 * Хранит информацию о времени последней инициализации и сохранения коллекции.
 */
public class CollectionManager {
    private int currentId = 1;
    TreeMap<Integer, HumanBeing> collection = new TreeMap<>();
    private LocalDateTime lastInitTime;
    private LocalDateTime lastSaveTime;
    private final DumpManager dumpManager;

    /**
     * Конструктор менеджера коллекции.
     *
     * @param dumpManager менеджер для чтения и записи коллекции в файл
     */
    public CollectionManager(DumpManager dumpManager) {
        this.lastInitTime = null;
        this.lastSaveTime = null;
        this.dumpManager = dumpManager;
    }

    /**
     * Возвращает время последней инициализации коллекции.
     *
     * @return Последнее время инициализации.
     */
    public LocalDateTime getLastInitTime() {
        return lastInitTime;
    }

    /**
     * Возвращает время последнего сохранения коллекции.
     *
     * @return Последнее время сохранения.
     */
    public LocalDateTime getLastSaveTime() {
        return lastSaveTime;
    }

    /**
     * Возвращает коллекцию объектов {@link HumanBeing}.
     *
     * @return коллекция.
     */
    public TreeMap<Integer, HumanBeing> getCollection() {
        return collection;
    }

    /**
     * Получает объект {@link HumanBeing} по его id.
     *
     * @param id идентификатор элемента
     * @return объект HumanBeing или null, если не найден
     */
    public HumanBeing getById(int id) {
        return collection.get(id);
    }

    /**
     * Проверяет, содержится ли объект {@link HumanBeing} в коллекции по его id.
     *
     * @param e объект HumanBeing для проверки
     * @return true, если элемент содержится в коллекции или e == null, иначе false
     */
    public boolean isContain(HumanBeing e) {
        return e == null || getById(e.getId()) != null;
    }

    /**
     * Добавляет объект {@link HumanBeing} в коллекцию.
     *
     * @param a объект для добавления
     * @return true, если объект успешно добавлен, иначе false
     */
    public boolean add(HumanBeing a) {
        if (isContain(a)) return false;
        collection.put(a.getId(), a);
        return true;
    }

    /**
     * Обновляет объект {@link HumanBeing} в коллекции по его id.
     *
     * @param humanBeing объект с обновлёнными данными
     * @return true, если обновление прошло успешно, иначе false
     */
    public boolean update(HumanBeing humanBeing) {
        if (!isContain(humanBeing)) return false;
        collection.remove(humanBeing.getId());
        collection.put(humanBeing.getId(), humanBeing);
        return true;
    }

    /**
     * Удаляет объект {@link HumanBeing} из коллекции по его id.
     *
     * @param id идентификатор элемента для удаления
     * @return true, если удаление прошло успешно, иначе false
     */
    public boolean remove(Integer id) {
        HumanBeing humanBeing = getById(id);
        if (humanBeing == null) return false;
        collection.remove(humanBeing.getId());
        return true;
    }

    /**
     * Загружает коллекцию из файла с помощью {@link DumpManager}.
     * Очищает текущую коллекцию, читает новую и обновляет время инициализации.
     *
     * @return true, если загрузка прошла успешно
     */
    public boolean loadCollection() {
        collection.clear();
        dumpManager.readCollection(collection);
        lastInitTime = LocalDateTime.now();
        for (HumanBeing humanBeing : collection.values())
            if (humanBeing.getId() > currentId) currentId = humanBeing.getId();
        IdGenerator.restoreHumanBeingCounter(collection);
        return true;
    }

    /**
     * Сохраняет коллекцию в файл с помощью {@link DumpManager} и обновляет время сохранения.
     */
    public void saveCollection() {
        dumpManager.writeCollection(collection);
        lastSaveTime = LocalDateTime.now();
    }

    /**
     * Возвращает строковое представление коллекции.
     *
     * @return строка с элементами коллекции или сообщение, если коллекция пуста
     */
    @Override
    public String toString() {
        if (collection.isEmpty()) return "Коллекция пуста!";

        StringBuilder info = new StringBuilder();
        for (HumanBeing humanBeing : collection.values()) {
            info.append(humanBeing).append("\n");
        }
        return info.toString().trim();
    }
}