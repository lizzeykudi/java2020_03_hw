package ru.otus.model;

public class Implementation2 implements Interface{

    public Implementation2() {
        System.out.println("constructor of class "+this.getClass().getCanonicalName());
    }

    @Override
    public void print() {
        System.out.println("print "+this.getClass().getCanonicalName());
    }
}
