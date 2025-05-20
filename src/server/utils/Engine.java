package server.utils;

import client.managers.CollectionManager;
import client.managers.CommandManager;
import common.serverUtils.Request;
import server.managers.DumpManager;
import client.utility.Runner;
import client.utility.console.Console;
import client.utility.console.StandartConsole;
import common.serverUtils.Response;
import server.server.Server;
import sun.misc.Signal;
import sun.misc.SignalHandler;

import java.io.IOException;
import java.net.InetSocketAddress;
import java.nio.channels.SelectionKey;
import java.nio.channels.Selector;
import java.util.Iterator;
import java.util.NoSuchElementException;
import java.util.Scanner;
import java.util.Set;
import java.util.logging.Level;
import java.util.logging.Logger;

public class Engine {
    private static final Logger logger = ServerLogger.getInstance();
    private boolean flag = true;
    private CollectionManager collectionManager;
    private DumpManager dumpManager;
    private CommandManager commandManager;
    private Scanner scanner = new Scanner(System.in);
    private Server server;

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

        var dumpManager = new DumpManager(args[0], console);
        var collectionManager = new CollectionManager(dumpManager);
        if (!collectionManager.loadCollection()) {
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

//        Signal.handle(new Signal("INT"), new SignalHandler() {
//            @Override
//            public void handle(Signal signal) {
//                logger.log(Level.SEVERE, "Введен SIGINT. Завершение работы");
//                Response response = commandManager.setUserRequest(new Request("exit".split(" ")));
//            }
//        });

        try {
            while (this.flag) {
                String consoleRequest = scanner.nextLine().trim();
                logger.log(Level.INFO, "Получен ввод из консоли : " + consoleRequest);
                if (consoleRequest.equals("exit") || consoleRequest.equals("save")) {
                    Response response = this.commandManager.setUserRequest(new Request(consoleRequest.split(" ")));
                    System.out.println(response.getMessage());
                }
            }
        } catch (NoSuchElementException e) {
            logger.log(Level.SEVERE, "Перекрыт поток консольного ввода. Завершение работы");
            Response response = this.commandManager.setUserRequest(new Request("exit".split(" ")));
        }
        thread.stop();
        System.exit(0);

        new Runner(console, commandManager).interactiveMode();
    }


    private Thread serverThread() {
        Runnable r = () -> {
            Selector selector = null;
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
            Request localRequest = request;
            InetSocketAddress clientAddress = localRequest.getClientAddress();
            String command = localRequest.getClientRequest();
            if (command.equals("save_dump")) {
                // Сохраняем коллекцию, присланную клиентом
                dumpManager.writeCollection(localRequest.getDataRequest());
                Response threadResponse = new Response("Коллекция успешно сохранена на сервере.");
                threadResponse.setClientAddress(clientAddress);
                this.server.sendResponse(threadResponse);
            } else if (command.equals("get_dump")) {
                // Загружаем коллекцию с сервера и отправляем клиенту
                String xmlData = dumpManager.getXmlDump(); // Реализуйте этот метод для получения XML-дампа
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

    /**
     * Возвращает {@link CollectionManager}, назначенный объекту
     *
     * @return {@link Engine#collectionManager}
     */
    public CollectionManager getCollectionManager() {
        return collectionManager;
    }

    /**
     * Возвращает {@link CommandManager}, назначенный объекту
     *
     * @return {@link Engine#commandManager}
     */
    public CommandManager getCommandManager() {
        return commandManager;
    }

    public DumpManager getDumpManager() {
        return dumpManager;
    }

}
