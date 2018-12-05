package com.zuoyue.weiyang.bean;


import java.io.Serializable;

public class RedisLogin implements Serializable {

    private Long id;
    private String token;
    private long refTime;

    public RedisLogin() {

    }

    public RedisLogin(Long id, String token, long refTime) {
        this.id = id;
        this.token = token;
        this.refTime = refTime;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token;
    }

    public long getRefTime() {
        return refTime;
    }

    public void setRefTime(long refTime) {
        this.refTime = refTime;
    }
}
