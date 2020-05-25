package proxy;

import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Method;
import java.lang.reflect.Proxy;
import java.util.List;

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
        List<Method> annotatedMethods;

        DemoInvocationHandler(MyClassInterface myClass) {
            this.myClass = myClass;
            this.annotatedMethods = new TestClass(myClass.getClass()).getAnnotatedMethods(Log.class);
        }

        @Override
        public Object invoke(Object proxy, Method method, Object[] args) throws Throwable {

            if (annotatedMethods!=null&&annotatedMethods.stream().anyMatch(method1 -> method1.getName().equals(method.getName()))) {
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
