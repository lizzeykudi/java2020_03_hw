package ru.otus.sockets;


import ru.otus.messagesystem.message.MessageHelper;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;
import java.util.concurrent.TimeUnit;
import java.util.function.Consumer;

public class SocketClient {
    private static final int PORT = 8090;
    private static final String HOST = "localhost";
    Socket clientSocket;
    PrintWriter outputStream;
    BufferedReader in;

    Consumer<String> readCallback;

    public void setReadCallback(Consumer<String> readCallback) {
        this.readCallback = readCallback;
    }

    public SocketClient() {
        try {
            clientSocket = new Socket(HOST, PORT);
            outputStream = new PrintWriter(clientSocket.getOutputStream(), true);
            in = new BufferedReader(new InputStreamReader(clientSocket.getInputStream()));

            Thread thread = new Thread() {
                public void run() {
                    while (true) {
                        String read = read();
                        if (read!=null&&!"".equals(read)) {
                        readCallback.accept(read); }
                    }
                }
            };
            thread.start();
        } catch (Exception ex) {
            ex.printStackTrace();
        }
    }

    public void send(String msg) {
        try {
            outputStream.println(msg);
            System.out.println("Sent to server:" + msg);

        } catch (Exception ex) {
            ex.printStackTrace();
        }

    }

    public String read() {
        try {
            String responseMsg = "";
            responseMsg = in.readLine();

//            Thread.sleep(TimeUnit.SECONDS.toMillis(3));

            /*System.out.println("\nstop communication");
            outputStream.println("stop");*/
            if (responseMsg!=null) {
                System.out.println("Received from server: " + responseMsg);
            return responseMsg; }
            else {return "";}
        } catch (Exception ex) {
            ex.printStackTrace();
        }
        return null;
    }
}
