package ru.otus.summerFramework.summerFramework;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class BeanRegistry {

    private BeanFactory beanFactory;

    private Map<String, Object> beans = new HashMap<>();

    private Map<Class<?>, List<Object>> beansByType = new HashMap<>();

    public BeanRegistry(BeanFactory beanFactory) {
        this.beanFactory = beanFactory;
    }

    public void register(String beanName, Object bean) {
        beans.put(beanName, bean);
        List<Object> beansOfType = beansByType.get(bean.getClass());
        if (beansOfType==null) {
            beansOfType = new ArrayList<>();
            beansOfType.add(bean);
            beansByType.put(bean.getClass(), beansOfType);
        } else {
        beansOfType.add(bean);}

    }

    public <C> C getBean(Class<C> beanClass) {
        Object bean = findBean(beanClass);
        if (bean==null||beanFactory.getBeanDefinition(beanClass).isPrototype()) {
            bean = beanFactory.createBean(beanClass);
        }
        return (C) bean;
    }

    public <C> C getBean(String beanName) {
        Object bean = findBean(beanName);
        if (bean==null||beanFactory.getBeanDefinition(beanName).isPrototype()) {
            bean = beanFactory.createBean(beanName);
        }
        return (C) bean;
    }

    private  <C> C findBean(Class<C> beanClass) {
        List<Object> beans = beansByType.get(beanClass);
        if (beans==null||beans.size()==0) {return null;}
        if (beans.size()>1) {throw new RuntimeException("NoUniqueBeanDefinitionException"+beanClass);}
        return (C) beans.get(0);
    }

    private  <C> C findBean(String beanName) {
        return (C) beans.get(beanName);
    }

    public Map<String, Object> getBeans() {
        return beans;
    }
}
