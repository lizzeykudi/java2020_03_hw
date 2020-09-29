package ru.otus;

import ru.otus.model.A;
import ru.otus.model.C;
import ru.otus.model.ExampleClass;
import ru.otus.summerFramework.summerFramework.SummerFramework;
import ru.otus.config.AppConfig;

public class App {

    public static void main(String[] args) {
        SummerFramework context = new SummerFramework(AppConfig.class);
        C c = context.getBean(C.class);
    }
}
