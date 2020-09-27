package ru.otus.model;

import ru.otus.container.container.BeanPostProcessor;

public class ExampleBeanPostPricessor implements BeanPostProcessor {

    public ExampleBeanPostPricessor() {
        System.out.println("constructor of class "+this.getClass().getCanonicalName());
    }

    @Override
    public Object postProcessBeforeInitialization(Object bean, String beanName) {
        System.out.println("postProcessBeforeInitialization of class "+this.getClass().getCanonicalName() + " for bean "+ beanName + " " + bean);
        return bean;
    }

    @Override
    public Object postProcessAfterInitialization(Object bean, String beanName) {
        System.out.println("postProcessAfterInitialization of class "+this.getClass().getCanonicalName() + " for bean "+ beanName + " " + bean);
        return bean;
    }
}
