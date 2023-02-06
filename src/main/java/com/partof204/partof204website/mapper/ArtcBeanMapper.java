package com.partof204.partof204website.mapper;

import com.partof204.partof204website.bean.ArtcBean;
import com.partof204.partof204website.bean.ArtcBeanExample;
import java.util.List;
import org.apache.ibatis.annotations.Param;

public interface ArtcBeanMapper {
    long countByExample(ArtcBeanExample example);

    int deleteByExample(ArtcBeanExample example);

    int deleteByPrimaryKey(Integer aid);

    int insert(ArtcBean record);

    int insertSelective(ArtcBean record);

    List<ArtcBean> selectByExampleWithBLOBs(ArtcBeanExample example);

    List<ArtcBean> selectByExample(ArtcBeanExample example);

    ArtcBean selectByPrimaryKey(Integer aid);

    int updateByExampleSelective(@Param("record") ArtcBean record, @Param("example") ArtcBeanExample example);

    int updateByExampleWithBLOBs(@Param("record") ArtcBean record, @Param("example") ArtcBeanExample example);

    int updateByExample(@Param("record") ArtcBean record, @Param("example") ArtcBeanExample example);

    int updateByPrimaryKeySelective(ArtcBean record);

    int updateByPrimaryKeyWithBLOBs(ArtcBean record);

    int updateByPrimaryKey(ArtcBean record);

    List<ArtcBean> selectAll();
}