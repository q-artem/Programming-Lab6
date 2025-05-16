package common;

/**
 * Перечисление типов оружия, доступных для HumanBeing.
 */
public enum WeaponType {
    /**
     * Молот
     */
    HAMMER,
    /**
     * Топор
     */
    AXE,
    /**
     * Нож
     */
    KNIFE;

    /**
     * Возвращает строку со списком всех типов оружия, разделенных запятыми.
     *
     * @return строка с перечислением типов оружия
     */
    public static String names() {
        StringBuilder nameList = new StringBuilder();
        for (var weaponType : values()) {
            nameList.append(weaponType.name()).append(", ");
        }
        return nameList.substring(0, nameList.length() - 2);
    }
}