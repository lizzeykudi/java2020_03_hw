package ru.otus.hibernate.cachehw;

public class MyListener implements HwListener {
    @Override
    public void notify(Object key, Object value, String action) {
        System.out.println(action + " " + key + " = " + value);
    }
}
