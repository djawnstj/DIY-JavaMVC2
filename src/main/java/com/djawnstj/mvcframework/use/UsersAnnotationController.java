package com.djawnstj.mvcframework.use;

import com.djawnstj.mvcframework.context.annotation.bind.RequestMapping;
import com.djawnstj.mvcframework.context.annotation.stereotype.Controller;

@Controller
@RequestMapping("/anno")
public class UsersAnnotationController {

    @RequestMapping("/users")
    public String annotationUsers() {
        return "users";
    }
}
