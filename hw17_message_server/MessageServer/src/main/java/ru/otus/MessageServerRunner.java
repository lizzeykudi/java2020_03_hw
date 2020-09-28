package ru.otus;

import org.springframework.context.annotation.AnnotationConfigApplicationContext;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import ru.otus.sockets.SocketServer;


@Configuration
@ComponentScan({"ru.otus.messagesystem.server", "ru.otus.sockets"})
public class MessageServerRunner {



    public static void main(String[] args) {
        AnnotationConfigApplicationContext applicationContext = new AnnotationConfigApplicationContext(MessageServerRunner.class);
        SocketServer socketServer = applicationContext.getBean(SocketServer.class);
        socketServer.go();
    }
}
