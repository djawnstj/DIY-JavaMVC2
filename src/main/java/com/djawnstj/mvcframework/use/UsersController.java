package com.djawnstj.mvcframework.use;

import com.djawnstj.mvcframework.web.servlet.ModelAndView;
import com.djawnstj.mvcframework.web.servlet.mvc.Controller;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.Collection;
import java.util.List;
import java.util.Map;

public class UsersController implements Controller {
    @Override
    public ModelAndView handleRequest(final HttpServletRequest req, final HttpServletResponse resp) throws Exception {
        final Collection<User> users = UserRepository.findAll();

        return new ModelAndView("users", Map.of("users", users));
    }
}
