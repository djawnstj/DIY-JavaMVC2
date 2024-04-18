package com.djawnstj.mvcframework.beans;

import com.djawnstj.mvcframework.context.annotation.Component;
import com.djawnstj.mvcframework.web.servlet.HandlerMapping;
import org.reflections.Reflections;

import java.lang.annotation.Annotation;
import java.util.Set;
import java.util.stream.Collectors;

public class BeanScanner {

    private final Reflections reflections;

    public BeanScanner(final String basePackage) {
        reflections = new Reflections(basePackage);
    }

    public <T> Set<Class<? extends T>> scanClassesSubTypesOf(Class<T> clazz) {
        return reflections.getSubTypesOf(clazz);
    }

    public Set<Class<?>> scanClassesTypeAnnotatedWith(Class<? extends Annotation> annotation) {
        return reflections.getTypesAnnotatedWith(annotation)
                .stream()
                .filter(type -> (!type.isAnnotation() && !type.isInterface()))
                .collect(Collectors.toSet());
    }
}
