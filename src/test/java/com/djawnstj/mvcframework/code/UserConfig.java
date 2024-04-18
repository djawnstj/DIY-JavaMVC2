package com.djawnstj.mvcframework.code;

import com.djawnstj.mvcframework.context.annotation.Bean;
import com.djawnstj.mvcframework.context.annotation.Configuration;

@Configuration
public class UserConfig {

    @Bean
    public UserTestCode userTestCode(final LogUtils logUtils) {
        return new UserTestCode(logUtils);
    }
}
