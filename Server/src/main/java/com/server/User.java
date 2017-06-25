package com.server;

import java.io.*;
import java.net.Socket;

public class User {
    private Socket userSocket;
    private String name;
    private BufferedReader reader;
    private BufferedWriter writer;

    public User(String name, Socket socket) throws IOException{
        setReaderAndWriter(socket);
        this.name = name;
        this.userSocket = socket;
    }

    public User(Socket userSocket) {
        this.userSocket = userSocket;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setReaderAndWriter(Socket socket) throws IOException {
        reader = new BufferedReader(new InputStreamReader(socket.getInputStream()));
        writer = new BufferedWriter(new OutputStreamWriter(socket.getOutputStream()));
    }

    public BufferedReader getReader() {
        return reader;
    }

    public BufferedWriter getWriter() {
        return writer;
    }

    public String getName() {
        return name;
    }

    public Socket getUserSocket() {
        return userSocket;
    }
}


