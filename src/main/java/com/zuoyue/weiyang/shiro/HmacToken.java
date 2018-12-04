package com.zuoyue.weiyang.shiro;

import org.apache.shiro.authc.AuthenticationToken;

import java.util.Map;

public class HmacToken implements AuthenticationToken {

    private static final long serialVersionUID = -424939335090442553L;

    private String clientKey; // 客户标识（可以是用户名、app id等等）
    private String digest; // 消息摘要
    private String timestamp; // 时间戳
    private Map<String, String[]> parameters; // 访问参数
    private String host; // 客户端IP

    public HmacToken(String clientKey, String digest, String timestamp, Map<String, String[]> parameters, String host) {
        this.clientKey = clientKey;
        this.digest = digest;
        this.timestamp = timestamp;
        this.parameters = parameters;
        this.host = host;
    }

    @Override
    public Object getPrincipal() {
        return this.clientKey;
    }

    @Override
    public Object getCredentials() {
        return Boolean.TRUE;
    }

    public String getClientKey() {
        return clientKey;
    }

    public void setClientKey(String clientKey) {
        this.clientKey = clientKey;
    }

    public String getDigest() {
        return digest;
    }

    public void setDigest(String digest) {
        this.digest = digest;
    }

    public String getTimestamp() {
        return timestamp;
    }

    public void setTimestamp(String timestamp) {
        this.timestamp = timestamp;
    }

    public Map<String, String[]> getParameters() {
        return parameters;
    }

    public void setParameters(Map<String, String[]> parameters) {
        this.parameters = parameters;
    }

    public String getHost() {
        return host;
    }

    public void setHost(String host) {
        this.host = host;
    }
}
