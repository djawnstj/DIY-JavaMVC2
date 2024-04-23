package com.djawnstj.mvcframework.web.servlet.mvc.method;

import com.djawnstj.mvcframework.context.annotation.bind.RequestMethod;
import com.djawnstj.mvcframework.web.servlet.mvc.condition.RequestMethodsRequestCondition;

import java.util.Set;

public final class RequestMappingInfo {
    private final String name;
    private final RequestMethodsRequestCondition methodsCondition;

    public RequestMappingInfo(final String name, final RequestMethodsRequestCondition methodsCondition) {
        this.name = name;
        this.methodsCondition = methodsCondition;
    }

    public boolean isMatch(final String url, final RequestMethod method) {
        return (url.equals(this.name)) && isMethodMatch(method);
    }

    private boolean isMethodMatch(final RequestMethod method) {
        final Set<RequestMethod> methods = this.methodsCondition.getMethods();

        if (methods.isEmpty()) {
            return true;
        }

        return methods.contains(method);
    }

}
