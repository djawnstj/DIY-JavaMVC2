package com.djawnstj.mvcframework.web.method.support;

import com.djawnstj.mvcframework.http.HttpStatus;
import com.djawnstj.mvcframework.ui.ExtendedModelMap;
import com.djawnstj.mvcframework.ui.ModelMap;

import java.util.Map;

public class ModelAndViewContainer {

    private Object view;

    private ModelMap model = new ExtendedModelMap();

    private HttpStatus status;

    public void setViewName(final String viewName) {
        this.view = viewName;
    }

    public String getViewName() {
   		return (this.view instanceof String ? (String) this.view : null);
   	}

    public Object getView() {
        return this.view;
    }

    public void setView(final Object view) {
        this.view = view;
    }

    public void addAttribute(final String attributeName, final Object attributeValue) {
        this.model.addAttribute(attributeName, attributeValue);
    }

    public void addAllAttribute(final Map<String, Object> model) {
        this.model.addAllAttribute(model);
    }

    public ModelMap getModel() {
        return model;
    }

    public Map<String, Object> getModelMap() {
        return model.getModelMap();
    }

    public HttpStatus getStatus() {
        return status;
    }

    public void setStatus(final HttpStatus status) {
        this.status = status;
    }
}
