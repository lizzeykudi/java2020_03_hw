package ru.otus.sockets;

import ru.otus.messagesystem.MessageSystem;
import ru.otus.messagesystem.MessageSystemImpl;
import ru.otus.messagesystem.message.Message;
import ru.otus.messagesystem.message.MessageHelper;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.List;

public class SocketServer {
    private static final int PORT = 8090;
    List<Socket> clientSocketbd;

    private MessageSystem messageSystem;

    public SocketServer(MessageSystem messageSystem) {
        this.messageSystem = messageSystem;
        messageSystem.setMessageConsumer(this::sendMessage);
    }

    public static void main(String[] args) {
        new SocketServer(new MessageSystemImpl()).go();
    }

    private void go() {
        //DatagramSocket - UDP
        System.out.println("waiting for client connection");
        try (ServerSocket serverSocket = new ServerSocket(PORT)) {
            while (!Thread.currentThread().isInterrupted()) {
                Thread thread = new Thread(){
                    public void run(){
                        try (Socket clientSocket = serverSocket.accept()) {
                            messageSystem.addClient(clientSocket);
                            handleClientConnection(clientSocket);
                        } catch (IOException e) {
                            e.printStackTrace();
                        }
                    }
                };

                thread.start();

            }
        } catch (Exception ex) {
            ex.printStackTrace();
        }
    }

    private void handleClientConnection(Socket clientSocket) {
        try (
                PrintWriter outptStream = new PrintWriter(clientSocket.getOutputStream(), true);
                BufferedReader in = new BufferedReader(new InputStreamReader(clientSocket.getInputStream()))
        ) {
            String input = "";
            while (!"stop".equals(input)) {
                input += in.readLine();
            }
            messageSystem.newMessage(MessageHelper.deSerializeMessage(input.getBytes()));
        } catch (Exception ex) {
            ex.printStackTrace();
        }
        System.out.println();
    }

    private void sendMessage(Socket clientSocket, Message message) {
        try (
                PrintWriter outptStream = new PrintWriter(clientSocket.getOutputStream(), true);
                BufferedReader in = new BufferedReader(new InputStreamReader(clientSocket.getInputStream()))
        ) {
            outptStream.println(String.valueOf(MessageHelper.serializeMessage(message))+"\nstop");

        } catch (Exception ex) {
            ex.printStackTrace();
        }
        System.out.println();
    }


}
