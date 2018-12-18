package com.zuoyue.weiyang.bean;

public class Member {

    private Long id;
    private Long userId;
    private Long studentUnionId;
    private Long departmentId;
    private User user;

    public Long getId() {
        return id;
    }

    public Member setId(Long id) {
        this.id = id;
        return this;
    }

    public Long getUserId() {
        return userId;
    }

    public Member setUserId(Long userId) {
        this.userId = userId;
        return this;
    }

    public Long getStudentUnionId() {
        return studentUnionId;
    }

    public Member setStudentUnionId(Long studentUnionId) {
        this.studentUnionId = studentUnionId;
        return this;
    }

    public Long getDepartmentId() {
        return departmentId;
    }

    public Member setDepartmentId(Long departmentId) {
        this.departmentId = departmentId;
        return this;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }
}
