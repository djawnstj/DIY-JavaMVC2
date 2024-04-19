package com.djawnstj.mvcframework.context;

import com.djawnstj.mvcframework.beans.BeanScanner;
import com.djawnstj.mvcframework.beans.factory.BeanFactory;
import com.djawnstj.mvcframework.context.annotation.factory.Autowired;
import com.djawnstj.mvcframework.context.annotation.Bean;
import com.djawnstj.mvcframework.context.annotation.stereotype.Component;
import com.djawnstj.mvcframework.context.annotation.Configuration;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.lang.reflect.*;
import java.util.*;

public class ApplicationContext implements BeanFactory {

    private static final Logger log = LoggerFactory.getLogger(ApplicationContext.class);

    private final BeanScanner scanner;
    private final Set<Class<?>> beanClasses = new HashSet<>();
    private final Map<Class<?>, Class<?>> beanMethodOwnerPair = new LinkedHashMap<>();
    private final Map<String, Object> beansMap = new LinkedHashMap<>();

    public ApplicationContext(final String componentScanPackage) {
        this.scanner = new BeanScanner(componentScanPackage);
    }

    public void initialize() {
        beanClasses.addAll(scanner.scanClassesTypeAnnotatedWith(Component.class));
        log.debug("scanned types = {}", List.of(beanClasses));

        extractBeanMethodInConfiguration(beanClasses);
        log.debug("added bean method return type in configuration = {}", beanMethodOwnerPair.keySet());

        createBeansByClass(beanClasses);
        log.debug("bean create done. created bean instances = {}", beansMap);
    }

    private void extractBeanMethodInConfiguration(final Set<Class<?>> preInstantiatedClasses) {
        preInstantiatedClasses.stream()
                .filter(clazz -> clazz.isAnnotationPresent(Configuration.class))
                .forEach(clazz ->
                        Arrays.stream(clazz.getDeclaredMethods())
                                .filter(method -> method.isAnnotationPresent(Bean.class))
                                .forEach(method -> beanMethodOwnerPair.put(method.getReturnType(), clazz)));
    }

    private void createBeansByClass(final Set<Class<?>> preInstantiatedClasses) {
        for (Class<?> clazz : preInstantiatedClasses) {
            if (isBeanInitialized(clazz.getName())) continue;

            createBeanInstance(clazz);
        }
    }

    private boolean isBeanInitialized(final String beanName) {
        return beansMap.containsKey(beanName);
    }

    private Object createBeanInstance(final Class<?> clazz) {
        final Constructor<?> constructor = findConstructor(clazz);
        final Method[] autowiredMethods = findAutowiredMethods(clazz);
        final Field[] autowiredFields = findAutowiredFields(clazz);

        try {
            final Object[] parameters = createParameters(constructor.getParameterTypes());

            constructor.setAccessible(true);

            final Object instance = constructor.newInstance(parameters);

            injectAutowiredMethod(autowiredMethods, instance);

            injectAutowiredFields(autowiredFields, instance);

            saveBean(clazz.getName(), instance);

            if (clazz.isAnnotationPresent(Configuration.class)) {
                createBeanInConfigurationAnnotatedClass(instance);
            }

            return instance;
        } catch (InvocationTargetException | InstantiationException | IllegalAccessException e) {
            throw new RuntimeException(e);
        } finally {
            constructor.setAccessible(false);
        }
    }

    private Constructor<?> findConstructor(final Class<?> clazz) {
        final Constructor<?>[] constructors = clazz.getDeclaredConstructors();

        if (constructors.length == 1) {
            return constructors[0];
        }

        return findAutowiredConstructor(constructors);
    }

    private Constructor<?> findAutowiredConstructor(final Constructor<?>[] constructors) {
        final Constructor<?>[] autowiredConstructors = Arrays.stream(constructors)
                .filter(constructor -> constructor.isAnnotationPresent(Autowired.class))
                .toArray(Constructor[]::new);

        if (autowiredConstructors.length != 1) {
            throw new RuntimeException("Autowire constructor not found");
        }

        return autowiredConstructors[0];
    }

