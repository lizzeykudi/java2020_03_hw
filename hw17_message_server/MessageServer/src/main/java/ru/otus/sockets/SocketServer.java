package ru.otus.sockets;

import ru.otus.messagesystem.MessageSystem;
import ru.otus.messagesystem.message.Message;
import ru.otus.messagesystem.message.MessageHelper;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

public class SocketServer {
    private static final int PORT = 8090;

    private final MessageSystem messageSystem;
    private final Map<Socket, PrintWriter> clientOutputs = new ConcurrentHashMap<>();

    public SocketServer(MessageSystem messageSystem) {
        this.messageSystem = messageSystem;
        messageSystem.setMessageConsumer(this::sendMessage);
    }

    public void go() {
        //DatagramSocket - UDP
        System.out.println("waiting for client connection");
        try (ServerSocket serverSocket = new ServerSocket(PORT)) {
            while (!Thread.currentThread().isInterrupted()) {
                Thread thread = new Thread(() -> {
                    try (Socket clientSocket = serverSocket.accept()) {
                        System.out.println("Client connects");
                        messageSystem.addClient(clientSocket);
                        try (
                                PrintWriter outputStream = new PrintWriter(clientSocket.getOutputStream(), true);
                        ) {
                            clientOutputs.put(clientSocket, outputStream);
                            handleClientConnection(clientSocket);
                        }
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                });

                thread.start();

            }
        } catch (Exception ex) {
            ex.printStackTrace();
        }
    }

    private void handleClientConnection(Socket clientSocket) {
        try (
                BufferedReader in = new BufferedReader(new InputStreamReader(clientSocket.getInputStream()))
        ) {
            System.out.println("Handling connection for " + clientSocket.toString());
            while(true) {
                String message = in.readLine();
                System.out.printf("Received from %s: %s\n", clientSocket, message);
                messageSystem.newMessage(MessageHelper.deSerializeMessage(message));
            }
        } catch (Exception ex) {
            ex.printStackTrace();
        }
        System.out.println();
    }

    private void sendMessage(Socket clientSocket, Message message) {
        PrintWriter outputStream = clientOutputs.get(clientSocket);

        String serializedMessage = MessageHelper.serializeMessageToString(message);
        System.out.printf("Sending to %s: %s\n", clientSocket, serializedMessage);
        outputStream.println(serializedMessage);

    }


}
