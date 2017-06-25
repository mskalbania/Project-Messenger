package com.server;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.IOException;
import java.util.*;

public class MessagingService extends Thread {

    private final List<Message> messagePool;
    private final Set<User> userSet;
    private final Set<User> disconnectedUsers;

    public MessagingService() {
        messagePool = Collections.synchronizedList(new LinkedList<>());
        userSet = Collections.synchronizedSet(new HashSet<>());
        disconnectedUsers = Collections.synchronizedSet(new HashSet<>());
    }

    public void readMessagesFromUsers() throws IOException {
        if (userSet.size() > 1) {
            for (User user : userSet) {
                BufferedReader reader = user.getReader();
                if (reader.ready()) {
                    messagePool.add(new Message(user.getName(), user.getReader().readLine()));
                }
            }
        }
    }

    public void addUser(User user) {
        userSet.add(user);
    }

    public synchronized void sendMessageToUsers() {

        if (userSet.size() > 1) {
            Message tempMessage = messagePool.get(0);
            BufferedWriter tempWriter;
            System.out.println("Server intercepted -> " + tempMessage.getSender() + ": " + tempMessage.getContent());
            for (User user : userSet) {
                if (!tempMessage.getSender().equals(user.getName())) {
                    try {
                        tempWriter = user.getWriter();
                        tempWriter.write(tempMessage.getSender() + ": " + tempMessage.getContent() + "\n");
                        tempWriter.flush();
                    } catch (IOException e){
                        System.out.println("\n>>USER " + user.getUserSocket().getRemoteSocketAddress() + " DISCONNECTED<<\n");
                        disconnectedUsers.add(user);
                    }
                }
            }
            messagePool.remove(0);
            userSet.removeAll(disconnectedUsers);
        }
    }

    @Override
    public void run() {
        try {
            while (true) {
                if (messagePool.isEmpty()) {
                    readMessagesFromUsers();
                } else {
                    sendMessageToUsers();
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
