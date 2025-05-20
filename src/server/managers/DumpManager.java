package server.managers;

import common.Car;
import common.Coordinates;
import common.HumanBeing;
import common.WeaponType;
import org.dom4j.DocumentException;
import org.dom4j.io.SAXReader;
import client.utility.console.Console;

import java.io.*;
import java.time.LocalDate;
import java.util.*;

import org.dom4j.Document;
import org.dom4j.DocumentHelper;
import org.dom4j.Element;
import org.dom4j.io.OutputFormat;
import org.dom4j.io.XMLWriter;

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
     * Сохраняет коллекцию {@link HumanBeing} в XML-файл, принимая XML-дамп в виде строки.
     * Получает XML-дамп коллекции в виде строки, парсит его и сохраняет в файл.
     * В случае ошибки выводит сообщение в консоль.
     *
     * @param xmlData XML-дамп коллекции для сохранения
     */
    public void writeCollection(String xmlData) {
        try {
            org.dom4j.io.SAXReader reader = new org.dom4j.io.SAXReader();
            org.dom4j.Document document = reader.read(new java.io.StringReader(xmlData));
            org.dom4j.io.OutputFormat format = org.dom4j.io.OutputFormat.createPrettyPrint();
            format.setEncoding("UTF-8");
            try (BufferedWriter writer = new BufferedWriter(new FileWriter(fileName))) {
                org.dom4j.io.XMLWriter xmlWriter = new org.dom4j.io.XMLWriter(writer, format);
                xmlWriter.write(document);
            }
        } catch (Exception e) {
            console.printError("Ошибка при сохранении коллекции: " + e.getMessage());
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

    /**
     * Возвращает XML-дамп коллекции, считанной из файла (или текущей коллекции).
     * Используется для передачи коллекции клиенту по сети.
     * @return XML-дамп коллекции или пустую строку в случае ошибки
     */
    public String getXmlDump() {
        try (BufferedReader reader = new BufferedReader(new FileReader(fileName))) {
            StringBuilder xmlContent = new StringBuilder();
            String line;
            while ((line = reader.readLine()) != null) {
                xmlContent.append(line).append("\n");
            }
            return xmlContent.toString();
        } catch (Exception e) {
            console.printError("Ошибка при чтении XML-дампа: " + e.getMessage());
            return "";
        }
    }
}
