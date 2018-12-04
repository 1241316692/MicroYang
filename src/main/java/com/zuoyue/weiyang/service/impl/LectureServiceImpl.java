package com.zuoyue.weiyang.service.impl;

import com.zuoyue.weiyang.bean.Lecture;
import com.zuoyue.weiyang.dao.LectureDao;
import com.zuoyue.weiyang.service.LectureService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class LectureServiceImpl implements LectureService {

    @Autowired
    LectureDao lectureDao;

    @Override
    public int insert(Lecture... lectures) {
        return lectureDao.insert(lectures);
    }

    @Override
    public List<Lecture> list(Lecture lecture) {
        return lectureDao.list(lecture);
    }

    @Override
    public Lecture selectById(Long id) {
        return lectureDao.selectById(id);
    }

    @Override
    public int updateByIds(Lecture lecture, Long... ids) {
        return lectureDao.updateByIds(lecture,ids);
    }

    @Override
    public int deleteByIds(Long... ids) {
        return lectureDao.deleteByIds(ids);
    }
}
