package com.djawnstj.mvcframework.beans.factory;

public interface BeanFactory {
    Object getBean(final String name);
    <T> T getBean(final Class<T> requiredType);
}
