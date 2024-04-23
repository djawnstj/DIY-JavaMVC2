package com.djawnstj.mvcframework.web.servlet;

import com.djawnstj.mvcframework.beans.factory.BeanFactoryUtils;
import com.djawnstj.mvcframework.context.ApplicationContext;
import com.djawnstj.mvcframework.web.context.support.WebApplicationContextUtils;
import com.djawnstj.mvcframework.web.servlet.handler.AbstractHandlerMapping;
import com.djawnstj.mvcframework.web.servlet.view.JspViewResolver;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.Map;

public class DispatcherServlet extends HttpServlet {

    private final static Logger log = LoggerFactory.getLogger(DispatcherServlet.class);

    private List<HandlerMapping> handlerMappings;

    private List<HandlerAdapter> handlerAdapters;

    @Override
    public void init() throws ServletException {
        log.info("DispatcherServlet init called.");

        initStrategies(initWebApplicationContext());
        super.init();
    }

    private ApplicationContext initWebApplicationContext() {
        return WebApplicationContextUtils.getWebApplicationContext(getServletContext(), ApplicationContext.APPLICATION_CONTEXT_ATTRIBUTE);
    }

    private void initStrategies(final ApplicationContext context) {
        initHandlerMappings(context);
        initHandlerAdapters(context);
    }

    private void initHandlerMappings(final ApplicationContext context) {
        final Map<String, HandlerMapping> matchingBeans =
                BeanFactoryUtils.beansOfTypeIncludingAncestors(context, HandlerMapping.class);
        this.handlerMappings = new ArrayList<>(matchingBeans.values());

        this.handlerMappings.sort(Comparator.comparingInt(o -> ((AbstractHandlerMapping) o).getOrder()));
    }

    private void initHandlerAdapters(final ApplicationContext context) {
        final Map<String, HandlerAdapter> matchingBeans =
                BeanFactoryUtils.beansOfTypeIncludingAncestors(context, HandlerAdapter.class);
        this.handlerAdapters = new ArrayList<>(matchingBeans.values());
    }

    @Override
    protected void service(final HttpServletRequest req, final HttpServletResponse resp) throws ServletException, IOException {
        log.debug("DispatcherServlet service called. method={}, url={}", req.getMethod(), req.getRequestURI());

        doDispatch(req, resp);
    }

    private void doDispatch(final HttpServletRequest req, final HttpServletResponse resp) {
        try {
            final Object handler = getHandler(req);

            final HandlerAdapter ha = getHandlerAdapter(handler);

            final ModelAndView mv = ha.handle(req, resp, handler);

            render(mv, req, resp);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    protected Object getHandler(final HttpServletRequest req) throws Exception {
   		if (this.handlerMappings != null) {
   			for (final HandlerMapping mapping : this.handlerMappings) {
   				final Object handler = mapping.getHandler(req);
   				if (handler != null) {
   					return handler;
   				}
   			}
   		}
   		return null;
   	}

    protected HandlerAdapter getHandlerAdapter(final Object handler) throws ServletException {
   		if (this.handlerAdapters != null) {
   			for (final HandlerAdapter adapter : this.handlerAdapters) {
   				if (adapter.supports(handler)) {
   					return adapter;
   				}
   			}
   		}

   		throw new ServletException("No adapter for handler [" + handler +
   				"]: The DispatcherServlet configuration needs to include a HandlerAdapter that supports this handler");
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
