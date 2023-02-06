package com.partof204.partof204website.controller;

import com.partof204.partof204website.bean.UserBean;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class Developing {
    @GetMapping("/")
    public String root(HttpServletRequest request){
        return "redirect:/index";
    }

    @GetMapping("/index")
    public String index(Model model,HttpServletRequest request){
        if(((UserBean)request.getSession().getAttribute("user")) ==null){
            model.addAttribute("username","您尚未登录,请<a href='/login'>登录</a>");
            model.addAttribute("img","default_face.jpg");
            return "/index";
        }
        model.addAttribute("img","/upload/"+((UserBean) request.getSession().getAttribute("user")).getImg());
        model.addAttribute("username",((UserBean)request.getSession().getAttribute("user")).getName());

        return "/index";
    }
}
