package com.djawnstj.mvcframework.web.servlet;

import javax.servlet.http.HttpServletRequest;

public interface HandlerMapping {
    Object getHandler(final HttpServletRequest req) throws Exception;
}
