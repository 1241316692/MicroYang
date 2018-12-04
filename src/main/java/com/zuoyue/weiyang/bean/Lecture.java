package com.zuoyue.weiyang.bean;

import com.fasterxml.jackson.annotation.JsonFormat;

import java.util.Date;

public class Lecture {
    private Long id;
    private Long user_id;
    private String theme;
    private String content;
    private String lecturer;
    @JsonFormat(pattern = "yyy-MM-dd",timezone ="GMT+8" )
    private Date time;
    private String duration;
    private Long number;

    public Long getId() {
        return id;
    }

    public Lecture setId(Long id) {
        this.id = id;
        return this;
    }

    public Long getUser_id() {
        return user_id;
    }

    public Lecture setUser_id(Long user_id) {
        this.user_id = user_id;
        return this;
    }

    public String getTheme() {
        return theme;
    }

    public Lecture setTheme(String theme) {
        this.theme = theme;
        return this;
    }

    public String getContent() {
        return content;
    }

    public Lecture setContent(String content) {
        this.content = content;
        return this;
    }

    public String getLecturer() {
        return lecturer;
    }

    public Lecture setLecturer(String lecturer) {
        this.lecturer = lecturer;
        return this;
    }

    public Date getTime() {
        return time;
    }

    public Lecture setTime(Date time) {
        this.time = time;
        return this;
    }

    public String getDuration() {
        return duration;
    }

    public Lecture setDuration(String duration) {
        this.duration = duration;
        return this;
    }

    public Long getNumber() {
        return number;
    }

    public Lecture setNumber(Long number) {
        this.number = number;
        return this;
    }
}
