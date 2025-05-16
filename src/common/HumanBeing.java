package common;

import common.utility.Element;
import common.utility.Validatable;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.Objects;

// Тип данных полей не соответствует комментариям. Тип данных был изменён.

/**
 * Класс, представляющий человека с различными характеристиками.
 * Наследует Element и реализует Validatable для проверки полей.
 * Поля автоматически генерируются: id и creationDate.
 * Обязательные поля: name, coordinates, soundtrackName, weaponType.
 */
public class HumanBeing extends Element implements Validatable {
    private final Integer id; //Поле не может быть null, Значение поля должно быть больше 0, Значение этого поля должно быть уникальным, Значение этого поля должно генерироваться автоматически
    private String name; //Поле не может быть null, Строка не может быть пустой
    private Coordinates coordinates; //Поле не может быть null
    private final LocalDate creationDate; //Поле не может быть null, Значение этого поля должно генерироваться автоматически
    private Boolean realHero;
    private Boolean hasToothpick;
    private float impactSpeed; //Поле не может быть null
    private String soundtrackName; //Поле не может быть null
    private Double minutesOfWaiting; // ДОЛЖНО быть не отрицательным
    private WeaponType weaponType; //Поле не может быть null
    private Car car; //Поле МОЖЕТ быть null

    /**
     * Конструктор по умолчанию. Автоматически генерирует id и дату создания.
     */
    public HumanBeing() {
        this.id = IdGenerator.assignHumanBeingId();
        this.creationDate = LocalDate.now();
    }

    /**
     * Конструктор для загрузки из файла с указанными id и датой.
     *
     * @param id   идентификатор
     * @param date дата создания
     */
    public HumanBeing(Integer id, LocalDate date) {
        this.id = id;
        this.creationDate = date;
    }

    /**
     * Возвращает уникальный идентификатор человека.
     *
     * @return идентификатор
     */
    @Override
    public Integer getId() {
        return id;
    }

    /**
     * Возвращает имя человека.
     *
     * @return имя (не может быть null или пустым)
     */
    public String getName() {
        return name;
    }

    /**
     * Возвращает координаты человека.
     *
     * @return объект Coordinates (не может быть null)
     */
    public Coordinates getCoordinates() {
        return coordinates;
    }

    /**
     * Возвращает дату создания записи.
     *
     * @return дата создания
     */
    public LocalDate getCreationDate() {
        return creationDate;
    }

    /**
     * Возвращает, является ли человек настоящим героем.
     *
     * @return true если герой, иначе false
     */
    public Boolean getRealHero() {
        return realHero;
    }

    /**
     * Возвращает, есть ли у человека зубочистка.
     *
     * @return true если есть, иначе false
     */
    public Boolean getHasToothpick() {
        return hasToothpick;
    }

    /**
     * Возвращает скорость удара человека.
     *
     * @return скорость удара
     */
    public float getImpactSpeed() {
        return impactSpeed;
    }

    /**
     * Возвращает название саундтрека человека.
     *
     * @return название саундтрека (не может быть null или пустым)
     */
    public String getSoundtrackName() {
        return soundtrackName;
    }

    /**
     * Возвращает время ожидания человека.
     *
     * @return время ожидания (может быть null, но не отрицательным)
     */
    public Double getMinutesOfWaiting() {
        return minutesOfWaiting;
    }

    /**
     * Возвращает тип оружия человека.
     *
     * @return тип оружия (не может быть null)
     */
    public WeaponType getWeaponType() {
        return weaponType;
    }

    /**
     * Возвращает автомобиль человека.
     *
     * @return автомобиль (может быть null)
     */
    public Car getCar() {
        return car;
    }

    /**
     * Проверяет валидность идентификатора.
     *
     * @param id идентификатор для проверки
     * @return true если id > 0, иначе false
     */
    public static boolean validateId(Integer id) {
        return id > 0;  // id always not null
    }

    /**
     * Проверяет валидность имени.
     *
     * @param name имя для проверки
     * @return true если имя не null и не пустое, иначе false
     */
    public static boolean validateName(String name) {
        return name != null && !name.isEmpty();
    }

    /**
     * Проверяет валидность координат.
     *
     * @param coordinates координаты для проверки
     * @return true если координаты не null и валидны, иначе false
     */
    public static boolean validateCoordinates(Coordinates coordinates) {
        return coordinates != null && coordinates.validate();
    }

