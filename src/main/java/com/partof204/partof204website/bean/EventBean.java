package com.partof204.partof204website.bean;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;

public class EventBean {
    private Date time;

    private String describea;

    private String person;

    public Date getTime() {
        return time;
    }

    public void setTime(Date time) {
        this.time = time;
    }

    public String getDescribea() {
        return describea;
    }

    public void setDescribea(String describea) {
        this.describea = describea == null ? null : describea.trim();
    }

    public String getPerson() {
        return person;
    }

    public void setPerson(String person) {
        this.person = person == null ? null : person.trim();
    }

    public String getFormationDate(){
        DateFormat df = new SimpleDateFormat("yyyy-MM-dd");
        return df.format(time);
    }
}