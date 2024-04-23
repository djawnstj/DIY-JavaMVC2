package com.djawnstj.mvcframework.context.support;

import com.djawnstj.mvcframework.context.ApplicationContext;

public abstract class ApplicationObjectSupport {

    protected abstract void initApplicationContext(final ApplicationContext context);

    public final void setApplicationContext(final ApplicationContext context) {
        this.initApplicationContext(context);
    }
}
