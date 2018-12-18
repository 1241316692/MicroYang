package com.zuoyue.weiyang.service;

import com.zuoyue.weiyang.bean.StudentUnion;

import java.util.List;

public interface StudentUnionService {

    int insert (StudentUnion...studentUnions);

    List<StudentUnion> list(StudentUnion studentUnion);

    List<StudentUnion> listDp(StudentUnion studentUnion);

    StudentUnion selectById(Long id);

    int updateByIds(StudentUnion studentUnion,Long ...ids);

    int deleteByIds(Long... ids);

}
