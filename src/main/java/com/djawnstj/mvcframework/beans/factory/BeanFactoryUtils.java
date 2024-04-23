package com.djawnstj.mvcframework.beans.factory;

import java.util.Map;

public abstract class BeanFactoryUtils {
    public static <T> Map<String, T> beansOfTypeIncludingAncestors(final BeanFactory beanFactory, Class<T> type) {
        return beanFactory.getBeansOfType(type);
    }
}
