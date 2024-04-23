package com.djawnstj.mvcframework.web.servlet.config.annotation;

import com.djawnstj.mvcframework.context.annotation.Bean;
import com.djawnstj.mvcframework.context.annotation.Configuration;
import com.djawnstj.mvcframework.web.servlet.handler.BeanNameUrlHandlerMapping;
import com.djawnstj.mvcframework.web.servlet.mvc.SimpleControllerHandlerAdapter;
import com.djawnstj.mvcframework.web.servlet.mvc.method.annotation.RequestMappingHandlerAdapter;
import com.djawnstj.mvcframework.web.servlet.mvc.method.annotation.RequestMappingHandlerMapping;

@Configuration
public class WebMvcConfigurationSupport {

    @Bean
   	public BeanNameUrlHandlerMapping beanNameHandlerMapping() {
        final BeanNameUrlHandlerMapping mapping = new BeanNameUrlHandlerMapping();
        mapping.setOrder(2);

        return mapping;
    }

    @Bean
    public RequestMappingHandlerMapping requestMappingHandlerMapping() {
        final RequestMappingHandlerMapping mapping = new RequestMappingHandlerMapping();
        mapping.setOrder(1);

        return mapping;
    }

    @Bean
   	public SimpleControllerHandlerAdapter simpleControllerHandlerAdapter() {
   		return new SimpleControllerHandlerAdapter();
   	}

    @Bean
    public RequestMappingHandlerAdapter requestMappingHandlerAdapter() {
        return new RequestMappingHandlerAdapter();
    }
}