    /**
     * Проверяет валидность даты создания.
     *
     * @param creationDate дата для проверки
     * @return true если дата не null, иначе false
     */
    public static boolean validateCreationDate(java.time.LocalDate creationDate) {
        return creationDate != null;
    }

    /**
     * Проверяет валидность скорости удара.
     *
     * @param impactSpeed скорость для проверки
     * @return true если скорость не null, иначе false
     */
    public static boolean validateImpactSpeed(Float impactSpeed) {
        return impactSpeed != null;
    }

    /**
     * Проверяет валидность названия саундтрека.
     *
     * @param soundtrackName название для проверки
     * @return true если название не null и не пустое, иначе false
     */
    public static boolean validateSoundtrackName(String soundtrackName) {
        return soundtrackName != null && !soundtrackName.isEmpty();
    }

    /**
     * Проверяет валидность времени ожидания.
     *
     * @param minutesOfWaiting время для проверки
     * @return true если время null или >= 0, иначе false
     */
    public static boolean validateMinutesOfWaiting(Double minutesOfWaiting) {
        return minutesOfWaiting == null || minutesOfWaiting >= 0;
    }

    /**
     * Проверяет валидность типа оружия.
     *
     * @param weaponType тип оружия для проверки
     * @return true если тип не null, иначе false
     */
    public static boolean validateWeaponType(WeaponType weaponType) {
        return weaponType != null;
    }

    /**
     * Проверяет валидность автомобиля.
     *
     * @param car автомобиль для проверки
     * @return true если автомобиль null или валиден, иначе false
     */
    public static boolean validateCar(Car car) {
        return car == null || car.validate();
    }

    /**
     * Проверяет валидность всех полей человека.
     *
     * @return true если все поля валидны, иначе false
     */
    public boolean validate() {
        return HumanBeing.validateId(id) &&
                validateName(name) &&
                validateCoordinates(coordinates) &&
                validateCreationDate(creationDate) &&
                // impactSpeed never been null
                validateSoundtrackName(soundtrackName) &&
                validateMinutesOfWaiting(minutesOfWaiting) &&
                validateWeaponType(weaponType) &&
                validateCar(car);
    }

    /**
     * Возвращает строковое представление человека в JSON-подобном формате.
     *
     * @return строковое представление
     */
    @Override
    public String toString() {
        return "HumanBeing{\"id\": " + id + ", " +
                "\"name\": \"" + name + "\", " +
                "\"coordinates\": " + coordinates + ", " +
                "\"creationDate\": \"" + creationDate.format(DateTimeFormatter.ISO_DATE) + "\", " +
                "\"realHero\": " + realHero + ", " +
                "\"hasToothpick\": " + hasToothpick + ", " +
                "\"impactSpeed\": " + impactSpeed + ", " +
                "\"soundtrackName\": \"" + soundtrackName + "\", " +
                "\"minutesOfWaiting\": " + minutesOfWaiting + ", " +
                "\"weaponType\": \"" + weaponType + "\", " +
                "\"car\": " + car +
                "}";
    }

    /**
     * Сравнивает этого человека с другим элементом по id.
     *
     * @param element элемент для сравнения
     * @return разница между id
     */
    @Override
    public int compareTo(Element element) {
        return this.id - element.getId();
    }

