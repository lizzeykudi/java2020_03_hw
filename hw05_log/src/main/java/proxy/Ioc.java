package proxy;

import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Method;
import java.lang.reflect.Proxy;
import java.util.HashSet;
import java.util.List;
import java.util.stream.Collectors;

class Ioc {

    private Ioc() {
    }

    static MyClassInterface createMyClass() {
        InvocationHandler handler = new DemoInvocationHandler(new MyClassImpl());
        return (MyClassInterface) Proxy.newProxyInstance(Ioc.class.getClassLoader(),
                new Class<?>[]{MyClassInterface.class}, handler);
    }

    static class DemoInvocationHandler implements InvocationHandler {
        private final MyClassInterface myClass;
        HashSet<String> annotatedMethodNames;

        DemoInvocationHandler(MyClassInterface myClass) {
            this.myClass = myClass;
            List<Method> annotatedMethods = TestClass.scanAnnotatedMethods(myClass.getClass(), Log.class);
            this.annotatedMethodNames = annotatedMethods.stream().map(Method::getName).collect(Collectors.toCollection(HashSet::new));

        }

        @Override
        public Object invoke(Object proxy, Method method, Object[] args) throws Throwable {

            if (annotatedMethodNames!=null&&annotatedMethodNames.contains(method.getName())) {
                System.out.println("invoking method:" + method);
            }
            return method.invoke(myClass, args);
        }

        @Override
        public String toString() {
            return "DemoInvocationHandler{" +
                    "myClass=" + myClass +
                    '}';
        }
    }
}
