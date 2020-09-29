package ru.otus.model;

public class B {

    private A a;

    public B() {
        System.out.println("constructor of class "+this.getClass().getCanonicalName());
    }

    public B(A a) {
        System.out.println("constructor of class "+this.getClass().getCanonicalName());
        this.a = a;
    }

    public void setA(A a) {
        this.a = a;
    }
}
