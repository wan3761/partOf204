package com.partof204.partof204website.service;

import com.partof204.partof204website.Util;
import com.partof204.partof204website.bean.*;
import com.partof204.partof204website.mapper.EventBeanMapper;
import com.partof204.partof204website.mapper.UserBeanMapper;
import jakarta.annotation.Resource;
import org.springframework.stereotype.Service;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@Service
public class EventService {
    @Resource
    EventBeanMapper eventBeanMapper;
    @Resource
    UserBeanMapper userBeanMapper;
    public List<EventBean> getAllEvents(){
        return eventBeanMapper.selectAll();
    }

    public List<EventBeanWithUsername> toEventBeanWithUsername(List<EventBean> list){
        List<EventBeanWithUsername> result = new ArrayList<>();
        for (int i = 0; i <list.size(); i++){
            EventBean eventBean = list.get(i);
            String ids = eventBean.getPerson();
            String[] idSs = ids.split(",");
            EventBeanWithUsername eventBeanWithUsername = new EventBeanWithUsername();
            eventBeanWithUsername.setTime(eventBean.getTime());
            eventBeanWithUsername.setDescribe(eventBean.getDescribea());
            eventBeanWithUsername.setPerson("");
            eventBeanWithUsername.setIndex(i);
            eventBeanWithUsername.setCreator(userBeanMapper.selectByPrimaryKey(eventBean.getCreator()).getName());

            String[] editors = eventBean.getEditor().split(",");
            for (String editor : editors){
                eventBeanWithUsername.setEditor(eventBeanWithUsername.getEditor()+","+userBeanMapper.selectByPrimaryKey(Integer.parseInt(editor)));
            }

            for (String id : idSs) {
                eventBeanWithUsername.setPerson(eventBeanWithUsername.getPerson()+","+(userBeanMapper.selectByPrimaryKey(Integer.valueOf(id))).getName());
            }
            eventBeanWithUsername.setPerson(eventBeanWithUsername.getPerson().substring(1,eventBeanWithUsername.getPerson().length()));
            result.add(eventBeanWithUsername);
        }
        return result;
    }

    public boolean hasSameData(String date,String describe){
        DateFormat df = new SimpleDateFormat("yyyy-MM-dd");
        Date date1 = null;
        try {
            date1 = df.parse(date);
        } catch (ParseException e) {
            throw new RuntimeException(e);
        }

        EventBeanExample example = new EventBeanExample();
        EventBeanExample.Criteria criteria = example.createCriteria();
        criteria.andDescribeaEqualTo(describe);
        criteria.andTimeEqualTo(date1);

        return eventBeanMapper.countByExample(example) > 0;
    }

    public boolean newEvent(String date,String describe,String username,int creator){
        DateFormat df = new SimpleDateFormat("yyyy-MM-dd");
        Date date1 = null;
        try {
            date1 = df.parse(date);
        } catch (ParseException e) {
            throw new RuntimeException(e);
        }

        EventBean eventBean = new EventBean();
        eventBean.setTime(date1);
        eventBean.setDescribea(describe);
        eventBean.setCreator(creator);
        eventBean.setEditor(String.valueOf(creator));
        String[] usernames = username.split(",");
        StringBuilder stringBuffer = new StringBuilder();
        for (String user : usernames){
            UserBeanExample userBeanExample = new UserBeanExample();
            userBeanExample.createCriteria().andNameEqualTo(user);
            List<UserBean> list = userBeanMapper.selectByExample(userBeanExample);
            if (list.size() == 0){
                return false;
            }
            stringBuffer.append(list.get(0).getId().toString()).append(",");
        }
        String s = stringBuffer.toString();
        eventBean.setPerson(s.substring(0,s.length()-1));

        eventBeanMapper.insert(eventBean);
        return true;
    }

    public List<EventBean> getEventLike(String date,String describe,String person){
        EventBeanExample example = new EventBeanExample();
        EventBeanExample.Criteria criteria = example.createCriteria();
        if (!date.equals("")){
            DateFormat df = new SimpleDateFormat("yyyy-MM-dd");
            Date date1 = null;
            try {
                date1 = df.parse(date);
            } catch (ParseException e) {
                throw new RuntimeException(e);
            }
            criteria.andTimeEqualTo(date1);
        }
        if (!describe.equals("")){
            criteria.andDescribeaLike(describe);
        }
        if (!person.equals("")){
            criteria.andPersonLike(person);
        }
        return eventBeanMapper.selectByExample(example);
    }

    public void deleteByIndex(int index){
        List<EventBean> list = eventBeanMapper.selectAll();
        if (index+1 > list.size()){
            return;
        }
        EventBean eventBean = list.get(index);
        EventBeanExample example = new EventBeanExample();
        EventBeanExample.Criteria criteria = example.createCriteria();
        criteria.andTimeEqualTo(eventBean.getTime());
        criteria.andDescribeaEqualTo(eventBean.getDescribea());
        criteria.andPersonEqualTo(eventBean.getPerson());

        eventBeanMapper.deleteByExample(example);
    }

    public EventBean getById(int id){
        return eventBeanMapper.selectAll().get(id);
    }

    public boolean update(int id,String date,String describe,String username,int editor){
        EventBean eventBean = getById(id);

        DateFormat df = new SimpleDateFormat("yyyy-MM-dd");
        EventBeanExample example = new EventBeanExample();
            example.createCriteria().andPersonEqualTo(eventBean.getPerson())
                                    .andDescribeaEqualTo(eventBean.getDescribea())
                                    .andTimeEqualTo(eventBean.getTime());

        eventBean.setEditor(eventBean.getEditor()+","+editor);
        try {
            eventBean.setTime(df.parse(date));
        } catch (ParseException e) {
            throw new RuntimeException(e);
        }
        eventBean.setDescribea(describe);
        List<Integer> ids = getIdsByNames(username);
        StringBuilder sb = new StringBuilder();
        for (Integer idsss : ids) {
            sb.append(idsss).append(",");
        }
        sb.substring(0, sb.length() - 1);
        eventBean.setPerson(sb.toString());
        return eventBeanMapper.updateByExampleSelective(eventBean,example) > 0;
    }

    public int getIdByName(String name) {
        UserBeanExample example = new UserBeanExample();
        example.createCriteria().andNameEqualTo(name);
        return userBeanMapper.selectByExample(example).get(0).getId();
    }

    public String getNameById(int id) {
        return userBeanMapper.selectByPrimaryKey(id).getName();
    }

    public List<Integer> getIdsByNames(String name){
        List<Integer> ids = new ArrayList<>();
        for (String name1 : name.split(",")){
            ids.add(getIdByName(name1));
        }
        return ids;
    }
}
