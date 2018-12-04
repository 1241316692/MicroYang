package com.zuoyue.weiyang.bean;

import com.fasterxml.jackson.annotation.JsonFormat;

import java.util.Date;

public class Perform {
    private Long id;
    private Long userId;
    private String theme;
    private String content;
    private String sponsor;
    private Boolean accept;
    @JsonFormat(pattern = "yyy-MM-dd",timezone ="GMT+8" )
    private Date time;
    private String place;

    public Long getId() {
        return id;
    }

    public Perform setId(Long id) {
        this.id = id;
        return this;
    }

    public Long getUserId() {
        return userId;
    }

    public Perform setUserId(Long userId) {
        this.userId = userId;
        return this;
    }

    public String getTheme() {
        return theme;
    }

    public Perform setTheme(String theme) {
        this.theme = theme;
        return this;
    }

    public String getContent() {
        return content;
    }

    public Perform setContent(String content) {
        this.content = content;
        return this;
    }

    public String getSponsor() {
        return sponsor;
    }

    public Perform setSponsor(String sponsor) {
        this.sponsor = sponsor;
        return this;
    }

    public Boolean getAccept() {
        return accept;
    }

    public Perform setAccept(Boolean accept) {
        this.accept = accept;
        return this;
    }

    public Date getTime() {
        return time;
    }

    public Perform setTime(Date time) {
        this.time = time;
        return this;
    }

    public String getPlace() {
        return place;
    }

    public Perform setPlace(String place) {
        this.place = place;
        return this;
    }
}
