package com.partof204.partof204website.controller;

import com.partof204.partof204website.bean.UserBean;
import com.partof204.partof204website.bean.UserBeanExample;
import com.partof204.partof204website.service.UserService;
import jakarta.annotation.Resource;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

@Controller
public class UserLogin {

    @Resource
    UserService userService;

    @GetMapping("/login")
    public String login(String username){
        return "/login";
    }

    @PostMapping("/login")
    @ResponseBody
    public String login(String username, String password, HttpServletRequest request){
        if (username == null  || password == null){
            return "no";
        }
        UserBean userBean = userService.getUserByUserExample(username,password);
        if (userBean != null) {
            request.getSession().setAttribute("user",userBean);
            return "ok";
        }else{
            return "no";
        }
    }
}
