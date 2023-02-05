package com.partof204.partof204website.controller;

import com.partof204.partof204website.bean.UserBean;
import com.partof204.partof204website.bean.UserInfomation;
import com.partof204.partof204website.mapper.UserInfomationMapper;
import com.partof204.partof204website.service.UserService;
import jakarta.annotation.Resource;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import static com.partof204.partof204website.Util.MD5;

@Controller
public class UserAdmin {
    @Resource
    UserService userService;
    @Resource
    UserInfomationMapper userInfomationMapper;

    @PostMapping("/user/userUpdate")
    @ResponseBody
    public String userUpdate(HttpServletRequest request, String password, String spwd,String describe){
        UserBean userBean = ((UserBean)request.getSession().getAttribute("user"));
        if (!"".equals(password)){
            if (!userBean.getPassword().equals(MD5(spwd+userBean.getSalt()))){
                return "no";
            }
            userBean.setPassword(MD5(password+userBean.getSalt()));
            if (userService.updateUser(userBean)>0){
                UserInfomation userInfomation = new UserInfomation();
                userInfomation.setIid(((UserBean) request.getSession().getAttribute("user")).getId());
                userInfomation.setDescribea(describe);
                userInfomationMapper.updateByPrimaryKeySelective(userInfomation);
                request.getSession().invalidate();
                return "ok";
            }
        }else {
            UserInfomation userInfomation = new UserInfomation();
            userInfomation.setIid(((UserBean) request.getSession().getAttribute("user")).getId());
            userInfomation.setDescribea(describe);
            userInfomationMapper.updateByPrimaryKeySelective(userInfomation);
            return "ok";
        }
        request.getSession().invalidate();
        return "no";
    }

    @GetMapping("/user/userUpdate")
    public String userUpdate(HttpServletRequest request,Model model){
        model.addAttribute("username", ((UserBean)request.getSession().getAttribute("user")).getName());
        model.addAttribute("describe",userInfomationMapper.selectByPrimaryKey(((UserBean) request.getSession().getAttribute("user")).getId()));
        return "/user/admin";
    }

}
