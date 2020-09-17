package ru.otus.sockets;


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
    Socket clientSocket ;
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
                //for (int idx = 0; idx < 100; idx++) {
                //String msg = String.format("##%d - I Believe", idx);


            Thread thread = new Thread(){
                public void run(){
                    while (true) {
                        String read = read();
                        readCallback.accept(read);
                    }
                }
            };
            thread.start();
        } catch (Exception ex) {
            ex.printStackTrace();
        }
    }

    public static void main(String[] args) {
        String msg = String.format("##%d - I Believe", 0);
        new SocketClient().go(msg);
    }

    public void go(String msg) {
        try {
            System.out.println(String.format("sending to server: %s", msg));
            outputStream.println(msg);


        } catch (Exception ex) {
            ex.printStackTrace();
        }

    }

    public String read() {
        try {

            String responseMsg = in.readLine();
            System.out.println(String.format("server response: %s", responseMsg));
            Thread.sleep(TimeUnit.SECONDS.toMillis(3));

            System.out.println();
            //}

            /*System.out.println("\nstop communication");
            outputStream.println("stop");*/
            return responseMsg;
        } catch (Exception ex) {
            ex.printStackTrace();
        }
        return null;
    }
}
