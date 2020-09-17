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

public class SocketServer {
    private static final int PORT = 8090;
    MessageSystem messageSystem = new MessageSystemImpl();

    public static void main(String[] args) {
        new SocketServer().go();
    }

    private void go() {
        //DatagramSocket - UDP
        System.out.println("waiting for client connection");
        try (ServerSocket serverSocket = new ServerSocket(PORT)) {
            while (!Thread.currentThread().isInterrupted()) {
                Thread thread = new Thread(){
                    public void run(){
                        try (Socket clientSocket = serverSocket.accept()) {
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
                PrintWriter outputStream = new PrintWriter(clientSocket.getOutputStream(), true);
                BufferedReader in = new BufferedReader(new InputStreamReader(clientSocket.getInputStream()))
        ) {
            String input = null;
            while (!"stop".equals(input)) {
                input = in.readLine();
                if (input != null) {
                    Message message = MessageHelper.deSerializeMessage(input.getBytes());
                    boolean b = messageSystem.newMessage(message);
                    System.out.println(String.format("from client: %s", input));
                    outputStream.println(String.format("%s I Can Fly!", input));
                }
            }
        } catch (Exception ex) {
            ex.printStackTrace();
        }
        System.out.println();
    }


}
