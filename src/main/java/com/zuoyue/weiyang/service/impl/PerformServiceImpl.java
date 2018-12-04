package com.zuoyue.weiyang.service.impl;

import com.zuoyue.weiyang.bean.Perform;
import com.zuoyue.weiyang.dao.PerformDao;
import com.zuoyue.weiyang.service.PerformService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class PerformServiceImpl implements PerformService {

    @Autowired
    PerformDao performDao;

    @Override
    public int insert(Perform... performs) {
        return performDao.insert(performs);
    }

    @Override
    public List<Perform> list(Perform perform) {
        return performDao.list(perform);
    }

    @Override
    public List<Perform> listAccept(Perform perform) {
        return performDao.listAccept(perform);
    }

    @Override
    public Perform selectById(Long id) {
        return performDao.selectById(id);
    }

    @Override
    public int updateByIds(Perform perform, Long... ids) {
        return performDao.updateByIds(perform,ids);
    }

    @Override
    public int deleteByIds(Long... ids) {
        return performDao.deleteByIds(ids);
    }
}
