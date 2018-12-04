package com.zuoyue.weiyang.shiro;

import org.apache.shiro.authc.AuthenticationToken;

public class JwtToken implements AuthenticationToken {

    private static final long serialVersionUID = -3304951806212959641L;

    private String jwt; // json web token
    private String host; // 客户端IP

    public JwtToken(String jwt, String host) {
        this.jwt = jwt;
        this.host = host;
    }

    @Override
    public Object getPrincipal() {
        return this.jwt;
    }

    @Override
    public Object getCredentials() {
        return Boolean.TRUE;
    }

    public static long getSerialVersionUID() {
        return serialVersionUID;
    }

    public String getJwt() {
        return jwt;
    }

    public void setJwt(String jwt) {
        this.jwt = jwt;
    }

    public String getHost() {
        return host;
    }

    public void setHost(String host) {
        this.host = host;
    }
}
