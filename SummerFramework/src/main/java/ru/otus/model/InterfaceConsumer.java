package ru.otus.model;

import java.util.List;

public class InterfaceConsumer {

    Interface implementation;

    public InterfaceConsumer(Interface implementation) {
        System.out.println("constructor of class "+this.getClass().getCanonicalName());
        this.implementation = implementation;
        implementation.print();
    }

}
