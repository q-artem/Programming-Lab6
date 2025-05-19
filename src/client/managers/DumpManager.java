package client.managers;

import client.utility.console.Console;
import common.Car;
import common.Coordinates;
import common.HumanBeing;
import common.WeaponType;
import org.dom4j.Document;
import org.dom4j.DocumentException;
import org.dom4j.DocumentHelper;
import org.dom4j.Element;
import org.dom4j.io.OutputFormat;
import org.dom4j.io.SAXReader;
import org.dom4j.io.XMLWriter;

import java.io.*;
import java.time.LocalDate;
import java.util.Map;
import java.util.Objects;
import java.util.Scanner;
import java.util.TreeMap;

/**
 * Класс-менеджер для чтения и записи коллекции {@link HumanBeing} в XML-файл.
 * Позволяет сериализовать коллекцию в XML и десериализовать её обратно, используя Dom4j.
 */
public class DumpManager {
    private final String fileName;
    private final Console console;

    /**
     * Конструктор менеджера дампа.
     *
     * @param fileName имя файла для сохранения и загрузки коллекции
     * @param console  консоль для вывода сообщений об ошибках и информации
     */
    public DumpManager(String fileName, Console console) {
        this.fileName = fileName;
        this.console = console;
    }

    /**
     * Записывает коллекцию {@link HumanBeing} в XML-файл.
     * Формирует XML-документ, сериализует все элементы коллекции и сохраняет в файл.
     * В случае ошибки выводит сообщение в консоль.
     *
     * @param collection коллекция для сохранения
     */
    public void writeCollection(TreeMap<Integer, HumanBeing> collection) {
        try {
            Document document = DocumentHelper.createDocument();
            Element rootElement = document.addElement("humanBeings");

            for (Map.Entry<Integer, HumanBeing> entry : collection.entrySet()) {
                HumanBeing humanBeing = entry.getValue();
                Element humanElement = rootElement.addElement("humanBeing").addAttribute("id", String.valueOf(entry.getKey()));

                humanElement.addElement("name").setText(humanBeing.getName());

                Element coordinates = humanElement.addElement("coordinates");
                coordinates.addElement("x").setText(String.valueOf(humanBeing.getCoordinates().getX()));
                coordinates.addElement("y").setText(humanBeing.getCoordinates().getY() != null ? String.valueOf(humanBeing.getCoordinates().getY()) : "");

                humanElement.addElement("creationDate").setText(humanBeing.getCreationDate().toString());
                humanElement.addElement("realHero").setText(humanBeing.getRealHero() != null ? String.valueOf(humanBeing.getRealHero()) : "");
                humanElement.addElement("hasToothpick").setText(humanBeing.getHasToothpick() != null ? String.valueOf(humanBeing.getHasToothpick()) : "");
                humanElement.addElement("impactSpeed").setText(String.valueOf(humanBeing.getImpactSpeed()));
                humanElement.addElement("soundtrackName").setText(String.valueOf(humanBeing.getSoundtrackName()));
                humanElement.addElement("minutesOfWaiting").setText(humanBeing.getMinutesOfWaiting() != null ? String.valueOf(humanBeing.getMinutesOfWaiting()) : "");
                humanElement.addElement("weaponType").setText(String.valueOf(humanBeing.getWeaponType()));

                Element carElement = humanElement.addElement("car");
                carElement.addElement("name").setText(humanBeing.getCar() != null ? String.valueOf(humanBeing.getCar()) : "");
            }

            OutputFormat format = OutputFormat.createPrettyPrint();
            format.setEncoding("UTF-8");

            try (BufferedWriter writer = new BufferedWriter(new FileWriter(fileName))) {
                XMLWriter xmlWriter = new XMLWriter(writer, format);
                xmlWriter.write(document);
            }
        } catch (IOException e) {
            console.printError("Ошибка записи в файл: " + e.getMessage());
        }
    }

    /**
     * Загружает коллекцию {@link HumanBeing} из XML-файла.
     * Очищает переданную коллекцию, парсит XML и добавляет элементы в коллекцию.
     * В случае ошибок парсинга или чтения файла выводит сообщения в консоль.
     *
     * @param collection коллекция для загрузки данных
     */
    public void readCollection(TreeMap<Integer, HumanBeing> collection) {
        collection.clear();

        try (Scanner fileScanner = new Scanner(new File(fileName))) {
            StringBuilder xmlContent = new StringBuilder();
            while (fileScanner.hasNextLine()) {
                xmlContent.append(fileScanner.nextLine());
            }

            if (xmlContent.isEmpty()) {
                console.printError("Файл пуст!");
                return;
            }

            SAXReader reader = new SAXReader();
            Document document = reader.read(new StringReader(xmlContent.toString()));
            Element root = document.getRootElement();

            for (Element humanElement : root.elements("humanBeing")) {
                try {
                    int id = Integer.parseInt(humanElement.attributeValue("id"));

                    String name = humanElement.elementText("name");
                    LocalDate creationDate = LocalDate.parse(humanElement.elementText("creationDate"));

                    Element coordElement = humanElement.element("coordinates");
                    long x = Long.parseLong(coordElement.elementText("x"));
                    Float y = !Objects.equals(coordElement.elementText("y"), "") ? Float.parseFloat(coordElement.elementText("y")) : null;
                    Coordinates coordinates = new Coordinates.Builder().x(x).y(y).build();

                    Boolean realHero = !Objects.equals(humanElement.elementText("realHero"), "") ? Boolean.parseBoolean(humanElement.elementText("realHero")) : null;
                    Boolean hasToothpick = !Objects.equals(humanElement.elementText("hasToothpick"), "") ? Boolean.parseBoolean(humanElement.elementText("hasToothpick")) : null;
                    float impactSpeed = Float.parseFloat(humanElement.elementText("impactSpeed"));
                    String soundtrackName = humanElement.elementText("soundtrackName");
                    Double minutesOfWaiting = !Objects.equals(humanElement.elementText("minutesOfWaiting"), "") ? Double.parseDouble(humanElement.elementText("minutesOfWaiting")) : null;
                    WeaponType weaponType = WeaponType.valueOf(humanElement.elementText("weaponType"));

                    Element carElement = humanElement.element("car");
                    String nameCar = carElement.elementText("name");
                    Car car = new Car.Builder().name(nameCar).build();

                    HumanBeing human = new HumanBeing.Builder(id, creationDate).name(name)
                            .coordinates(coordinates)
                            .realHero(realHero)
                            .hasToothpick(hasToothpick)
                            .impactSpeed(impactSpeed)
                            .soundtrackName(soundtrackName)
                            .minutesOfWaiting(minutesOfWaiting)
                            .weaponType(weaponType)
                            .car(car)
                            .build();

                    collection.put(id, human);
                } catch (Exception e) {
                    console.printError("Ошибка парсинга элемента humanBeing: " + e.getMessage());
                }
            }

            console.println("Коллекция успешно загружена!");
        } catch (FileNotFoundException e) {
            console.printError("Файл не найден: " + e.getMessage());
        } catch (DocumentException e) {
            console.printError("Ошибка парсинга: " + e.getMessage());
        } catch (Exception e) {
            console.printError("Непредвиденная ошибка: " + e.getMessage());
        }
    }
}
