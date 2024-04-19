package com.djawnstj.mvcframework.web.servlet;

import com.djawnstj.mvcframework.use.SignUpController;
import com.djawnstj.mvcframework.use.UsersController;
import com.djawnstj.mvcframework.web.servlet.handler.BeanNameUrlHandlerMapping;
import com.djawnstj.mvcframework.web.servlet.mvc.SimpleControllerHandlerAdapter;
import com.djawnstj.mvcframework.web.servlet.view.JspViewResolver;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

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

    private final HandlerAdapter handlerAdapter = new SimpleControllerHandlerAdapter();

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

            final boolean supports = handlerAdapter.supports(handler);

            if (supports) {
                final ModelAndView mv = handlerAdapter.handle(req, resp, handler);
                render(mv, req, resp);
            }
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    private void render(final ModelAndView mv, final HttpServletRequest req, final HttpServletResponse resp) throws Exception {
        final String viewName = mv.getViewName();

        final View view = resolveViewName(viewName);

        view.render(mv.getModelInternal(), req, resp);
    }

    private View resolveViewName(final String viewName) {
        final ViewResolver viewResolver = new JspViewResolver();

        final View view = viewResolver.resolveViewName(viewName);

        return view;
    }

}
