package ru.otus.container.container;

import ru.otus.container.annotations.Bean;
import ru.otus.container.annotations.Configurations;

import java.util.*;

public class Container {

    private BeanFactory beanFactory = new BeanFactory();

    public Container(Class<?> initialConfigClass) {
        new BeanDefinitionReader(beanFactory).readBeanDefinition(initialConfigClass);

        beanFactory.createBeans();
        beanFactory.postProcessBeforeInitialization();
        beanFactory.afterPropertiesSet();
        beanFactory.postProcessAfterInitialization();


    }

    public <C> C getBean(Class<C> beanClass) {
        return beanFactory.getBeanRegistry().getBean(beanClass);
    }

    public <C> C getBean(String beanName) {
        return beanFactory.getBeanRegistry().getBean(beanName);
    }


}
