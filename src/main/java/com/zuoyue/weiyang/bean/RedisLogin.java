package com.zuoyue.weiyang.bean;

public class RedisLogin {

    private String uid;
    private String token;
    private long refTime;

    public RedisLogin() {

    }

    public RedisLogin(String uid, String token, long refTime) {
        this.uid = uid;
        this.token = token;
        this.refTime = refTime;
    }

    public String getUid() {
        return uid;
    }

    public void setUid(String uid) {
        this.uid = uid;
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
