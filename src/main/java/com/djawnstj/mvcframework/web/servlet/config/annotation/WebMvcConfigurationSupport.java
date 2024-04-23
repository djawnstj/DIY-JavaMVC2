package com.djawnstj.mvcframework.web.servlet.config.annotation;

import com.djawnstj.mvcframework.context.annotation.Bean;
import com.djawnstj.mvcframework.context.annotation.Configuration;
import com.djawnstj.mvcframework.web.servlet.handler.BeanNameUrlHandlerMapping;
import com.djawnstj.mvcframework.web.servlet.mvc.SimpleControllerHandlerAdapter;

@Configuration
public class WebMvcConfigurationSupport {

    @Bean
   	public BeanNameUrlHandlerMapping beanNameHandlerMapping() {
        final BeanNameUrlHandlerMapping mapping = new BeanNameUrlHandlerMapping();
        mapping.setOrder(2);
        return mapping;
    }

    @Bean
   	public SimpleControllerHandlerAdapter simpleControllerHandlerAdapter() {
   		return new SimpleControllerHandlerAdapter();
   	}
}
