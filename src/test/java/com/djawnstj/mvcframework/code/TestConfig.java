package com.djawnstj.mvcframework.code;

import com.djawnstj.mvcframework.context.annotation.Bean;
import com.djawnstj.mvcframework.context.annotation.Configuration;

@Configuration
public class TestConfig {

    @Bean
    public LogUtils logUtils() {
        return new LogUtils();
    }
}
