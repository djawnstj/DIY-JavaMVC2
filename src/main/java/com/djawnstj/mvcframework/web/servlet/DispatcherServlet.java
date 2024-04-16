package com.djawnstj.mvcframework.web.servlet;

import com.djawnstj.mvcframework.use.SignUpController;
import com.djawnstj.mvcframework.use.UsersController;
import com.djawnstj.mvcframework.web.servlet.handler.BeanNameUrlHandlerMapping;
import com.djawnstj.mvcframework.web.servlet.mvc.Controller;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletConfig;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.Map;

public class DispatcherServlet extends HttpServlet {

    private final static Logger log = LoggerFactory.getLogger(DispatcherServlet.class);

    private final HandlerMapping handlerMapping = new BeanNameUrlHandlerMapping(
            Map.of(
                    "/sign-up", new SignUpController(),
                    "/users", new UsersController()
            )
    );

    @Override
    public void init(final ServletConfig config) throws ServletException {
        log.info("DispatcherServlet init called.");
        super.init(config);
    }

    @Override
    protected void service(final HttpServletRequest req, final HttpServletResponse resp) throws ServletException, IOException {
        log.debug("DispatcherServlet service called. method={}, url={}", req.getMethod(), req.getRequestURI());

        doDispatch(req, resp);
    }

    private void doDispatch(final HttpServletRequest req, final HttpServletResponse resp) {
        try {
            final Object handler = handlerMapping.getHandler(req);
            final String viewName = ((Controller) handler).handleRequest(req, resp);

            final RequestDispatcher requestDispatcher = req.getRequestDispatcher(viewName);
            requestDispatcher.forward(req, resp);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

}
