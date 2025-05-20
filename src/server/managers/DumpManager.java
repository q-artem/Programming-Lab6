package server.managers;

import common.HumanBeing;

import java.io.*;
import java.util.logging.Level;
import java.util.logging.Logger;

import server.utils.ServerLogger;

/**
 * Класс-менеджер для чтения и записи коллекции {@link HumanBeing} в XML-файл.
 * Позволяет сериализовать коллекцию в XML и десериализовать её обратно, используя Dom4j.
 */
public class DumpManager {
    private final String fileName;
    private static final Logger logger = ServerLogger.getInstance();

    /**
     * Конструктор менеджера дампа.
     *
     * @param fileName имя файла для сохранения и загрузки коллекции
     */
    public DumpManager(String fileName) {
        this.fileName = fileName;
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
            logger.log(Level.WARNING, "Ошибка при сохранении коллекции: " + e.getMessage());
        }
    }

    /**
     * Возвращает XML-дамп коллекции, считанной из файла (или текущей коллекции).
     * Используется для передачи коллекции клиенту по сети.
     *
     * @return XML-дамп коллекции или пустую строку в случае ошибки
     */
    public String readCollection() {
        try (BufferedReader reader = new BufferedReader(new FileReader(fileName))) {
            StringBuilder xmlContent = new StringBuilder();
            String line;
            while ((line = reader.readLine()) != null) {
                xmlContent.append(line).append("\n");
            }
            return xmlContent.toString();
        } catch (Exception e) {
            logger.log(Level.WARNING, "Ошибка при чтении XML-дампа: " + e.getMessage());
            return "";
        }
    }
}
