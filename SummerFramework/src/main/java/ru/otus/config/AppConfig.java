package ru.otus.config;

import ru.otus.summerFramework.annotations.*;
import ru.otus.summerFramework.summerFramework.BeanPostProcessor;
import ru.otus.model.*;

@Configurations()
public class AppConfig {

   /* @Bean(name = "a")
    @Scope("prototype")
    public A a() {
        return new A();
    }*/

   /* @Bean(name = "b")
    @Lazy
    public B b(A a) {
        return new B(a);
    }*/

    @Bean(name = "c")
    //@DependsOn({"a", "b"})
    public C c(@Value("value") String value) {
        return new C(value);
    }
    /*@Bean(name = "implementation1")
    public Interface implementation1() {
        return new Implementation1();
    }

    @Bean(name = "implementation2")
    public Interface implementation2() {
        return new Implementation2();
    }

    @Bean(name = "interfaceConsumer")
    @DependsOn({"implementation1", "implementation2"})
    public InterfaceConsumer interfaceConsumer
            (@Qualifier("implementation1") Interface implementation) {
        return new InterfaceConsumer(implementation);
    }*/

    /*@Bean(name = "exampleBeanPostPricessor")
    public BeanPostProcessor exampleBeanPostPricessor() {
        return new ExampleBeanPostPricessor();
    }

    @Bean(name = "exampleInitializingBean")
    public ExampleInitializingBean exampleInitializingBean() {
        return new ExampleInitializingBean();
    }
*/}
