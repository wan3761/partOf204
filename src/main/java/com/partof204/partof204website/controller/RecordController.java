package com.partof204.partof204website.controller;

import com.partof204.partof204website.bean.EventBean;
import com.partof204.partof204website.bean.UserBean;
import com.partof204.partof204website.service.EventService;
import jakarta.annotation.Resource;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;

import java.util.ArrayList;
import java.util.List;

@Controller
public class RecordController {
    @Resource
    EventService eventService;
    @GetMapping("/record/history")
    public ModelAndView showHistory(Model model){
        ModelAndView modelAndView = new ModelAndView();
        modelAndView.addObject("events",eventService.toEventBeanWithUsername(eventService.getAllEvents()));

        modelAndView.setViewName("/record/history");
        return modelAndView;
    }

    @PostMapping("/record/newEvent")
    @ResponseBody
    public String newEvent(String date, String describe, String username, HttpServletRequest request){
        if (date.equals("") || describe.equals("") || username.equals("")){
            return "不能为空";
        }
        boolean hasSameData = eventService.hasSameData(date,describe);
        if (hasSameData){
            return "数据已经存在";
        }
        if (!eventService.newEvent(date,describe,username,((UserBean)request.getSession().getAttribute("user")).getId())){
            return "用户不存在";
        }
        return "ok";
    }


    @GetMapping("/record/newEvent")
    public String newEvent(){
        return "/record/newEvent";
    }

    @RequestMapping("/record/search")
    public ModelAndView searchHistory(Model model,String sdate,String edate){
        if ("".equals(sdate) && sdate.equals(edate)){
            ModelAndView modelAndView = new ModelAndView();
            modelAndView.addObject("events",eventService.toEventBeanWithUsername(eventService.getAllEvents()));
            modelAndView.setViewName("/record/history");
            return modelAndView;
        }
        ModelAndView modelAndView = new ModelAndView();
        modelAndView.addObject("events",eventService.toEventBeanWithUsername(eventService.getEventIn(sdate,edate)));
        modelAndView.setViewName("/record/history");
        return modelAndView;
    }

    @GetMapping("/history/delete")
    public ModelAndView delete(String index,HttpServletRequest request,Model model){
        ModelAndView modelAndView = new ModelAndView();
        if (!eventService.canEditOrDelete((UserBean) request.getSession().getAttribute("user"), Integer.parseInt(index))){
            modelAndView.setViewName("/error");
            modelAndView.addObject("msg","没有操作权限");
            return modelAndView;
        }
        eventService.deleteByIndex(Integer.parseInt(index));
        modelAndView.setViewName("redirect:/record/history");
        return modelAndView;
    }

    @GetMapping("/history/edit")
    public ModelAndView edit(int index){
        ModelAndView modelAndView = new ModelAndView();
        modelAndView.setViewName("/record/edit");
        List<EventBean> list = new ArrayList<>();
        list.add(eventService.getById(index));
        modelAndView.addObject("event",eventService.toEventBeanWithUsername(list).get(0));
        return modelAndView;
    }

    @PostMapping("/history/edit")
    @ResponseBody
    public String edit(String index,String date,String describe,String person,HttpServletRequest request){

        if ("".equals(date) || "".equals(describe) || "".equals(person)){
            return "不能为空";
        }
        if (!eventService.canEditOrDelete((UserBean) request.getSession().getAttribute("user"),Integer.parseInt(index))){
            return "没有编辑权限";
        }
        if (!eventService.update(Integer.parseInt(index),date,describe,person,((UserBean)request.getSession().getAttribute("user")).getId())){
            return "用户不存在";
        }
        return "ok";

    }
}
