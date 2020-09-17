package ru.otus.messagesystem;

import ru.otus.messagesystem.client.MsClient;
import ru.otus.messagesystem.message.Message;

import java.net.Socket;
import java.util.function.BiConsumer;

public interface MessageSystem {

    public void setMessageConsumer(BiConsumer<Socket, Message> messageConsumer);

    void addClient(Socket msClient);

    boolean newMessage(Message msg);

    void dispose() throws InterruptedException;

    void dispose(Runnable callback) throws InterruptedException;

    void start();

    int currentQueueSize();
}

