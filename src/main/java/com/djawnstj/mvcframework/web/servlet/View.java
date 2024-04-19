package com.djawnstj.mvcframework.web.servlet;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.Map;

public interface View {
    void render(final Map<String, ?> model, final HttpServletRequest req, final HttpServletResponse resp) throws Exception;
}
