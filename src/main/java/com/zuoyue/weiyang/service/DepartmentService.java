package com.zuoyue.weiyang.service;

import com.zuoyue.weiyang.bean.Department;

import java.util.List;

public interface DepartmentService {

    int insert (Department...departments);

    List<Department> list(Department department);

    Department selectById(Long id);

    List<Department> selectByMb(Long id);

    int updateByIds(Department department, Long ...ids);

    int deleteByIds(Long... ids);

}
