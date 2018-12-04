package com.zuoyue.weiyang.dao;

import com.zuoyue.weiyang.bean.Lecture;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface LectureDao {

    int insert (Lecture...lectures);

    List<Lecture> list(Lecture lecture);

    Lecture selectById(Long id);

    int updateByIds(@Param("param") Lecture lecture, @Param("ids")Long ...ids);

    int deleteByIds(Long... ids);

}
