package TestFramework.runner;

import TestFramework.annotations.After;
import TestFramework.annotations.Before;
import TestFramework.annotations.Test;
import TestFramework.runners.model.TestClass;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.List;

public class Runner {

    private final TestClass testClass;
    int all = 0;
    int fail = 0;

    public Runner(Class<?> testClass) {
        this.testClass = new TestClass(testClass);
    }

    public Result run() {
        List<Method> annotatedMethods = new ArrayList<>();
        annotatedMethods.addAll(testClass.getAnnotatedMethods(Before.class));
        annotatedMethods.addAll(testClass.getAnnotatedMethods(Test.class));
        annotatedMethods.addAll(testClass.getAnnotatedMethods(After.class));
        annotatedMethods.forEach(method -> {
            try {
                all++;
                method.invoke(testClass.getClazz().newInstance());
            } catch (IllegalAccessException e) {
                e.printStackTrace();
            } catch (InvocationTargetException e) {
                fail++;
                e.printStackTrace();
            } catch (InstantiationException e) {
                e.printStackTrace();
            }
        });

        System.out.println("all="+all+ " failed="+fail);
        return new Result();
    }
}
