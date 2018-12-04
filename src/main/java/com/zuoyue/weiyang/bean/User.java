package com.zuoyue.weiyang.bean;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.zuoyue.weiyang.enums.RoleType;

public class User {
    private Long id;
    private String username;
    private RoleType status;
    private Long studentId;
    private String sex;
    private String school;
    private String systemType;
    private String classes;
    @JsonIgnore
    private String password;

    public Long getId() {
        return id;
    }

    public User setId(Long id) {
        this.id = id;
        return this;
    }

    public String getUsername() {
        return username;
    }

    public User setUsername(String username) {
        this.username = username;
        return this;
    }

    public RoleType getStatus() {
        return status;
    }

    public User setStatus(RoleType status) {
        this.status = status;
        return this;
    }

    public Long getStudentId() {
        return studentId;
    }

    public User setStudentId(Long studentId) {
        this.studentId = studentId;
        return this;
    }

    public String getSex() {
        return sex;
    }

    public User setSex(String sex) {
        this.sex = sex;
        return this;
    }

    public String getSchool() {
        return school;
    }

    public User setSchool(String school) {
        this.school = school;
        return this;
    }

    public String getSystemType() {
        return systemType;
    }

    public User setSystemType(String systemType) {
        this.systemType = systemType;
        return this;
    }

    public String getClasses() {
        return classes;
    }

    public User setClasses(String classes) {
        this.classes = classes;
        return this;
    }

    public String getPassword() {
        return password;
    }

    public User setPassword(String password) {
        this.password = password;
        return this;
    }
}
