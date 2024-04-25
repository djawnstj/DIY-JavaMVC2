package com.djawnstj.mvcframework.web.servlet.view;

import com.djawnstj.mvcframework.core.Ordered;
import com.djawnstj.mvcframework.web.servlet.View;
import com.djawnstj.mvcframework.web.servlet.ViewResolver;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class HtmlViewResolver implements ViewResolver, Ordered {

    private static final Logger log = LoggerFactory.getLogger(HtmlViewResolver.class);

    private int order = -1;

    @Override
    public View resolveViewName(final String viewName) {
        final String resolvedViewName = "/templates/" + viewName + ".html";
        log.debug("resolved view={}", resolvedViewName);
        return new HtmlView(resolvedViewName);
    }

    public void setOrder(final int order) {
        this.order = order;
    }

    @Override
    public int getOrder() {
        return this.order;
    }
}
