package com.partof204.partof204website.bean;

import java.util.Date;

public class EventBean {
    private Integer id;

    private Date time;

    private String describea;

    private String person;

    private Integer creator;

    private String editor;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

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

    public Integer getCreator() {
        return creator;
    }

    public void setCreator(Integer creator) {
        this.creator = creator;
    }

    public String getEditor() {
        return editor;
    }

    public void setEditor(String editor) {
        this.editor = editor == null ? null : editor.trim();
    }
}