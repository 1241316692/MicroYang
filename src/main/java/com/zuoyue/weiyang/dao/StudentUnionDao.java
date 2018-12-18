package com.zuoyue.weiyang.dao;

import com.zuoyue.weiyang.bean.StudentUnion;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface StudentUnionDao {

    int insert (StudentUnion...studentUnions);

    List<StudentUnion> list(StudentUnion studentUnion);

    List<StudentUnion> listDp(StudentUnion studentUnion);

    StudentUnion selectById(Long id);

    int updateByIds(@Param("param") StudentUnion studentUnion, @Param("ids")Long ...ids);

    int deleteByIds(Long... ids);

}
