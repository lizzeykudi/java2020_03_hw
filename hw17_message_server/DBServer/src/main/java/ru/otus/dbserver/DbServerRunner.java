package ru.otus.dbserver;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;

@SpringBootApplication
public class DbServerRunner {
    public static void main(String[] args) {
        SpringApplication.run(DbServerRunner.class, args);

    }

}
