package common.serverUtils;

import java.io.Serializable;
import java.net.InetSocketAddress;

public class Response implements Serializable {
    private String message;
    private InetSocketAddress clientAddress;

    public Response(String message) {
        this.message = message;
    }

    public Response(String message, InetSocketAddress clientAddress) {
        this.message = message;
        this.clientAddress = clientAddress;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public void setClientAddress(InetSocketAddress clientAddress) {
        this.clientAddress = clientAddress;
    }

    public InetSocketAddress getClientAddress() {
        return clientAddress;
    }
}
