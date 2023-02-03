package com.partof204.partof204website.bean;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class EventBeanWithUsername {
    private Date time;

    private int index;

    private String describe;

    private List<UserBean> person = new ArrayList<>();

    public Date getTime() {
        return time;
    }

    public void setTime(Date time) {
        this.time = time;
    }

    public String getDescribe() {
        return describe;
    }

    public void setDescribe(String describe) {
        this.describe = describe == null ? null : describe.trim();
    }

    public List<UserBean> getPerson() {
        return person;
    }

    public void setPerson(List<UserBean> person) {
        this.person = person;
    }

    public String getFormationDate(){
        DateFormat df = new SimpleDateFormat("yyyy-MM-dd");
        return df.format(time);
    }

    public int getIndex() {
        return index;
    }

    public void setIndex(int index) {
        this.index = index;
    }
}
