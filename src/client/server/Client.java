package client.server;


import common.serverUtils.Request;
import common.serverUtils.Response;

import java.io.IOException;
import java.net.InetSocketAddress;
import java.nio.ByteBuffer;
import java.nio.channels.DatagramChannel;


import static common.serverUtils.Serializer.deserialize;
import static common.serverUtils.Serializer.serialize;

public class Client {
    private static final int TIME_OUT = 3000;
    private static final int BUFFER_SIZE = 8096;
    private static final int SERVER_PORT = 1448;
    private DatagramChannel channel;

    public Client() throws IOException {
        channel = DatagramChannel.open();
        channel.configureBlocking(false);
    }

    public void sendRequest(Request request) throws IOException {
        if (request == null){
            return;
        }
        ByteBuffer byteBuffer = ByteBuffer.allocate(BUFFER_SIZE);
        InetSocketAddress inetSocketAddress = new InetSocketAddress("localhost", SERVER_PORT);
        byteBuffer.clear();
        byteBuffer = ByteBuffer.wrap(serialize(request));
        channel.send(byteBuffer, inetSocketAddress);
        byteBuffer.clear();
    }

    public Response recieveRequest() throws IOException, ClassNotFoundException {
        ByteBuffer byteBuffer = ByteBuffer.allocate(BUFFER_SIZE);
        long startTime = System.currentTimeMillis();
        Response response = null;
        while (System.currentTimeMillis() - startTime < TIME_OUT) {
            InetSocketAddress inetSocketAddress = (InetSocketAddress) channel.receive(byteBuffer);
            if (inetSocketAddress == null){
                continue;
            }
            Object object = deserialize(byteBuffer.array());
            if (object instanceof Response) {
                response = (Response) object;
            }
            byteBuffer.clear();
            return response;
        }
        return new Response("Ошибка : ответа от сервера не последовало");
    }
}