package proxy;

import java.lang.annotation.Annotation;
import java.lang.reflect.Method;
import java.util.*;


public class TestClass {


    protected static List<Method> scanAnnotatedMethods(Class<?> clazz, Class<? extends Annotation> annotationClass) {
         Map<Class<? extends Annotation>, List<Method>> methodsForAnnotatios = new HashMap<>();

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
        return methodsForAnnotatios.get(annotationClass);
    }
}
