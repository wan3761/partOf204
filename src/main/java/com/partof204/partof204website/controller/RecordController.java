package com.partof204.partof204website.controller;

import com.partof204.partof204website.service.EventService;
import jakarta.annotation.Resource;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

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
    public String newEvent(String date,String describe,String username){
        if (date.equals("") || describe.equals("") || username.equals("")){
            return "不能为空";
        }
        boolean hasSameData = eventService.hasSameData(date,describe);
        if (hasSameData){
            return "数据已经存在";
        }
        if (!eventService.newEvent(date,describe,username)){
            return "用户不存在";
        }
        return "ok";
    }

    @GetMapping("/record/newEvent")
    public String newEvent(){
        return "/record/newEvent";
    }

    @RequestMapping("/record/search")
    public ModelAndView searchHistory(Model model,String date,String describe,String person){
        if (date.equals(describe) && describe.equals(person) && date.equals("")){
            ModelAndView modelAndView = new ModelAndView();
            modelAndView.addObject("events",eventService.toEventBeanWithUsername(eventService.getAllEvents()));
            modelAndView.setViewName("/record/history");
            return modelAndView;
        }
        ModelAndView modelAndView = new ModelAndView();
        modelAndView.addObject("events",eventService.toEventBeanWithUsername(eventService.getEventLike(date,describe,person)));
        modelAndView.setViewName("/record/history");
        return modelAndView;
    }

    @GetMapping("/history/delete")
    public String delete(String index){
        eventService.deleteByIndex(Integer.parseInt(index));
        return "redirect:/record/history";
    }
}
