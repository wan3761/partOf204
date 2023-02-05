package com.partof204.partof204website.bean;

public class UserInfomation {
    private Integer iid;

    private String describea;

    public Integer getIid() {
        return iid;
    }

    public void setIid(Integer iid) {
        this.iid = iid;
    }

    public String getDescribea() {
        return describea;
    }

    public void setDescribea(String describea) {
        this.describea = describea == null ? null : describea.trim();
    }
}