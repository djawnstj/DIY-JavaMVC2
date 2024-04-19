package com.djawnstj.mvcframework.web.servlet.view;

import com.djawnstj.mvcframework.web.servlet.View;

import javax.servlet.RequestDispatcher;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.Map;

public class JspView implements View {

    private final String viewName;

    public JspView(final String viewName) {
        this.viewName = viewName;
    }

    @Override
    public void render(final Map<String, ?> model, final HttpServletRequest req, final HttpServletResponse resp) throws Exception {
        model.forEach(req::setAttribute);

        RequestDispatcher requestDispatcher = req.getRequestDispatcher(viewName);
        requestDispatcher.forward(req, resp);
    }

}
