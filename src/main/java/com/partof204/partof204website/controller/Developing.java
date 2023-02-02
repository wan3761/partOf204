package com.partof204.partof204website.controller;

import jakarta.servlet.http.HttpServletRequest;
import org.springframework.stereotype.Controller;
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
    @ResponseBody
    public String index(){
        return "developing";
    }
}
