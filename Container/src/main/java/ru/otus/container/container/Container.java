package ru.otus.container.container;

import ru.otus.container.annotations.Bean;
import ru.otus.container.annotations.Configurations;

import java.util.*;

public class Container {

    private BeanFactory beanFactory = new BeanFactory();

    public Container(Class<?> initialConfigClass) {
        scan(initialConfigClass);
    }

    private void scan(Class<?> configClass) {
        checkConfigClass(configClass);
        Map<String, BeanDefinitions> beansDefinitions = new HashMap<>();
        Map<Class<?>, BeanDefinitions> beansDefinitionsByType = new HashMap<>();
        Arrays.stream(configClass.getDeclaredMethods()).forEach(method -> {
            Bean annotation = method.getAnnotation(Bean.class);
            if (annotation!=null) {
            String beanName = annotation.name();
            beansDefinitions.put(beanName, new BeanDefinitions(method));
            beansDefinitionsByType.put(method.getReturnType(), new BeanDefinitions(method));
            }
        });

        beanFactory.setBeansDefinitions(beansDefinitions);
        beanFactory.setBeansDefinitionsByType(beansDefinitionsByType);
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



    private void checkConfigClass(Class<?> configClass) {
        if (!configClass.isAnnotationPresent(Configurations.class)) {
            throw new IllegalArgumentException(String.format("Given class is not config %s", configClass.getName()));
        }
    }

}
