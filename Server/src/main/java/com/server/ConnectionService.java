package com.server;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.Collections;
import java.util.HashSet;
import java.util.Set;

public class ConnectionService extends Thread {

    private final MessagingService messagingService;
    private int connectionsNumber = 0;
    private final int maxSocketAmount;
    private final ServerSocket serverSocket;
    private final Set<User> userSet;

    public ConnectionService(int port, int maxSocketAmount, MessagingService messagingService) throws IOException {

        this.maxSocketAmount = maxSocketAmount;
        this.serverSocket = new ServerSocket(port);
        this.messagingService = messagingService;
        userSet = Collections.synchronizedSet(new HashSet<>());
    }

    private void establishConnections() throws IOException {
        if (userSet.size() < maxSocketAmount) {
            Socket socket = serverSocket.accept();
            System.out.println("\n>>USER " + socket.getRemoteSocketAddress() + " CONNECTED<<\n");
            User tempUser = new User(new BufferedReader(new InputStreamReader(socket
                    .getInputStream())).readLine(), socket);

            userSet.add(tempUser);
            messagingService.addUser(tempUser);
        }
    }

    @Override
    public void run() {
        try {
            while (true) {
                establishConnections();
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
