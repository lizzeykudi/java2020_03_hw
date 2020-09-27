package ru.otus.model;

public class Implementation1 implements Interface{

    public Implementation1() {
        System.out.println("constructor of class "+this.getClass().getCanonicalName());

    }

    @Override
    public void print() {
        System.out.println("print "+this.getClass().getCanonicalName());

    }
}
