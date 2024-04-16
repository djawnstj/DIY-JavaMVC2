package com.djawnstj.mvcframework.web.servlet.mvc;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

@FunctionalInterface
public interface Controller {

    String handleRequest(final HttpServletRequest req, final HttpServletResponse resp) throws Exception;

}
