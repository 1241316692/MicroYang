package com.zuoyue.weiyang.bean;

public class Match {
    private Long id;
    private Long userId;
    private String theme;
    private String content;
    private String organizer;

    public Long getId() {
        return id;
    }

    public Match setId(Long id) {
        this.id = id;
        return this;
    }

    public Long getUserId() {
        return userId;
    }

    public Match setUserId(Long userId) {
        this.userId = userId;
        return this;
    }

    public String getTheme() {
        return theme;
    }

    public Match setTheme(String theme) {
        this.theme = theme;
        return this;
    }

    public String getContent() {
        return content;
    }

    public Match setContent(String content) {
        this.content = content;
        return this;
    }

    public String getOrganizer() {
        return organizer;
    }

    public Match setOrganizer(String organizer) {
        this.organizer = organizer;
        return this;
    }
}
