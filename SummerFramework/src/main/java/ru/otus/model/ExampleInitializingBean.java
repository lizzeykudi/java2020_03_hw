package ru.otus.model;

import ru.otus.summerFramework.summerFramework.InitializingBean;

public class ExampleInitializingBean implements InitializingBean {

    public ExampleInitializingBean() {
        System.out.println("constructor of class "+this.getClass().getCanonicalName());
    }

    @Override
    public void afterPropertiesSet() {
        System.out.println("afterPropertiesSet of class "+this.getClass().getCanonicalName());
    }
}
