package com.djawnstj.mvcframework.web.servlet.mvc.method.annotation;

import com.djawnstj.mvcframework.web.method.HandlerMethod;
import com.djawnstj.mvcframework.web.method.support.ModelAndViewContainer;
import com.djawnstj.mvcframework.web.servlet.HandlerAdapter;
import com.djawnstj.mvcframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public class RequestMappingHandlerAdapter implements HandlerAdapter {

    @Override
    public boolean supports(final Object handler) {
        return (handler instanceof HandlerMethod);
    }

    @Override
    public ModelAndView handle(final HttpServletRequest req, final HttpServletResponse res, final Object handler) throws Exception {

        final ModelAndViewContainer mavContainer = new ModelAndViewContainer();

        ((HandlerMethod) handler).handle(req, res, mavContainer);
        Object view = mavContainer.getView();
        if (!(view instanceof String)) throw new IllegalArgumentException("Unknown return value type: " + view.getClass().getSimpleName());

        return getModelAndView(mavContainer);
    }

    private ModelAndView getModelAndView(final ModelAndViewContainer mavContainer) {
        return new ModelAndView(mavContainer.getViewName(), mavContainer.getModelMap());
    }

}
