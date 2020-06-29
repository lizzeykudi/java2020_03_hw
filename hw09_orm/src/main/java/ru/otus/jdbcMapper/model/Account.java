package ru.otus.jdbcMapper.model;

import ru.otus.jdbcMapper.annotation.Id;

public class Account {
    @Id
    long no;
    String type;
    Number rest;

    public Account() {
    }

    public Account(String type, Number rest) {
        this.type = type;
        this.rest = rest;
    }

    public long getNo() {
        return no;
    }

    public String getType() {
        return type;
    }

    public Number getRest() {
        return rest;
    }

    public void setType(String type) {
        this.type = type;
    }

    public void setRest(Number rest) {
        this.rest = rest;
    }

    @Override
    public String toString() {
        return "Account{" +
                "no=" + no +
                ", type='" + type + '\'' +
                ", rest=" + rest +
                '}';
    }
}
