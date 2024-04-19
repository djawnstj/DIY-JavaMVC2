package com.djawnstj.mvcframework.use;

import com.djawnstj.mvcframework.web.servlet.ModelAndView;
import com.djawnstj.mvcframework.web.servlet.mvc.Controller;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public class SignUpController implements Controller {
    @Override
    public ModelAndView handleRequest(final HttpServletRequest req, final HttpServletResponse resp) throws Exception {

        if (req.getMethod().equalsIgnoreCase("POST")) {
            final String id = req.getParameter("id");
            final String pw = req.getParameter("pw");

            UserRepository.save(id, pw);
        }

        return new ModelAndView("sign-up");
    }
}
