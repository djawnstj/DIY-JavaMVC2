package com.djawnstj.mvcframework.web.servlet;

public interface ViewResolver {
    View resolveViewName(final String viewName);
}
