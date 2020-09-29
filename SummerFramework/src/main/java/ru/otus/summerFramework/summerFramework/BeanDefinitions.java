package ru.otus.summerFramework.summerFramework;

import ru.otus.summerFramework.annotations.*;

import java.lang.reflect.Method;
import java.lang.reflect.Parameter;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class BeanDefinitions {

    private String name;
    private Method method;
    private boolean lazy;
    private List<String> dependsOn;
    private List<String> valueFields = new ArrayList<>();
    private List<String> qualifierFields = new ArrayList<>();
    private List<Class<?>> autowiredFields = new ArrayList<>();

    public BeanDefinitions(Method method) {
        name = method.getAnnotation(Bean.class).name();
        this.method = method;
        lazy = method.getAnnotation(Lazy.class) != null;
        dependsOn = dependsOn(method);
        defineAutowiredTypeFields();
    }

    private void defineAutowiredTypeFields() {
        Parameter[] parameters = method.getParameters();
        Arrays.stream(parameters).forEach(parameter -> {
            defineAutowiredTypeField(parameter);
        });
    }

    private void defineAutowiredTypeField(Parameter parameter) {

        Value value = parameter.getAnnotation(Value.class);

        if (value!=null) {
            valueFields.add(value.value());
            return;
        }

        Qualifier qualifier = parameter.getAnnotation(Qualifier.class);
        if (qualifier!=null) {
            qualifierFields.add(qualifier.value());
            return;
        }

            autowiredFields.add(parameter.getType());
    }

    public boolean isPrototype() {
        Scope scope = method.getAnnotation(Scope.class);
        return (scope!=null&&"prototype".equals(scope.value()));
    }

    private List<String> dependsOn(Method method) {
        DependsOn dependsOnAnnotation = method.getAnnotation(DependsOn.class);
        if (dependsOnAnnotation!=null) {
            return Arrays.asList(dependsOnAnnotation.value());
        } else {
            return new ArrayList<>(0);
        }
    }

    public boolean isLazy() {
        return lazy;
    }

    public List<String> getQualifierFields() {
        return qualifierFields;
    }

    public List<Class<?>> getAutowiredFields() {
        return autowiredFields;
    }

    public List<String> getValueFields() {
        return valueFields;
    }

    public Method getMethod() {
        return method;
    }

    public String getName() {
        return name;
    }

    public List<String> getDependsOn() {
        return dependsOn;
    }
}
