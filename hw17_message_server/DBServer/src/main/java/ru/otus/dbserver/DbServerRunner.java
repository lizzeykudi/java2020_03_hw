package ru.otus.dbserver;

import org.springframework.context.annotation.AnnotationConfigApplicationContext;
import ru.otus.messagesystem.HandlersStore;


public class DbServerRunner {
    public static void main(String[] args) {
        AnnotationConfigApplicationContext applicationContext = new AnnotationConfigApplicationContext(ApplConfig.class);
    }

}
