package com.djawnstj.mvcframework.ui;

import java.util.LinkedHashMap;
import java.util.Map;

public class ModelMap {

    private final Map<String, Object> modelMap = new LinkedHashMap<>();

    public ModelMap() {
    }

    public ModelMap(final Map<String, Object> model) {
        this.modelMap.putAll(model);
    }

    public ModelMap(final String attributeName, final Object attributeValue) {
        this.addAttribute(attributeName, attributeValue);
    }

    public void addAttribute(final String attributeName, final Object attributeValue) {
        this.modelMap.put(attributeName, attributeValue);
    }

    public void addAllAttribute(final Map<String, Object> model) {
        this.modelMap.putAll(model);
    }

    public Map<String, Object> getModelMap() {
        return this.modelMap;
    }

}
