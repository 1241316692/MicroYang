package com.zuoyue.weiyang.dao;

import com.zuoyue.weiyang.bean.Department;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface DepartmentDao {

    int insert (Department...departments);

    List<Department> list(Department department);

    Department selectById(Long id);

    List<Department> selectByMb(Long id);

    int updateByIds(@Param("param") Department department, @Param("ids")Long ...ids);

    int deleteByIds(Long... ids);

}
