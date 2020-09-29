package ru.otus;

import ru.otus.summerFramework.summerFramework.SummerFramework;
import ru.otus.config.AppConfig;

public class App {

    public static void main(String[] args) {
        SummerFramework container = new SummerFramework(AppConfig.class);
        //C c = container.getBean("c");
    }
}
