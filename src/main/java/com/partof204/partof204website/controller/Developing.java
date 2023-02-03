package com.partof204.partof204website.controller;

import com.partof204.partof204website.bean.UserBean;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

@Controller
public class Developing {
    @GetMapping("/")
    public String root(HttpServletRequest request){

        return "redirect:/index";
    }

    @GetMapping("/index")
    public String index(Model model,HttpServletRequest request){
        model.addAttribute("username",((UserBean)request.getSession().getAttribute("user")).getName());
        return "/index";
    }
}
