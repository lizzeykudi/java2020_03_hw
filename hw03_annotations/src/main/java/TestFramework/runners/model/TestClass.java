package TestFramework.runners.model;

import java.lang.annotation.Annotation;
import java.lang.reflect.Method;
import java.util.*;


public class TestClass {

    private final Class<?> clazz;
    private Map<Class<? extends Annotation>, List<Method>> methodsForAnnotatios = new HashMap<>();

    public TestClass(Class<?> clazz) {
        this.clazz = clazz;
        scanAnnotatedMethods();
    }

    public List<Method> getAnnotatedMethods(
            Class<? extends Annotation> annotationClass) {
        return methodsForAnnotatios.get(annotationClass);
    }

    protected void scanAnnotatedMethods() {



        Method[] declaredMethods = clazz.getDeclaredMethods();



        for (Method method : declaredMethods) {
            Annotation[] methodAnnotations = method.getAnnotations();

            for (Annotation annotation : methodAnnotations) {

                List<Method> methods = methodsForAnnotatios.get(annotation.annotationType());
                if (methods == null) {
                    methods = new ArrayList<>();
                    methodsForAnnotatios.put(annotation.annotationType(), methods);
                }
                methods.add(method);

            }
        }
    }

    public Class<?> getClazz() {
        return clazz;
    }
}
