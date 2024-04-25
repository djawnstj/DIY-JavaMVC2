package com.djawnstj.mvcframework.web.servlet.view;

import com.djawnstj.mvcframework.core.Ordered;
import com.djawnstj.mvcframework.web.servlet.View;
import com.djawnstj.mvcframework.web.servlet.ViewResolver;

public class JspViewResolver implements ViewResolver, Ordered {

    @Override
    public View resolveViewName(final String viewName) {
        return new JspView("/" + viewName + ".jsp");
    }

    @Override
    public int getOrder() {
        return 1;
    }
}
