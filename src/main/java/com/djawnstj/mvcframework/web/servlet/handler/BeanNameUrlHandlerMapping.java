package com.djawnstj.mvcframework.web.servlet.handler;

import com.djawnstj.mvcframework.beans.factory.BeanFactoryUtils;
import com.djawnstj.mvcframework.context.ApplicationContext;
import com.djawnstj.mvcframework.context.support.ApplicationObjectSupport;
import com.djawnstj.mvcframework.web.servlet.HandlerMapping;
import com.djawnstj.mvcframework.web.servlet.mvc.Controller;

import javax.servlet.http.HttpServletRequest;
import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

public class BeanNameUrlHandlerMapping extends ApplicationObjectSupport implements HandlerMapping {

    private final Map<String, Object> handlerMap = new LinkedHashMap<>();

    private int order = 0;

    public void setOrder(int order) {
   		this.order = order;
   	}

    @Override
    protected void initApplicationContext(final ApplicationContext context) {
        final Map<String, Controller> matchingBeans =
                BeanFactoryUtils.beansOfTypeIncludingAncestors(context, Controller.class);

        matchingBeans.forEach(this::registerHandler);
    }

    private void registerHandler(final String urlPath, final Object handler) {
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
