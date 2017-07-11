package com.client;

public class RunClient {
    public static void main(String[] args) {

        String ip = args[0];
        int port = Integer.parseInt(args[1]);

        ConnectionService connectionService  = new ConnectionService(port, ip);
        connectionService.establishConnection();

        MessagingService messagingService = new MessagingService(connectionService);
        messagingService.start();

    }
}