    private Field[] findAutowiredFields(final Class<?> clazz) {
        return Arrays.stream(clazz.getDeclaredFields())
                .filter(field -> field.isAnnotationPresent(Autowired.class))
                .toArray(Field[]::new);
    }

    private Method[] findAutowiredMethods(final Class<?> clazz) {
        return Arrays.stream(clazz.getDeclaredMethods())
                .filter(method -> method.isAnnotationPresent(Autowired.class))
                .toArray(Method[]::new);
    }

    private Object[] createParameters(final Class<?>[] parameterTypes) {
        final List<Class<?>> parameterTypesCollection = List.of(parameterTypes);

        if (parameterTypesCollection.isEmpty()) {
            return new Object[]{};
        }

        if (!beanClasses.containsAll(parameterTypesCollection) && !beanMethodOwnerPair.keySet().containsAll(parameterTypesCollection)) {
            log.error("parameter is not bean. parameterTypes = {}", parameterTypesCollection);
            throw new RuntimeException("parameter is not bean");
        }

        return Arrays.stream(parameterTypes).map(parameterType -> {
            if (isBeanInitialized(parameterType.getName())) {
                return getBean(parameterType.getSimpleName());
            }

            if (beanClasses.contains(parameterType)) {
                return createBeanInstance(parameterType);
            }

            final Class<?> configurationClazz = beanMethodOwnerPair.get(parameterType);

            if (!isBeanInitialized(configurationClazz.getName())) {
                createBeanInstance(configurationClazz);
            }

            return getBean(parameterType);
        }).toArray();
    }

    private void injectAutowiredMethod(final Method[] methods, final Object instance) {
        Arrays.stream(methods).forEach(method -> {
            try {
                method.setAccessible(true);

                final Object[] parameters = createParameters(method.getParameterTypes());

                method.invoke(instance, parameters);
            } catch (InvocationTargetException | IllegalAccessException e) {
                throw new RuntimeException(e);
            } finally {
                method.setAccessible(false);
            }
        });
    }

    private void injectAutowiredFields(final Field[] fields, final Object instance) throws IllegalAccessException {
        Arrays.stream(fields).forEach(field -> {
            try {
                field.setAccessible(true);

                if (!isBeanInitialized(field.getType().getSimpleName())) createBeanInstance(field.getType());

                field.set(instance, getBean(field.getType()));
            } catch (IllegalAccessException e) {
                throw new RuntimeException(e);
            } finally {
                field.setAccessible(false);
            }
        });
    }

    private void saveBean(final String beanName, final Object bean) {
        this.beansMap.put(beanName, bean);
    }

    private void createBeanInConfigurationAnnotatedClass(final Object configuration) {
        final Class<?> subclass = configuration.getClass();
        final Method[] methods = getBeanAnnotatedMethods(subclass);

        Arrays.stream(methods).forEach(method -> {
            try {
                method.setAccessible(true);
                final Object[] parameters = createParameters(method.getParameterTypes());
                final Object instance = method.invoke(configuration, parameters);
                final Bean annotation = method.getAnnotation(Bean.class);
                final String beanName = (annotation.name().isBlank()) ? method.getReturnType().getName() : annotation.name();
                saveBean(beanName, instance);
            } catch (InvocationTargetException | IllegalAccessException e) {
                throw new RuntimeException(e);
            }
        });
    }

    private Method[] getBeanAnnotatedMethods(final Class<?> clazz) {
        return Arrays.stream(clazz.getDeclaredMethods())
                .filter(method -> method.isAnnotationPresent(Bean.class))
                .toArray(Method[]::new);
    }

    @Override
    public Object getBean(final String name) {
        return this.beansMap.get(name);
    }

    @Override
    public <T> T getBean(final Class<T> requiredType) {
        return (T) this.beansMap.get(requiredType.getName());
    }
}
