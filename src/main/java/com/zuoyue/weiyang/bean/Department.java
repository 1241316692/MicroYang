package com.zuoyue.weiyang.bean;

import java.util.List;

public class Department {

    private Long id;
    private Long userId;
    private Long studentUnionId;
    private String departmentName;
    private String dpContent;
    private Boolean hospitalDepartment;
    private String systemType;
    private List<Member> members;

    public Long getId() {
        return id;
    }

    public Department setId(Long id) {
        this.id = id;
        return this;
    }

    public Long getUserId() {
        return userId;
    }

    public Department setUserId(Long userId) {
        this.userId = userId;
        return this;
    }

    public Long getStudentUnionId() {
        return studentUnionId;
    }

    public Department setStudentUnionId(Long studentUnionId) {
        this.studentUnionId = studentUnionId;
        return this;
    }

    public String getDepartmentName() {
        return departmentName;
    }

    public Department setDepartmentName(String departmentName) {
        this.departmentName = departmentName;
        return this;
    }

    public String getDpContent() {
        return dpContent;
    }

    public Department setDpContent(String dpContent) {
        this.dpContent = dpContent;
        return this;
    }

    public Boolean getHospitalDepartment() {
        return hospitalDepartment;
    }

    public Department setHospitalDepartment(Boolean hospitalDepartment) {
        this.hospitalDepartment = hospitalDepartment;
        return this;
    }

    public String getSystemType() {
        return systemType;
    }

    public Department setSystemType(String systemType) {
        this.systemType = systemType;
        return this;
    }

    public List<Member> getMembers() {
        return members;
    }

    public void setMembers(List<Member> members) {
        this.members = members;
    }
}
