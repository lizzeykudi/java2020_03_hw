package ru.otus.dbserver;

import org.springframework.context.annotation.AnnotationConfigApplicationContext;


public class DbServerRunner {
    public static void main(String[] args) {
        AnnotationConfigApplicationContext applicationContext = new AnnotationConfigApplicationContext(ApplConfig.class);
    }

}
