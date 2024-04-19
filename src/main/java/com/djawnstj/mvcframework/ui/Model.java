package com.djawnstj.mvcframework.ui;

import java.util.Map;

public interface Model {
    void addAttribute(final String attributeName, final Object attributeValue);
    Map<String, Object> asMap();
}
