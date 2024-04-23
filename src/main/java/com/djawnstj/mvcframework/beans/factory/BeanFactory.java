package com.djawnstj.mvcframework.beans.factory;

import java.util.Map;

public interface BeanFactory {
    Object getBean(final String name);
    <T> T getBean(final Class<T> requiredType);
    <T> Map<String, T> getBeansOfType(final Class<T> type);
    <T> String[] getBeanNamesForType(final Class<T> type);
}
