package com.djawnstj.mvcframework.web.servlet.mvc;

import com.djawnstj.mvcframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

@FunctionalInterface
public interface Controller {

    ModelAndView handleRequest(final HttpServletRequest req, final HttpServletResponse resp) throws Exception;

}
