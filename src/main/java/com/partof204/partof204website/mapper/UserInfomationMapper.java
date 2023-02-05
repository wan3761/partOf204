package com.partof204.partof204website.mapper;

import com.partof204.partof204website.bean.UserInfomation;
import com.partof204.partof204website.bean.UserInfomationExample;
import java.util.List;
import org.apache.ibatis.annotations.Param;

public interface UserInfomationMapper {
    long countByExample(UserInfomationExample example);

    int deleteByExample(UserInfomationExample example);

    int deleteByPrimaryKey(Integer iid);

    int insert(UserInfomation record);

    int insertSelective(UserInfomation record);

    List<UserInfomation> selectByExample(UserInfomationExample example);

    UserInfomation selectByPrimaryKey(Integer iid);

    int updateByExampleSelective(@Param("record") UserInfomation record, @Param("example") UserInfomationExample example);

    int updateByExample(@Param("record") UserInfomation record, @Param("example") UserInfomationExample example);

    int updateByPrimaryKeySelective(UserInfomation record);

    int updateByPrimaryKey(UserInfomation record);
}