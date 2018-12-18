package com.zuoyue.weiyang.bean;

import java.util.List;

public class StudentUnion {

    private Long id;
    private Long userId;
    private String name;
    private String Sessions;
    private String suContent;
    private Boolean courtYardStUn;
    private List<Department> departments;

    public Long getId() {
        return id;
    }

    public StudentUnion setId(Long id) {
        this.id = id;
        return this;
    }

    public Long getUserId() {
        return userId;
    }

    public StudentUnion setUserId(Long userId) {
        this.userId = userId;
        return this;
    }

    public String getName() {
        return name;
    }

    public StudentUnion setName(String name) {
        this.name = name;
        return this;
    }

    public String getSessions() {
        return Sessions;
    }

    public StudentUnion setSessions(String sessions) {
        Sessions = sessions;
        return this;
    }

    public String getSuContent() {
        return suContent;
    }

    public StudentUnion setSuContent(String suContent) {
        this.suContent = suContent;
        return this;
    }

    public Boolean getCourtYardStUn() {
        return courtYardStUn;
    }

    public StudentUnion setCourtYardStUn(Boolean courtYardStUn) {
        this.courtYardStUn = courtYardStUn;
        return this;
    }

    public List<Department> getDepartments() {
        return departments;
    }

    public void setDepartments(List<Department> departments) {
        this.departments = departments;
    }
}
