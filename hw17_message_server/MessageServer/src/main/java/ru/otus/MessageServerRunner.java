package ru.otus;

import ru.otus.messagesystem.MessageSystemImpl;
import ru.otus.sockets.SocketServer;

public class MessageServerRunner {
    public static void main(String[] args) {
        new SocketServer(new MessageSystemImpl()).go();
    }
}
