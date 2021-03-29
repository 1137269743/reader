package com.reader.controller.management;

import com.reader.entity.Member;
import com.reader.entity.User;
import com.reader.service.UserService;
import com.reader.service.exception.BusinessException;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.annotation.Resource;
import javax.servlet.http.HttpSession;
import java.util.HashMap;
import java.util.Map;

/**
 * @Author Flagship
 * @Date 2021/3/24 7:58
 * @Description 管理员用户控制器
 */
@Controller
public class ManageUserController {
    @Resource
    private UserService userService;

    @PostMapping("/management/check_login")
    @ResponseBody
    public Map checkLogin(String username, String password, String vc, HttpSession session) {
        String verifyCode = (String) session.getAttribute("kaptchaVerifyCode");
        Map result = new HashMap();

        try {
            User user = userService.checkLogin(username, password);
            session.setAttribute("loginUser", user);
            result.put("code", "0");
            result.put("msg", "success");
        } catch (BusinessException e) {
            e.printStackTrace();
            result.put("code", e.getCode());
            result.put("msg", e.getMsg());
        }
        return result;
    }
}
