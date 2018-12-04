package com.zuoyue.weiyang.service;

import com.zuoyue.weiyang.bean.Lecture;
import org.apache.ibatis.annotations.Param;

import java.util.List;

public interface LectureService {

    int insert (Lecture...lectures);

    List<Lecture> list(Lecture lecture);

    Lecture selectById(Long id);

    int updateByIds(@Param("param") Lecture lecture, @Param("ids")Long ...ids);

    int deleteByIds(Long... ids);

}
