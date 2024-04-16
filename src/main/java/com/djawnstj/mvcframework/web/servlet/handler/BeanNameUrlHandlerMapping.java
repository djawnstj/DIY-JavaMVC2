package com.djawnstj.mvcframework.web.servlet.handler;

import com.djawnstj.mvcframework.web.servlet.HandlerMapping;

import javax.servlet.http.HttpServletRequest;
import java.util.LinkedHashMap;
import java.util.Map;

public class BeanNameUrlHandlerMapping implements HandlerMapping {

    private final Map<String, Object> handlerMap = new LinkedHashMap<>();

    public BeanNameUrlHandlerMapping(final Map<String, Object> urlMap) {
        urlMap.forEach(this::registerHandler);
    }

    protected void registerHandler(final String urlPath, final Object handler) {
        final Object mappedHandler = handlerMap.get("urlPath");
        if (mappedHandler != null && mappedHandler != handler) {
            throw new IllegalStateException(
                    "Cannot map " + handler + " to URL path [" + urlPath +
                    "]: There is already " + handler + " mapped.");
        } else {
            handlerMap.put(urlPath, handler);
        }
    }

    @Override
    public Object getHandler(final HttpServletRequest req) throws Exception {
        final String urlPath = req.getRequestURI();
        return handlerMap.get(urlPath);
    }
}
