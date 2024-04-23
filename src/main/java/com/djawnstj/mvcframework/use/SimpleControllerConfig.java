package com.djawnstj.mvcframework.use;

import com.djawnstj.mvcframework.context.annotation.Bean;
import com.djawnstj.mvcframework.context.annotation.Configuration;

@Configuration
public class SimpleControllerConfig {

    @Bean(name = "/sign-up")
    public SignUpController signUpController() {
        return new SignUpController();
    }

    @Bean(name = "/users")
    public UsersController usersController() {
        return new UsersController();
    }
}
