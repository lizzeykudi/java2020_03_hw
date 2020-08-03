package ru.otus.appcontainer;

import org.reflections.Reflections;
import org.reflections.scanners.MethodAnnotationsScanner;
import ru.otus.appcontainer.api.AppComponent;
import ru.otus.appcontainer.api.AppComponentsContainer;
import ru.otus.appcontainer.api.AppComponentsContainerConfig;
import ru.otus.config.AppConfig;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.lang.reflect.Parameter;
import java.lang.reflect.Proxy;
import java.util.*;
import java.util.stream.Collectors;

public class AppComponentsContainerImpl implements AppComponentsContainer {

    private final List<Object> appComponents = new ArrayList<>();
    private final Map<String, Object> appComponentsByName = new HashMap<>();

    public AppComponentsContainerImpl(Class<?> initialConfigClass) {
        processConfig(initialConfigClass);
    }

    private void processConfig(Class<?> configClass) {
        checkConfigClass(configClass);
        Map<Integer, List<String>> map = new TreeMap();
        Map<String, Method> methods = new HashMap<>();
        Arrays.stream(configClass.getDeclaredMethods()).forEach(method -> {
            AppComponent annotation = method.getAnnotation(AppComponent.class);

            if (annotation!=null) {
            int order = annotation.order();
            String beanName = annotation.name();
            methods.put(beanName, method);

            if (map.get(order)==null) {List<String> values = new ArrayList<>(); values.add(beanName); map.put(order, values);}
            else {map.get(order).add(beanName);} }
        });

        for(Integer order : map.keySet()) {
            List<String> list = map.get(order);
            list.stream().forEach(beanName -> {
                Method method = methods.get(beanName);
                Parameter[] parameters = method.getParameters();
                List<Object> collect = Arrays.stream(parameters).map(parameter -> {
                    return getAppComponent(parameter.getType());
                }).collect(Collectors.toList());


                try {
                    Object invoke = method.invoke(new AppConfig(), collect.toArray());
                    appComponents.add(invoke);
                    appComponentsByName.put(beanName, invoke);
                } catch (IllegalAccessException e) {
                    e.printStackTrace();
                } catch (InvocationTargetException e) {
                    e.printStackTrace();
                }

            });
        }

    }

    private void checkConfigClass(Class<?> configClass) {
        if (!configClass.isAnnotationPresent(AppComponentsContainerConfig.class)) {
            throw new IllegalArgumentException(String.format("Given class is not config %s", configClass.getName()));
        }
    }

    @Override
    public <C> C getAppComponent(Class<C> componentClass) {
        return (C) appComponents.stream().filter(o -> componentClass.isAssignableFrom(o.getClass())).findFirst().get();

    }

    @Override
    public <C> C getAppComponent(String componentName) {
        return (C) appComponentsByName.get(componentName);
    }
}
