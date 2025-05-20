package common.serverUtils;

import common.HumanBeing;

import java.io.Serializable;
import java.net.InetSocketAddress;
import java.util.Arrays;

public class Request implements Serializable {
    private String clientRequest;
    private String dataRequest;
    private HumanBeing humanBeing;
    private InetSocketAddress clientAddress;
    private String login;
    private String password;

    public Request(String clientRequest, String dataRequest) {
        this.clientRequest = clientRequest;
        this.dataRequest = dataRequest;
    }

    public String getDataRequest() {
        return dataRequest;
    }

    public Request(String clientRequest, HumanBeing humanBeing) {
        this.clientRequest = clientRequest;
        this.humanBeing = humanBeing;
    }

    public Request(String clientRequest, HumanBeing humanBeing, String login, String password) {
        this.humanBeing = humanBeing;
        this.clientRequest = clientRequest;
        this.login = login;
        this.password = password;
    }

    public String getClientRequest() {
        return clientRequest;
    }

    public HumanBeing getHumanBeing() {
        return humanBeing;
    }

    public void setClientRequest(String clientRequest) {
        this.clientRequest = clientRequest;
    }

    public void setHumanBeing(HumanBeing humanBeing) {
        this.humanBeing = humanBeing;
    }

    public InetSocketAddress getClientAddress() {
        return clientAddress;
    }

    public void setClientAddress(InetSocketAddress clientAddress) {
        this.clientAddress = clientAddress;
    }

    public String getLogin() {
        return login;
    }

    public String getPassword() {
        return password;
    }

    public void setLogin(String login) {
        this.login = login;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    @Override
    public String toString() {
        return "Request{" +
                "clientRequest=" + clientRequest +
                ", dataRequest=" + dataRequest +
                ", clientAddress=" + clientAddress +
                '}';
    }
}
