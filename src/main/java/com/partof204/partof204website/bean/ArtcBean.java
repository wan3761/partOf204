package com.partof204.partof204website.bean;

public class ArtcBean {
    private Integer aid;

    private Integer author;

    private String title;

    private String tag;

    private String artc;

    public Integer getAid() {
        return aid;
    }

    public void setAid(Integer aid) {
        this.aid = aid;
    }

    public Integer getAuthor() {
        return author;
    }

    public void setAuthor(Integer author) {
        this.author = author;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title == null ? null : title.trim();
    }

    public String getTag() {
        return tag;
    }

    public void setTag(String tag) {
        this.tag = tag == null ? null : tag.trim();
    }

    public String getArtc() {
        return artc;
    }

    public void setArtc(String artc) {
        this.artc = artc == null ? null : artc.trim();
    }
}