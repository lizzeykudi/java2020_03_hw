package ru.otus;

import org.apache.catalina.core.ApplicationContext;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import ru.otus.messagesystem.MessageSystemImpl;
import ru.otus.sockets.SocketServer;


@Configuration
@ComponentScan({"ru.otus.messagesystem", "ru.otus.sockets"})
@SpringBootApplication
public class MessageServerRunner {
    public static void main(String[] args) {
        SpringApplication.run(MessageServerRunner.class, args);
        AnnotationConfigApplicationContext applicationContext = new AnnotationConfigApplicationContext(MessageServerRunner.class);
        SocketServer socketServer = applicationContext.getBean(SocketServer.class);
        socketServer.go();
    }
}
