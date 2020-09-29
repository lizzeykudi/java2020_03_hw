package ru.otus.summerFramework.summerFramework;

public class SummerFramework {

    private BeanFactory beanFactory = new BeanFactory();

    public SummerFramework(Class<?> initialConfigClass) {
        new BeanDefinitionReader(beanFactory).readBeanDefinition(initialConfigClass);

        beanFactory.createBeans();
        beanFactory.postProcessBeforeInitialization();
        beanFactory.afterPropertiesSet();
        beanFactory.postProcessAfterInitialization();


    }

    public <C> C getBean(Class<C> beanClass) {
        return beanFactory.getBeanRegistry().getBean(beanClass);
    }

    public <C> C getBean(String beanName) {
        return beanFactory.getBeanRegistry().getBean(beanName);
    }


}
