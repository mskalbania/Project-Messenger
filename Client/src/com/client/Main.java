package com.client;

public class Main {
    public static void main(String[] args) {

        ConnectionService connectionService  = new ConnectionService(8080, "127.0.0.1");
        connectionService.establishConnection();

        MessagingService messagingService = new MessagingService(connectionService);
        messagingService.start();

    }
}
