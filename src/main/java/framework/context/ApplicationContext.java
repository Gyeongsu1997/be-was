package framework.context;

import framework.beans.BeanFactory;
import framework.beans.factory.NoSuchBeanDefinitionException;
import framework.beans.factory.NoUniqueBeanDefinitionException;

import java.lang.reflect.InvocationTargetException;
import java.util.*;
import java.util.concurrent.ConcurrentHashMap;

public class ApplicationContext implements BeanFactory {
    private final Map<String, Object> beanStore = new ConcurrentHashMap<>();
    private final ConfigClassReader reader;

    public ApplicationContext(Class<?>... configClasses) throws InvocationTargetException, NoSuchMethodException, InstantiationException, IllegalAccessException {
        this.reader = new ConfigClassReader(this);
        for (Class<?> configClass : configClasses) {
            reader.read(configClass);
        }
    }

    public void putBean(String name, Object bean) {
        beanStore.put(name, bean);
    }

    @Override
    public Object getBean(String name) {
        Object bean = beanStore.get(name);
        if (bean == null) {
            throw new NoSuchBeanDefinitionException();
        }
        return bean;
    }

    @Override
    public <T> T getBean(String name, Class<T> requiredType) {
        Object bean = getBean(name);
        if (!requiredType.isInstance(bean)) {
            throw new NoSuchBeanDefinitionException();
        }
        return (T) bean;
    }

    @Override
    public <T> T getBean(Class<T> requiredType) {
        Collection<Object> beans = beanStore.values();
        List<Object> beansOfType = beans.stream().filter(requiredType::isInstance).toList();
        if (beansOfType.isEmpty()) {
            throw new NoSuchBeanDefinitionException();
        } else if (beansOfType.size() > 1) {
            throw new NoUniqueBeanDefinitionException();
        }
        return (T) beansOfType.get(0);
    }

    @Override
    public <T> Map<String, T> getBeansOfType(Class<T> type) {
        Map<String, T> beansOfType = new HashMap<>();

        Set<String> names = beanStore.keySet();
        for (String name : names) {
            Object bean = beanStore.get(name);
            if (type.isInstance(bean)) {
                beansOfType.put(name, (T) bean);
            }
        }
        return beansOfType;
    }

    @Override
    public boolean containsBean(String name) {
        return beanStore.containsKey(name);
    }
}
