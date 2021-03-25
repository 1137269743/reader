package com.reader.controller.management;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

/**
 * @Author Flagship
 * @Date 2021/3/22 16:37
 * @Description 管理首页控制器
 */
@Controller
@RequestMapping("/management")
public class ManagementController {
    @GetMapping("/index.html")
    public ModelAndView showIndex() {
        return new ModelAndView("/management/index");
    }
}
