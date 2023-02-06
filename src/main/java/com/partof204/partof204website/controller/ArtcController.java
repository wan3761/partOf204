package com.partof204.partof204website.controller;

import com.partof204.partof204website.bean.ArtcBean;
import com.partof204.partof204website.bean.UserBean;
import com.partof204.partof204website.service.ArtcService;
import com.partof204.partof204website.service.EventService;
import jakarta.annotation.Resource;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.ResponseBody;

@Controller
public class ArtcController {
    @Resource
    ArtcService artcService;
    @Resource
    EventService eventService;
    @GetMapping("/artc/show")
    public String showArtc(Model model){
        model.addAttribute("artc",artcService.getAll());
        return "/artc/list";
    }

    @GetMapping("/artc/showDescride")
    public String showArtc(int aid,Model model){
        model.addAttribute("artc", artcService.noXss(artcService.getById(aid)));
        model.addAttribute("service", eventService);
        return "/artc/describe";
    }
    @PostMapping("/artc/new")
    @ResponseBody
    public String newArtc(String title, String describe, HttpServletRequest httpServletRequest){
        if(artcService.newArtc(((UserBean)httpServletRequest.getSession().getAttribute("user")).getId(),
                                title,
                                describe)){
            return "ok";
        }else {
            return "error";
        }
    }

    @GetMapping("/artc/new")
    public String newArtc(){
        return "/artc/newArtc";
    }

    @GetMapping("/artc/myArtc")
    public String newArtc(Model model,HttpServletRequest httpServletRequest) {
        model.addAttribute("artc", artcService.getArtcByUser((UserBean) httpServletRequest.getSession().getAttribute("user")));
        return "/artc/myArtc";
    }

    @GetMapping("/artc/edit")
    public String edit(int id, Model model){
        model.addAttribute("artc",artcService.getById(id));
        return "/artc/edit";
    }

    @PostMapping("/artc/edit")
    @ResponseBody
    public String edit(Model model,HttpServletRequest httpServletRequest,int id,String title, String describe){
        ArtcBean artcBean = artcService.getById(id);
        ArtcBean artcBean1 = new ArtcBean();

        artcBean.setArtc(describe);
        artcBean.setTitle(title);


        if (artcService.update(artcBean)){
            return "ok";
        }else {
            return "error";
        }
    }

    @GetMapping("/artc/delete")
    public String delete(int aid){
        artcService.delete(aid);
        return "redirect:/index";
    }
}
