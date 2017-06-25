package com.server;

import java.io.IOException;

public class RunServer {
    public static void main(String[] args) {

        int port = Integer.parseInt(args[0]);
        int maxUsers = Integer.parseInt(args[1]);

        try {
            Server server = new Server(port, maxUsers);
            server.start();
        } catch (IOException e) {
            e.printStackTrace();
        }


    }
}
