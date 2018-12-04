package com.zuoyue.weiyang.shiro;

import java.util.Date;

public class JwtPlayload {

    private Long id;
    private String userName;
    private String issuer;
    private Date issuedAt;
    private String Audience;
    private String roles;
    private String perms;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getUserName() {
        return userName;
    }

    public JwtPlayload setUserName(String userName) {
        this.userName = userName;
        return this;
    }

    public String getIssuer() {
        return issuer;
    }

    public void setIssuer(String issuer) {
        this.issuer = issuer;
    }

    public Date getIssuedAt() {
        return issuedAt;
    }

    public void setIssuedAt(Date issuedAt) {
        this.issuedAt = issuedAt;
    }

    public String getAudience() {
        return Audience;
    }

    public void setAudience(String audience) {
        Audience = audience;
    }

    public String getRoles() {
        return roles;
    }

    public void setRoles(String roles) {
        this.roles = roles;
    }

    public String getPerms() {
        return perms;
    }

    public void setPerms(String perms) {
        this.perms = perms;
    }
}
