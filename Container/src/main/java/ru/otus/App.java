package ru.otus;

import ru.otus.container.container.Container;
import ru.otus.config.AppConfig;
import ru.otus.model.C;

public class App {

    public static void main(String[] args) {
        Container container = new Container(AppConfig.class);
        //C c = container.getBean("c");
    }
}
