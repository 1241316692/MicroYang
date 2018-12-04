package com.zuoyue.weiyang.enums;


import com.zuoyue.weiyang.util.ResponseVO;

import java.util.HashMap;
import java.util.Map;

public enum LoginResponseCode {

    USERID_NOT_NULL(3001, "用户id不能为空"),
    LOGIN_TOKEN_NOT_NULL(3002, "登录token不能为空"),
    USERID_NOT_UNAUTHORIZED(3003, "用户token或ID验证不通过"),
    PESPONSE_CODE_UNLOGIN_ERROR(421, "未登录异常"),
    LOGIN_TIME_EXP(3004, "登录时间超长，请重新登录"),
    ;

    // 成员变量
    private int code; // 状态吗
    private String message; // 返回信息

    // 构造方法
    private LoginResponseCode(int code, String mesage) {
        this.code = code;
        this.message = mesage;
    }

    public int getCode() {
        return code;
    }

    public void setCode(int code) {
        this.code = code;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public static ResponseVO builEnumResponseVO(LoginResponseCode responseCode, Object data) {
        return ResponseVO.build();
    }

    public static Map<String, Object> buildReturnMap(LoginResponseCode responseCode, Object data) {
        Map<String, Object> map = new HashMap<>();
        map.put("code", responseCode.getCode());
        map.put("message", responseCode.getMessage());
        map.put("data", data);
        return map;
    }
}
