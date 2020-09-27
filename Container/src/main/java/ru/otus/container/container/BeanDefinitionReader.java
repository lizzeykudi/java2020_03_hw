package ru.otus.container.container;

import ru.otus.container.annotations.Bean;
import ru.otus.container.annotations.Configurations;

import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;

public class BeanDefinitionReader {

    private BeanFactory beanFactory = new BeanFactory();

    public BeanDefinitionReader(BeanFactory beanFactory) {
        this.beanFactory = beanFactory;
    }

    public void readBeanDefinition(Class<?> configClass) {
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

    }

    private void checkConfigClass(Class<?> configClass) {
        if (!configClass.isAnnotationPresent(Configurations.class)) {
            throw new IllegalArgumentException(String.format("Given class is not config %s", configClass.getName()));
        }
    }
}
