package com.partof204.partof204website.mapper;

import com.partof204.partof204website.bean.EventBean;
import com.partof204.partof204website.bean.EventBeanExample;
import java.util.List;
import org.apache.ibatis.annotations.Param;

public interface EventBeanMapper {
    long countByExample(EventBeanExample example);

    List<EventBean> selectAll();
    int deleteByExample(EventBeanExample example);

    int deleteByPrimaryKey(Integer id);

    int insert(EventBean record);

    int insertSelective(EventBean record);

    List<EventBean> selectByExample(EventBeanExample example);

    EventBean selectByPrimaryKey(Integer id);

    int updateByExampleSelective(@Param("record") EventBean record, @Param("example") EventBeanExample example);

    int updateByExample(@Param("record") EventBean record, @Param("example") EventBeanExample example);

    int updateByPrimaryKeySelective(EventBean record);

    int updateByPrimaryKey(EventBean record);
}