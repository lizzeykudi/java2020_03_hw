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
    Result result = new Result();

    public Runner(Class<?> testClass) {
        this.testClass = new TestClass(testClass);
    }

    public Result run() {
        List<Method> beforeMethods = this.testClass.getAnnotatedMethods(Before.class);
        List<Method> testMethods = testClass.getAnnotatedMethods(Test.class);
        List<Method> afterMethods = this.testClass.getAnnotatedMethods(After.class);
        testMethods.forEach(method -> {
            System.out.println(">>>> run " + method.getName());
            runShedule(prepareShedule(method, beforeMethods, afterMethods));
        });
        System.out.println("\n\n");
        return result;
    }

    public List<Method> prepareShedule(Method method, List<Method> beforeMethods, List<Method> afterMethods) {
        List<Method> annotatedMethods = new ArrayList<>();
        annotatedMethods.addAll(beforeMethods);
        annotatedMethods.add(method);
        annotatedMethods.addAll(afterMethods);
        return annotatedMethods;
    }

    public void runShedule(List<Method> annotatedMethods) {
        Object instance = null;
        try {
            instance = testClass.getClazz().newInstance();
        } catch (InstantiationException e) {
            e.printStackTrace();
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        }

        result.setTotal(result.getTotal() + 1);
        for (Method method : annotatedMethods) {
            try {
                method.invoke(instance);
                System.out.println("v " + method.getName());
            } catch (IllegalAccessException e) {
                e.printStackTrace();
            } catch (InvocationTargetException e) {
                result.setFail(result.getFail() + 1);
                System.out.println("x " + method.getName());
                e.printStackTrace();
                return;
            }
        }


    }
}
