package com.client;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.util.Scanner;

public class MessagingService {

    private ConnectionService connectionService;
    private Scanner scanner;
    private BufferedReader bufferedReader;
    private OutputStreamWriter outputStreamWriter;

    public MessagingService(ConnectionService connectionService) {
        try {
            this.connectionService = connectionService;
            this.bufferedReader = new BufferedReader(new InputStreamReader(connectionService.getClientSocket().getInputStream()));
            this.outputStreamWriter = new OutputStreamWriter(connectionService.getClientSocket().getOutputStream());
            this.scanner = new Scanner(System.in);
            userNameSetup();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private void userNameSetup() throws IOException {
        System.out.println("SELECT YOUR USERNAME: ");
        writeToServer(scanner.next());
    }

    public void start() {
        Thread readerThread = new Thread(new Reader());
        Thread writerThread = new Thread(new Writer());
        readerThread.start();
        writerThread.start();
    }

    private String readLineFromServer() throws IOException {
        return bufferedReader.readLine();
    }

    private void writeToServer(String string) throws IOException {
        outputStreamWriter.write(string);
        outputStreamWriter.flush();
    }

    class Writer implements Runnable {
        @Override
        public void run() {
            while (true) {
                try {
                    writeToServer(scanner.nextLine() + "\n");
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
    }

    class Reader implements Runnable {
        @Override
        public void run() {
            while (true) {
                try {
                    String read = readLineFromServer();
                    if (read != null) {
                        System.out.println(read);
                    }else {
                        System.out.println("DISCONNECTED FROM SERVER. EXITING...");
                        System.exit(0);
                    }
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
    }
}
