package ru.otus.container.container;

import ru.otus.config.AppConfig;

import java.lang.reflect.InvocationTargetException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class BeanFactory {

    private BeanRegistry beanRegistry = new BeanRegistry(this);
    private Map<String, BeanDefinitions> beansDefinitions = new HashMap<>();
    private Map<Class<?>, BeanDefinitions> beansDefinitionsByType = new HashMap<>();

    private List<BeanPostProcessor> beanPostProcessors = new ArrayList<>();
    private List<InitializingBean> initializingBeans = new ArrayList<>();

    public void createBeans() {
        for (String beanName : beansDefinitions.keySet()) {
            BeanDefinitions beanDefinition = beansDefinitions.get(beanName);
            if (!beanDefinition.isLazy()&&!beanDefinition.isPrototype()) {
                beanRegistry.getBean(beanName);
            }
        }
    }

    public Object createBean(Class<?> beanClassType) {
        BeanDefinitions beanDefinition = beansDefinitionsByType.get(beanClassType);
        return createBean(beanDefinition);
    }

    public Object createBean(String beanName) {
        return createBean(beansDefinitions.get(beanName));
    }

    public Object createBean(BeanDefinitions beanDefinition) {
        beanDefinition.getDependsOn().forEach(depend->{beanRegistry.getBean(depend);});
        List<Object> fields = findFields(beanDefinition);
        try {
            Object invoke = beanDefinition.getMethod().invoke(new AppConfig(), fields.toArray());
            if (invoke instanceof InitializingBean) {
                initializingBeans.add((InitializingBean) invoke);
            }
            if (invoke instanceof BeanPostProcessor) {
                beanPostProcessors.add((BeanPostProcessor) invoke);
            } else {
            beanRegistry.register(beanDefinition.getName(), invoke);}
            return invoke;
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        } catch (InvocationTargetException e) {
            e.printStackTrace();
        }
        throw new RuntimeException("error with creating bean"+beanDefinition.getName());
    }

    private List<Object> findFields(BeanDefinitions beanDefinition) {
        List<Object> fields = new ArrayList<>();

        beanDefinition.getValueFields().forEach(value -> {
            fields.add(new String(value));
        });

        beanDefinition.getQualifierFields().forEach(fieldName -> {
            fields.add(beanRegistry.getBean(fieldName));
        });

        beanDefinition.getAutowiredFields().forEach(fieldClassType -> {
            fields.add(beanRegistry.getBean(fieldClassType));
        });
        return fields;
    }

    public void postProcessBeforeInitialization() {
        beanRegistry.getBeans().forEach((beanName, bean) -> {
            beanPostProcessors.forEach(beanPostProcessor -> {
                beanPostProcessor.postProcessBeforeInitialization(bean, beanName);
            });
        });
    }

    public void afterPropertiesSet() {
        initializingBeans.forEach(initializingBean -> initializingBean.afterPropertiesSet());
    }

    public void postProcessAfterInitialization() {
        beanRegistry.getBeans().forEach((beanName, bean) -> {
            beanPostProcessors.forEach(beanPostProcessor -> {
                beanPostProcessor.postProcessAfterInitialization(bean, beanName);
            });
        });
    }

    public void setBeansDefinitions(Map<String, BeanDefinitions> beansDefinitions) {
        this.beansDefinitions = beansDefinitions;
    }

    public void setBeansDefinitionsByType(Map<Class<?>, BeanDefinitions> beansDefinitionsByType) {
        this.beansDefinitionsByType = beansDefinitionsByType;
    }

    public BeanDefinitions getBeanDefinition(Class<?> beanClass) {
        return beansDefinitionsByType.get(beanClass);
    }

    public BeanDefinitions getBeanDefinition(String beanName) {
        return beansDefinitions.get(beanName);
    }

    public BeanRegistry getBeanRegistry() {
        return beanRegistry;
    }
}
