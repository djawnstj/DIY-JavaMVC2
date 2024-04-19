package com.djawnstj.mvcframework.ui;

import java.util.Map;

public class ExtendedModelMap extends ModelMap implements Model {

    @Override
    public void addAttribute(final String attributeName, final Object attributeValue) {
        super.addAttribute(attributeName, attributeValue);
    }

    @Override
    public void addAllAttribute(final Map<String, Object> model) {
        super.addAllAttribute(model);
    }

    @Override
    public Map<String, Object> asMap() {
        return this.getModelMap();
    }

}
