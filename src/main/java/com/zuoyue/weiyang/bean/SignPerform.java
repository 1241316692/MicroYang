package com.zuoyue.weiyang.bean;

import java.util.Date;

public class SignPerform {

    private Long id;
    private Long userId;
    private Long performId;
    private Date time;
    private Boolean affirm;

    public Long getId() {
        return id;
    }

    public SignPerform setId(Long id) {
        this.id = id;
        return this;
    }

    public Long getUserId() {
        return userId;
    }

    public SignPerform setUserId(Long userId) {
        this.userId = userId;
        return this;
    }

    public Long getPerformId() {
        return performId;
    }

    public SignPerform setPerformId(Long performId) {
        this.performId = performId;
        return this;
    }

    public Date getTime() {
        return time;
    }

    public SignPerform setTime(Date time) {
        this.time = time;
        return this;
    }

    public Boolean getAffirm() {
        return affirm;
    }

    public SignPerform setAffirm(Boolean affirm) {
        this.affirm = affirm;
        return this;
    }
}
