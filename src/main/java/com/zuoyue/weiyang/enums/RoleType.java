package com.zuoyue.weiyang.enums;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

@JsonFormat(shape = JsonFormat.Shape.OBJECT)
@JsonIgnoreProperties(value = {"declaring_class"})
public enum RoleType {

    GUEST(1, "学生", Roles.GUEST),
    MANAGER(2, "管理员", Roles.MANAGER),
    ADMIN(3, "超级管理员", Roles.ADMIN),
    ;

    private final int value;
    private final String desc;
    private final String role;

    RoleType(int value, String desc, String role) {
        this.value = value;
        this.desc = desc;
        this.role = role;
    }

    public int getValue() {
        return value;
    }

    public String getDesc() {
        return desc;
    }

    public String getRole() {
        return role;
    }

    public static RoleType valueOf(int value) {
        for (RoleType roleType : values()) {
            if (roleType.value == value) return roleType;
        }
        return null;
    }
}
