package com.zuoyue.weiyang.common;

public class Constant {

    public static final String JWT_SECRET = "a69cff0f0a6318edb714752b0cfa7086";
    public static final int TOKEN_MAX_AGE = 30 * 60; // 单位：秒
    public static final long TOKEN_EXP_TIME = TOKEN_MAX_AGE * 1000L;
    public static final String USER_TYPE_EMP = "admin";
}
