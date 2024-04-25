package com.djawnstj.mvcframework.boot.autoconfigure.html;

import com.djawnstj.mvcframework.boot.autoconfigure.condition.ConditionalOnProperty;
import com.djawnstj.mvcframework.context.annotation.Bean;
import com.djawnstj.mvcframework.context.annotation.Configuration;
import com.djawnstj.mvcframework.web.servlet.view.HtmlViewResolver;

@Configuration
@ConditionalOnProperty(name = "view-template", havingValue = "html")
public class HtmlAutoConfiguration {

    @Bean
    public HtmlViewResolver htmlViewResolver() {
        final HtmlViewResolver viewResolver = new HtmlViewResolver();
        viewResolver.setOrder(0);
        return viewResolver;
    }
}
