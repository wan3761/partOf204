package com.partof204.partof204website.service;

import com.partof204.partof204website.bean.ArtcBean;
import com.partof204.partof204website.bean.ArtcBeanExample;
import com.partof204.partof204website.bean.UserBean;
import com.partof204.partof204website.mapper.ArtcBeanMapper;
import jakarta.annotation.Resource;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class ArtcService {
    @Resource
    ArtcBeanMapper artcBeanMapper;
    @Resource
    EventService eventService;

    public List<ArtcBean> getAll(){
        return artcBeanMapper.selectAll();
    }

    public ArtcBean getById(int id){
        return artcBeanMapper.selectByPrimaryKey(id);
    }

    public List<ArtcBean> noXss(List<ArtcBean> list){
        List<ArtcBean> list1 = new ArrayList<>();
        for (ArtcBean o : list) {
            String artc = o.getArtc();
            String[] artc1 = artc.split("<script>");
            artc="";
            for (String artc2 : artc1) {
                artc+=artc2;
            }
            o.setArtc(artc);

            artc = o.getArtc();
            artc1 = artc.split("</script>");
            artc="";
            for (String artc2 : artc1) {
                artc+=artc2;
            }
            list1.add(o);
        }
        return list1;
    }

    public ArtcBean noXss(ArtcBean o){
        String artc = o.getArtc();
        String[] artc1 = artc.split("<script");
        artc="";
        for (String artc2 : artc1) {
            artc+=artc2;
        }
        o.setArtc(artc);

        artc = o.getArtc();
        artc1 = artc.split("script>");
        artc="";
        for (String artc2 : artc1) {
            artc+=artc2;
        }
        o.setArtc(artc);
        return o;
    }

    public boolean newArtc(int author,String title, String describe){
        ArtcBean artcBean = new ArtcBean();
        artcBean.setAuthor(author);
        artcBean.setTitle(title);
        artcBean.setArtc(describe);
        artcBean.setTag("反对双塔");
        artcBean = noXss(artcBean);
        return artcBeanMapper.insertSelective(artcBean) > 0;
    }

    public List<ArtcBean> getArtcByUser(UserBean userBean){
        ArtcBeanExample artcBean = new ArtcBeanExample();
        artcBean.createCriteria().andAuthorEqualTo(userBean.getId());
        return artcBeanMapper.selectByExample(artcBean);
    }

    public boolean update(ArtcBean artcBean1) {
        return artcBeanMapper.updateByPrimaryKeySelective(artcBean1)>0;
    }
    public boolean delete(int id){
        return artcBeanMapper.deleteByPrimaryKey(id) > 0;
    }
}
