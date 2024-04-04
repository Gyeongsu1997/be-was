package myframework.beans;

import java.util.Map;

public interface BeanFactory {
    Object getBean(String name);

    <T> T getBean(String name, Class<T> requiredType);

    <T> T getBean(Class<T> requiredType);

    <T> Map<String, T> getBeansOfType(Class<T> type);

    boolean containsBean(String name);
}

