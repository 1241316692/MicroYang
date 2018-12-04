package com.zuoyue.weiyang.bean;

import java.util.Date;

public class SignMatch {

    private Long id;
    private Long matchId;
    private Long userId;
    private Date time;
    private Boolean affirm;

    public Long getId() {
        return id;
    }

    public SignMatch setId(Long id) {
        this.id = id;
        return this;
    }

    public Long getMatchId() {
        return matchId;
    }

    public SignMatch setMatchId(Long matchId) {
        this.matchId = matchId;
        return this;
    }

    public Long getUserId() {
        return userId;
    }

    public SignMatch setUserId(Long userId) {
        this.userId = userId;
        return this;
    }

    public Date getTime() {
        return time;
    }

    public SignMatch setTime(Date time) {
        this.time = time;
        return this;
    }

    public Boolean getAffirm() {
        return affirm;
    }

    public SignMatch setAffirm(Boolean affirm) {
        this.affirm = affirm;
        return this;
    }
}
