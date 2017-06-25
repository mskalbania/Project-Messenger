package com.server;


import java.io.IOException;

public class Server {

    private final ConnectionService connectionService;
    private final MessagingService messagingService;

    public Server(int port, int maxSocketAmount) throws IOException {
        messagingService = new MessagingService();
        connectionService = new ConnectionService(port, maxSocketAmount, messagingService);
    }

    public void start() {
        System.out.println("SERVER STARTED");
        connectionService.start();
        messagingService.start();
    }
}
