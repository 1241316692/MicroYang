package com.zuoyue.weiyang.service.impl;

import com.zuoyue.weiyang.bean.StudentUnion;
import com.zuoyue.weiyang.dao.StudentUnionDao;
import com.zuoyue.weiyang.service.StudentUnionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class StudentUnionServiceImpl implements StudentUnionService {

    @Autowired
    StudentUnionDao studentUnionDao;

    @Override
    public int insert(StudentUnion... studentUnions) {
        return studentUnionDao.insert(studentUnions);
    }

    @Override
    public List<StudentUnion> list(StudentUnion studentUnion) {
        return studentUnionDao.list(studentUnion);
    }

    @Override
    public List<StudentUnion> listDp(StudentUnion studentUnion) {
        return studentUnionDao.listDp(studentUnion);
    }

    @Override
    public StudentUnion selectById(Long id) {
        return studentUnionDao.selectById(id);
    }

    @Override
    public int updateByIds(StudentUnion studentUnion, Long... ids) {
        return studentUnionDao.updateByIds(studentUnion,ids);
    }

    @Override
    public int deleteByIds(Long... ids) {
        return studentUnionDao.deleteByIds(ids);
    }
}
