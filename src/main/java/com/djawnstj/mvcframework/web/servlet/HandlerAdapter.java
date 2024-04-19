package com.djawnstj.mvcframework.web.servlet;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public interface HandlerAdapter {
    boolean supports(final Object handler);
    ModelAndView handle(final HttpServletRequest req, final HttpServletResponse resp, final Object handler) throws Exception;
}
