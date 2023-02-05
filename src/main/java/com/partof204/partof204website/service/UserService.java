package com.partof204.partof204website.service;

import com.partof204.partof204website.bean.UserBean;
import com.partof204.partof204website.bean.UserBeanExample;
import com.partof204.partof204website.mapper.UserBeanMapper;
import jakarta.annotation.Resource;
import org.springframework.stereotype.Service;

import java.util.List;

import static com.partof204.partof204website.Util.MD5;

@Service
public class UserService {
    @Resource
    UserBeanMapper userMapper;

    public UserBean getUserByName(String name){
        UserBeanExample userBeanExample = new UserBeanExample();
        UserBeanExample.Criteria criteria = userBeanExample.createCriteria();
        criteria.andNameEqualTo(name);
        return userMapper.selectByExample(userBeanExample).get(0);
    }

    public UserBean getUserByUserExample(String username,String password){
        UserBean userBean = getUserByName(username);
        if (MD5(password+userBean.getSalt()).equals(userBean.getPassword())){
            return userBean;
        }
        return null;
    }

    public int updateUser(UserBean userBean) {
        return userMapper.updateByPrimaryKey(userBean);
    }

    public List<UserBean> getAll() {
        return userMapper.selectAll();
    }

    public UserBean getUserById(int id) {
        return userMapper.selectByPrimaryKey(id);
    }
}
