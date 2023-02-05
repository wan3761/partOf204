package com.partof204.partof204website.mapper;

import com.partof204.partof204website.bean.EventBean;
import com.partof204.partof204website.bean.EventBeanExample;
import java.util.List;
import org.apache.ibatis.annotations.Param;

public interface EventBeanMapper {

    List<EventBean> selectAll();
    long countByExample(EventBeanExample example);

    int deleteByExample(EventBeanExample example);

    int insert(EventBean record);

    int insertSelective(EventBean record);

    List<EventBean> selectByExample(EventBeanExample example);

    int updateByExampleSelective(@Param("record") EventBean record, @Param("example") EventBeanExample example);

    int updateByExample(@Param("record") EventBean record, @Param("example") EventBeanExample example);
}