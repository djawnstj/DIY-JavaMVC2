package com.djawnstj.mvcframework.web.servlet.handler;

import com.djawnstj.mvcframework.context.support.ApplicationObjectSupport;
import com.djawnstj.mvcframework.core.Ordered;
import com.djawnstj.mvcframework.web.servlet.HandlerMapping;

import javax.servlet.http.HttpServletRequest;

public abstract class AbstractHandlerMapping extends ApplicationObjectSupport implements HandlerMapping, Ordered {
    private int order = 0;

    public void setOrder(int order) {
        this.order = order;
    }

    @Override
    public int getOrder() {
        return this.order;
    }

    protected abstract Object getHandlerInternal(HttpServletRequest req) throws Exception;

    public Object getHandler(HttpServletRequest req) throws Exception {
        Object handler = getHandlerInternal(req);

        if (handler == null) {
            return null;
        }

        return handler;
    }
}
