package com.client;

import java.io.IOException;
import java.net.Socket;

public class ConnectionService {

    private final int port;
    private final String ip;
    private Socket clientSocket;

    public ConnectionService(int port, String ip) {
        this.port = port;
        this.ip = ip;
    }

    public Socket getClientSocket() {
        return clientSocket;
    }

    public void establishConnection() {
        try {
            clientSocket = new Socket(ip, port);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
