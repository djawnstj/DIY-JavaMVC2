package com.djawnstj.mvcframework.code;

import com.djawnstj.mvcframework.context.annotation.bind.RequestMethod;
import com.djawnstj.mvcframework.context.annotation.stereotype.Controller;
import com.djawnstj.mvcframework.context.annotation.bind.RequestMapping;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

@Controller("users")
public class UserController {

    private final UserService userService;

    public UserController(final UserService userService) {
        this.userService = userService;
    }

    @RequestMapping(method = RequestMethod.GET)
    public String usersView(final HttpServletRequest req, HttpServletResponse resp) {

        return "users.jsp";
    }

    @RequestMapping(value = "/sign-up", method = RequestMethod.GET)
    public String signUpView(final HttpServletRequest req, HttpServletResponse resp) {

        return "sign-up.jsp";
    }

    @RequestMapping(method = RequestMethod.POST)
    public String createUser(final HttpServletRequest req, HttpServletResponse resp) {

        return "sign-up.jsp";
    }

}
