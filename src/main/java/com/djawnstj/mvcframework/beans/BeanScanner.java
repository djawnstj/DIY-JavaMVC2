package com.djawnstj.mvcframework.beans;

import com.djawnstj.mvcframework.boot.ApplicationArguments;
import com.djawnstj.mvcframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.reflections.Reflections;

import java.lang.annotation.Annotation;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

public class BeanScanner {

    private final Reflections reflections;
    private final ApplicationArguments applicationArgs;

    public BeanScanner(final String basePackage, final ApplicationArguments applicationArgs) {
        reflections = new Reflections(basePackage);
        this.applicationArgs = applicationArgs;
    }

    public Set<Class<?>> scanClassesTypeAnnotatedWith(Class<? extends Annotation> annotation) {
        return reflections.getTypesAnnotatedWith(annotation)
                .stream()
                .filter(type -> (!type.isAnnotation() && !type.isInterface() && isValidBeanType(type)))
                .collect(Collectors.toSet());
    }

    private boolean isValidBeanType(final Class<?> type) {
        if (!type.isAnnotationPresent(ConditionalOnProperty.class)) {
            return true;
        }

        final ConditionalOnProperty annotation = type.getAnnotation(ConditionalOnProperty.class);

        final String key = annotation.name();
        final String value = annotation.havingValue();

        final List<String> argValues = applicationArgs.getOptionValues(key);

        return argValues.contains(value);
    }
}
