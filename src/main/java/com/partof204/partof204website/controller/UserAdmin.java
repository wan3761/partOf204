package com.partof204.partof204website.controller;

import com.partof204.partof204website.bean.UserBean;
import com.partof204.partof204website.bean.UserInfomation;
import com.partof204.partof204website.mapper.UserBeanMapper;
import com.partof204.partof204website.mapper.UserInfomationMapper;
import com.partof204.partof204website.service.UserService;
import jakarta.annotation.Resource;
import jakarta.servlet.http.HttpServletRequest;
import lombok.SneakyThrows;
import org.apache.commons.codec.digest.DigestUtils;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.FileOutputStream;

import static com.partof204.partof204website.Util.MD5;

@Controller
public class UserAdmin {
    @Resource
    UserService userService;

    @Value("${web.upload-path}")
    String filePath;
    @Resource
    UserInfomationMapper userInfomationMapper;
    @Resource
    UserBeanMapper userBeanMapper;

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


    @SneakyThrows
    @PostMapping("/user/updateImg")
    @ResponseBody
    public String userUpdate(@RequestParam("img") MultipartFile img,HttpServletRequest request){
        if (img==null){
            return "error";
        }
        String last = img.getOriginalFilename();
        String[] a = last.split("\\.");
        last=a[a.length - 1];
        if (!last.equals("png") && !last.equals("jpg") && !last.equals("gif")){
            return "不支持的格式";
        }

        UserBean userBean = (UserBean) request.getSession().getAttribute("user");
        if (!"".equals(userBean.getImg())){
            new File(filePath+userBean.getImg()).delete();
        }
        String name = DigestUtils.md5Hex(img.getBytes())+"."+last;
        String path = filePath;
        File p = new File(path);
        File file = new File(filePath,name);
        if (!p.exists()){
            p.mkdirs();
        }
        byte[] data = img.getBytes();
        FileOutputStream fos = new FileOutputStream(file);
        fos.write(data);
        fos.flush();
        fos.close();
        userBean.setImg(name);


        return userBeanMapper.updateByPrimaryKeySelective(userBean) > 0 ? "ok":"error";
    }

}
