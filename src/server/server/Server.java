package server.server;

import server.utils.ServerLogger;

import java.io.IOException;
import java.net.BindException;
import java.net.InetSocketAddress;
import java.nio.ByteBuffer;
import java.nio.channels.DatagramChannel;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;
import java.util.logging.Level;
import java.util.logging.Logger;

import static common.serverUtils.Serializer.deserialize;
import static common.serverUtils.Serializer.serialize;
import common.serverUtils.Request;
import common.serverUtils.Response;


public class Server {
    private final Logger logger = ServerLogger.getInstance();
    private final int port;
    private final int bufferSize = 2048;
    private DatagramChannel channel;
    private final ExecutorService responseCashedPoll = Executors.newCachedThreadPool();
    private final ExecutorService readCashedPoll = Executors.newCachedThreadPool();
    private final Lock serverLock = new ReentrantLock();

    public Server(int port) {
        this.port = port;
    }

    public void startServer() throws IOException {
        channel = DatagramChannel.open();
        channel.configureBlocking(false);
        try {
            channel.bind(new InetSocketAddress(this.port));
        } catch (BindException e) {
            logger.log(Level.SEVERE, "Порт занят");
            System.exit(1);
        }
        logger.log(Level.INFO, "Открыт порт " + this.port);
    }

    public Request receiveRequest() {
        try {
            ByteBuffer byteBuffer = ByteBuffer.allocate(bufferSize);
            byteBuffer.clear();

            serverLock.lock();
            InetSocketAddress inetSocketAddress;
            try {
                inetSocketAddress = (InetSocketAddress) channel.receive(byteBuffer);
            } finally {
                serverLock.unlock();
            }

            byteBuffer.flip();
            Object object = deserialize(byteBuffer.array());
            Request request;
            if (object instanceof Request) {
                request = (Request) object;
            } else {
                return new Request("", "");
            }
            byteBuffer.clear();
            request.setClientAddress(inetSocketAddress);
            logger.log(Level.INFO, "Получен запрос" + inetSocketAddress.toString());
            return request;
        } catch (IOException | ClassNotFoundException e) {
            return null;
        }
    }

    public void sendResponse(Response response) {
        if (response == null) {
            return;
        }
        responseCashedPoll.submit(() -> {
                    try {
                        ByteBuffer byteBuffer = ByteBuffer.allocate(bufferSize);
                        byteBuffer.clear();
                        byteBuffer = ByteBuffer.wrap(serialize(response));
                        channel.send(byteBuffer, response.getClientAddress());
                        byteBuffer.clear();
                        logger.log(Level.INFO, "Отправлен ответ на "+response.getClientAddress().toString());
                    } catch (IOException e) {
                        logger.log(Level.SEVERE, "IO ошибка во время ответа по адресу "+response.getClientAddress().toString());
                    }
                }
        );
    }

    public DatagramChannel getChannel() {
        return this.channel;
    }

    public ExecutorService getResponseCashedPoll() {
        return this.responseCashedPoll;
    }

    public ExecutorService getReadCashedPoll() {
        return this.readCashedPoll;
    }
}