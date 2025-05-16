package common;

import java.util.TreeMap;

/**
 * Генератор уникальных идентификаторов для объектов HumanBeing.
 * Обеспечивает уникальность и последовательность ID.
 */
public class IdGenerator {
    private static Integer humanBeingCounter = 0;

    /**
     * Устанавливает текущее значение счетчика идентификаторов.
     *
     * @param idCounter новое значение счетчика
     */
    private static void setHumanBeingCounter(Integer idCounter) {
        humanBeingCounter = idCounter;
    }

    /**
     * Восстанавливает счетчик идентификаторов на основе существующей коллекции.
     * Устанавливает счетчик равным максимальному ID в коллекции.
     *
     * @param humanBeingTreeMap коллекция существующих объектов HumanBeing
     */
    public static void restoreHumanBeingCounter(TreeMap<Integer, HumanBeing> humanBeingTreeMap) {
        if (humanBeingTreeMap.isEmpty()) {
            setHumanBeingCounter(0);
            return;
        }

        // TreeMap сортирован => .lastKey() - максимальный ID
        Integer lastKey = humanBeingTreeMap.lastKey();
        if (lastKey > humanBeingCounter) {
            setHumanBeingCounter(lastKey);
        }
    }

    /**
     * Генерирует и возвращает новый уникальный идентификатор для HumanBeing.
     *
     * @return новый уникальный идентификатор
     */
    public static int assignHumanBeingId() {
        humanBeingCounter++;
        return humanBeingCounter;
    }
}