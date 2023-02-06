package com.partof204.partof204website.controller;

import com.partof204.partof204website.mapper.UserBeanMapper;
import com.partof204.partof204website.mapper.UserInfomationMapper;
import com.partof204.partof204website.service.UserService;
import jakarta.annotation.Resource;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.servlet.ModelAndView;

@Controller
public class UserList {
    @Resource
    UserService userService;
    @Resource
    UserInfomationMapper infomationMapper;
    @GetMapping("/user/list")
    public ModelAndView showList(){
        ModelAndView modelAndView = new ModelAndView("/user/list");
        modelAndView.addObject("users",userService.getAll());
        return modelAndView;
    }

    @GetMapping("/user/information")
    public ModelAndView info(int id){
        ModelAndView modelAndView = new ModelAndView("/user/infomation");
        modelAndView.addObject("user",userService.getUserById(id));
        modelAndView.addObject("describe", infomationMapper.selectByPrimaryKey(id).getDescribea());
        return modelAndView;
    }

    @GetMapping("/logout")
    public ModelAndView logout(HttpServletRequest request) {
        request.getSession().invalidate();
        ModelAndView modelAndView = new ModelAndView("/index");
        modelAndView.addObject("username","您尚未登录,请<a href='/login'>登录</a>");
        return modelAndView;
    }
}
