package com.djawnstj.mvcframework.web.servlet.mvc.method.annotation;

import com.djawnstj.mvcframework.beans.factory.BeanFactoryUtils;
import com.djawnstj.mvcframework.context.ApplicationContext;
import com.djawnstj.mvcframework.context.annotation.bind.RequestMapping;
import com.djawnstj.mvcframework.context.annotation.bind.RequestMethod;
import com.djawnstj.mvcframework.context.annotation.stereotype.Controller;
import com.djawnstj.mvcframework.web.method.HandlerMethod;
import com.djawnstj.mvcframework.web.servlet.handler.AbstractHandlerMapping;
import com.djawnstj.mvcframework.web.servlet.mvc.condition.RequestMethodsRequestCondition;
import com.djawnstj.mvcframework.web.servlet.mvc.method.RequestMappingInfo;

import javax.servlet.http.HttpServletRequest;
import java.lang.reflect.Method;
import java.util.*;

public class RequestMappingHandlerMapping extends AbstractHandlerMapping {

    private final MappingRegistry mappingRegistry = new MappingRegistry();

    @Override
    protected void initApplicationContext(final ApplicationContext context) {
        final Map<String, Object> matchingBeans = BeanFactoryUtils.beansOfAnnotated(context, Controller.class);

        matchingBeans.forEach((key, handler) -> detectHandlerMethods(handler));
    }

    private void detectHandlerMethods(final Object handler) {
        Class<?> clazz = handler.getClass();
        Method[] handlerMethods = clazz.getDeclaredMethods();

        StringBuilder parentPath = new StringBuilder();
        Set<RequestMethod> parentRequestMethods = new HashSet<>();

        boolean classHasRequestMapping = clazz.isAnnotationPresent(RequestMapping.class);

        if (classHasRequestMapping) {
            RequestMapping requestMapping = clazz.getDeclaredAnnotation(RequestMapping.class);

            setRequestMappingInfo(requestMapping, parentPath, parentRequestMethods);
        }

        final String basePath = parentPath.toString();

        final Map<Method, RequestMappingInfo> methods = extractMethodMappings(handlerMethods, basePath, parentRequestMethods);

        methods.forEach((method, mapping) -> registerHandlerMethod(handler, method, mapping));
   	}

    private Map<Method, RequestMappingInfo> extractMethodMappings(final Method[] handlerMethods, final String basePath, final Set<RequestMethod> baseMethods) {
        final Map<Method, RequestMappingInfo> methods = new LinkedHashMap<>();

        Arrays.stream(handlerMethods).forEach(method -> {
            StringBuilder path = new StringBuilder(basePath);
            Set<RequestMethod> requestMethods = new HashSet<>(baseMethods);

            boolean methodHasRequestMapping = method.isAnnotationPresent(RequestMapping.class);

            if (methodHasRequestMapping) {
                RequestMapping requestMapping = method.getDeclaredAnnotation(RequestMapping.class);

                setRequestMappingInfo(requestMapping, path, requestMethods);
            }

            final RequestMethodsRequestCondition methodsCondition = new RequestMethodsRequestCondition(requestMethods.toArray(RequestMethod[]::new));
            final RequestMappingInfo mapping = new RequestMappingInfo(path.toString(), methodsCondition);

            methods.put(method, mapping);
        });

        return methods;
    }

    private void setRequestMappingInfo(final RequestMapping requestMapping, final StringBuilder path, final Set<RequestMethod> httpMethods) {
        String tempPath = requestMapping.value();

        if (!tempPath.startsWith("/")) path.append("/");
        path.append(tempPath);

        httpMethods.addAll(List.of(requestMapping.method()));
    }

    private void registerHandlerMethod(final Object handler, final Method method, final RequestMappingInfo mapping) {
        this.mappingRegistry.register(mapping, handler, method);
    }

    @Override
    protected Object getHandlerInternal(final HttpServletRequest req) throws Exception {

        return this.mappingRegistry.getMethod(req.getRequestURI(), RequestMethod.valueOf(req.getMethod()));
    }

    class MappingRegistry {
        private final Map<RequestMappingInfo, HandlerMethod> registry = new HashMap<>();

        public void register(final RequestMappingInfo mapping, final Object handler, final Method method) {
            registry.put(mapping, new HandlerMethod(handler, method));
        }

        public HandlerMethod getMethod(final String url, final RequestMethod requestMethod) {
            for (RequestMappingInfo mapping : registry.keySet()) {
                final boolean match = mapping.isMatch(url, requestMethod);

                if (match) {
                    return this.registry.get(mapping);
                }
            }

            return null;
        }

    }
}
