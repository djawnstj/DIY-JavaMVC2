package com.djawnstj.mvcframework.web.servlet;

import com.djawnstj.mvcframework.web.servlet.mvc.Controller;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public class SimpleControllerHandlerAdapter implements HandlerAdapter {
    @Override
    public boolean supports(final Object handler) {
        return (handler instanceof Controller);
    }

    @Override
    public ModelAndView handle(final HttpServletRequest req, final HttpServletResponse resp, final Object handler) throws Exception {
        return ((Controller) handler).handleRequest(req, resp);
    }
}
