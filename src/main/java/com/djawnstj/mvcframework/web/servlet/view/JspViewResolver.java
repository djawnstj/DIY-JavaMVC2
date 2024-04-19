package com.djawnstj.mvcframework.web.servlet.view;

import com.djawnstj.mvcframework.web.servlet.View;
import com.djawnstj.mvcframework.web.servlet.ViewResolver;

public class JspViewResolver implements ViewResolver {
    @Override
    public View resolveViewName(final String viewName) {
        return new JspView("/" + viewName + ".jsp");
    }
}
