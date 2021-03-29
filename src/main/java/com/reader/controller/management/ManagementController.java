package com.reader.controller.management;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpSession;

/**
 * @Author Flagship
 * @Date 2021/3/22 16:37
 * @Description 管理首页控制器
 */
@Controller
@RequestMapping("/management")
public class ManagementController {
    @GetMapping("/index.html")
    public ModelAndView showIndex(HttpSession session) {
        if (session.getAttribute("loginUser") == null) {
            return new ModelAndView("/management/login");
        }
        return new ModelAndView("/management/index");
    }

    @GetMapping("/login.html")
    public ModelAndView showLogin(HttpSession session) {
        return new ModelAndView("/management/login");
    }

    @GetMapping("/logout")
    public ModelAndView logout(HttpSession session) {
        session.removeAttribute("loginUser");
        return new ModelAndView("/management/login");
    }
}
