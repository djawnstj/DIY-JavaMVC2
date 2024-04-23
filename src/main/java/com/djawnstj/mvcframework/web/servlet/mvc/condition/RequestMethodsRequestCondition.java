package com.djawnstj.mvcframework.web.servlet.mvc.condition;

import com.djawnstj.mvcframework.context.annotation.bind.RequestMethod;

import java.util.Arrays;
import java.util.Collections;
import java.util.LinkedHashSet;
import java.util.Set;

public final class RequestMethodsRequestCondition {
    private final Set<RequestMethod> methods;

    public RequestMethodsRequestCondition(RequestMethod... requestMethods) {
        this.methods = ((requestMethods == null || requestMethods.length == 0) ?
      				Collections.emptySet() : new LinkedHashSet<>(Arrays.asList(requestMethods)));
    }

    public Set<RequestMethod> getMethods() {
        return this.methods;
    }
}
