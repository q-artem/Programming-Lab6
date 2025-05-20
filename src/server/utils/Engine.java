package server.utils;

import common.serverUtils.Request;
import server.managers.DumpManager;
import common.utility.Console;
import common.utility.StandartConsole;
import common.serverUtils.Response;
import server.server.Server;

import java.io.IOException;
import java.net.InetSocketAddress;
import java.nio.channels.SelectionKey;
import java.nio.channels.Selector;
import java.util.Iterator;
import java.util.NoSuchElementException;
import java.util.Set;
import java.util.logging.Level;
import java.util.logging.Logger;

public class Engine {
    private static final Logger logger = ServerLogger.getInstance();
    private boolean flag = true;
    private final DumpManager dumpManager;
    private Server server;

    public Engine(String[] args) {
        dumpManager = new DumpManager(args[0]);
    }


    public void finishProgramm() {
        this.server.getResponseCashedPoll().shutdown();
        this.server.getReadCashedPoll().shutdown();
        logger.log(Level.INFO, "Завершение цикла жизни сервера");
        this.flag = false;
    }

    public void run(String[] args) {
        Console console = new StandartConsole();

        if (args.length == 0) {
            console.println("Введите имя загружаемого файла как аргумент командной строки");
            System.exit(1);
        }

        this.server = new Server(1448);
        try {
            server.startServer();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        Thread thread = serverThread();
        thread.start();

        try {
            while (this.flag) {
                String consoleRequest = console.readln().trim();
                logger.log(Level.INFO, "Получен ввод из консоли : " + consoleRequest);
                if (consoleRequest.equals("exit")) {
                    this.finishProgramm();
                }
            }
        } catch (NoSuchElementException e) {
            logger.log(Level.SEVERE, "Перекрыт поток консольного ввода. Завершение работы");
            this.finishProgramm();
        }
        thread.stop();
        System.exit(0);
    }


    private Thread serverThread() {
        Runnable r = () -> {
            Selector selector;
            try {
                selector = Selector.open();
                this.server.getChannel().register(selector, SelectionKey.OP_READ);
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
            while (this.flag) {
                try {
                    selector.select();
                    Set<SelectionKey> selectedKeys = selector.selectedKeys();
                    Iterator<SelectionKey> keyIterator = selectedKeys.iterator();
                    while (keyIterator.hasNext()) {
                        SelectionKey key = keyIterator.next();
                        keyIterator.remove();
                        if (key.isReadable()) {
                            this.server.getReadCashedPoll().submit(
                                    () -> {
                                        Request request = server.receiveRequest();
                                        if (request != null) {
                                            logger.log(Level.INFO, "Поступил запрос : " + request.getClientRequest());
                                            processRequest(request);
                                        } else {
                                            this.server.sendResponse(new Response("Ошибка : послан поврежденный запрос", request.getClientAddress()));
                                        }
                                    }
                            );
                        }
                    }
                } catch (Exception e) {
                    logger.log(Level.SEVERE, "Критическая ошибка " + e.getMessage() + e);
                }
            }
        };
        Thread thread = new Thread(r);
        thread.setName("Поток");
        return thread;
    }

    public void processRequest(Request request) {
        Runnable requestTask = () -> {
            InetSocketAddress clientAddress = request.getClientAddress();
            String command = request.getClientRequest();
            if (command.equals("save_dump")) {
                // Сохраняем коллекцию, присланную клиентом
                dumpManager.writeCollection(request.getDataRequest());
                Response threadResponse = new Response("Коллекция успешно сохранена на сервере.");
                threadResponse.setClientAddress(clientAddress);
                this.server.sendResponse(threadResponse);
            } else if (command.equals("get_dump")) {
                // Загружаем коллекцию с сервера и отправляем клиенту
                String xmlData = dumpManager.readCollection(); // Реализуйте этот метод для получения XML-дампа
                Response threadResponse = new Response(xmlData);
                threadResponse.setClientAddress(clientAddress);
                this.server.sendResponse(threadResponse);
            }
//            else {
//                Response threadResponse = commandManager.setUserRequest(localRequest);
//                threadResponse.setClientAddress(clientAddress);
//                this.server.sendResponse(threadResponse);
//            }
        };
        var requestThread = new Thread(requestTask);
        requestThread.start();
        logger.log(Level.INFO, "Запущен поток " + requestThread.getName() + ". С id = " + requestThread.getId());
    }
}
