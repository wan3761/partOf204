package com.partof204.partof204website.controller;

import com.partof204.partof204website.bean.UserBean;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.servlet.ModelAndView;

@Controller
public class Developing {

    @Value("${web.notice}")

    private String notice;
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
        String img = "/upload/"+((UserBean) request.getSession().getAttribute("user")).getImg();
        if (img.equals("/upload/null")) {
            img="default_face.jpg";
        }
        model.addAttribute("img",img);
        model.addAttribute("username",((UserBean)request.getSession().getAttribute("user")).getName());

        return "/index";
    }

    @GetMapping("/notice")
    public ModelAndView notice(){
        ModelAndView model = new ModelAndView("/showNotice");
        model.addObject("notice",notice);
        return model;
    }
}
