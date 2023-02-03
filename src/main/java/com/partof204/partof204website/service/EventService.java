package com.partof204.partof204website.service;

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
            eventBeanWithUsername.setPerson(new ArrayList<>());
            eventBeanWithUsername.setIndex(i);

            for (String id : idSs){
                eventBeanWithUsername.getPerson().add(userBeanMapper.selectByPrimaryKey(Integer.valueOf(id)));
            }
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

    public boolean newEvent(String date,String describe,String username){
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
}
