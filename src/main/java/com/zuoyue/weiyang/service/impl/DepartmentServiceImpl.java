package com.zuoyue.weiyang.service.impl;

import com.zuoyue.weiyang.bean.Department;
import com.zuoyue.weiyang.dao.DepartmentDao;
import com.zuoyue.weiyang.service.DepartmentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class DepartmentServiceImpl implements DepartmentService {

    @Autowired
    DepartmentDao departmentDao;

    @Override
    public int insert(Department... departments) {
        return departmentDao.insert(departments);
    }

    @Override
    public List<Department> list(Department department) {
        return departmentDao.list(department);
    }

    @Override
    public Department selectById(Long id) {
        return departmentDao.selectById(id);
    }

    @Override
    public List<Department> selectByMb(Long id) {
        return departmentDao.selectByMb(id);
    }

    @Override
    public int updateByIds(Department department, Long... ids) {
        return departmentDao.updateByIds(department,ids);
    }

    @Override
    public int deleteByIds(Long... ids) {
        return departmentDao.deleteByIds(ids);
    }
}