    /**
     * Сравнивает этого человека с другим объектом.
     *
     * @param o объект для сравнения
     * @return true если объекты равны по всем полям, иначе false
     */
    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        HumanBeing humanBeing = (HumanBeing) o;
        return Objects.equals(id, humanBeing.id) &&
                Objects.equals(name, humanBeing.name) &&
                Objects.equals(coordinates, humanBeing.coordinates) &&
                Objects.equals(creationDate, humanBeing.creationDate) &&
                Objects.equals(realHero, humanBeing.realHero) &&
                Objects.equals(hasToothpick, humanBeing.hasToothpick) &&
                Objects.equals(impactSpeed, humanBeing.impactSpeed) &&
                Objects.equals(soundtrackName, humanBeing.soundtrackName) &&
                Objects.equals(minutesOfWaiting, humanBeing.minutesOfWaiting) &&
                Objects.equals(weaponType, humanBeing.weaponType) &&
                Objects.equals(car, humanBeing.car);
    }

    /**
     * Возвращает хэш-код для этого человека.
     *
     * @return значение хэш-кода
     */
    @Override
    public int hashCode() {
        return Objects.hash(id, name, coordinates, creationDate, realHero, hasToothpick, impactSpeed, soundtrackName, minutesOfWaiting, weaponType, car);
    }

    /**
     * Билдер для создания экземпляров класса HumanBeing.
     * Поддерживает два режима: создание нового и загрузку из файла.
     */
    public static class Builder {
        private boolean loadedFromFile = false;
        private Integer id = null;
        private LocalDate date = null;

        private String name = null;
        private Coordinates coordinates = new Coordinates();
        private Boolean realHero = null;
        private Boolean hasToothpick = null;
        private float impactSpeed = 1f;
        private String soundtrackName = null;
        private Double minutesOfWaiting = null;
        private WeaponType weaponType = null;
        private Car car = new Car();

        /**
         * Конструктор по умолчанию для создания нового человека.
         */
        public Builder() {

        }

        /**
         * Конструктор для загрузки человека из файла.
         *
         * @param id   идентификатор
         * @param date дата создания
         */
        public Builder(Integer id, LocalDate date) {
            loadedFromFile = true;
            this.id = id;
            this.date = date;
        }

        /**
         * Устанавливает имя человека.
         *
         * @param name имя
         * @return этот билдер
         */
        public Builder name(String name) {
            this.name = name;
            return this;
        }

        /**
         * Устанавливает координаты человека.
         *
         * @param coordinates координаты
         * @return этот билдер
         */
        public Builder coordinates(Coordinates coordinates) {
            this.coordinates = coordinates;
            return this;
        }

        /**
         * Устанавливает, является ли человек настоящим героем.
         *
         * @param realHero true если герой
         * @return этот билдер
         */
        public Builder realHero(Boolean realHero) {
            this.realHero = realHero;
            return this;
        }

        /**
         * Устанавливает, есть ли у человека зубочистка.
         *
         * @param hasToothpick true если есть
         * @return этот билдер
         */
        public Builder hasToothpick(Boolean hasToothpick) {
            this.hasToothpick = hasToothpick;
            return this;
        }

        /**
         * Устанавливает скорость удара человека.
         *
         * @param impactSpeed скорость
         * @return этот билдер
         */
        public Builder impactSpeed(float impactSpeed) {
            this.impactSpeed = impactSpeed;
            return this;
        }

        /**
         * Устанавливает название саундтрека человека.
         *
         * @param soundtrackName название
         * @return этот билдер
         */
        public Builder soundtrackName(String soundtrackName) {
            this.soundtrackName = soundtrackName;
            return this;
        }

        /**
         * Устанавливает время ожидания человека.
         *
         * @param minutesOfWaiting время
         * @return этот билдер
         */
        public Builder minutesOfWaiting(Double minutesOfWaiting) {
            this.minutesOfWaiting = minutesOfWaiting;
            return this;
        }

        /**
         * Устанавливает тип оружия человека.
         *
         * @param weaponType тип оружия
         * @return этот билдер
         */
        public Builder weaponType(WeaponType weaponType) {
            this.weaponType = weaponType;
            return this;
        }

        /**
         * Устанавливает автомобиль человека.
         *
         * @param car автомобиль
         * @return этот билдер
         */
        public Builder car(Car car) {
            this.car = car;
            return this;
        }

        /**
         * Создает новый экземпляр HumanBeing с установленными параметрами.
         *
         * @return новый экземпляр HumanBeing
         */
        public HumanBeing build() {
            HumanBeing humanBeing;
            if (loadedFromFile) {
                humanBeing = new HumanBeing(id, date);
            } else {
                humanBeing = new HumanBeing();
            }
            humanBeing.name = this.name;
            humanBeing.coordinates = this.coordinates;
            humanBeing.realHero = this.realHero;
            humanBeing.hasToothpick = this.hasToothpick;
            humanBeing.impactSpeed = this.impactSpeed;
            humanBeing.soundtrackName = this.soundtrackName;
            humanBeing.minutesOfWaiting = this.minutesOfWaiting;
            humanBeing.weaponType = this.weaponType;
            humanBeing.car = this.car;
            return humanBeing;
        }
    }
}