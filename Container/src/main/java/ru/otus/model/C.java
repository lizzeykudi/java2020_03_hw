package ru.otus.model;

public class C {

    String value;

    public C(String value) {
        System.out.println("constructor of class "+this.getClass().getCanonicalName());
        this.value = value;
    }
}
