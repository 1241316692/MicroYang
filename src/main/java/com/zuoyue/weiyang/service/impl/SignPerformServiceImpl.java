package com.zuoyue.weiyang.service.impl;

import com.zuoyue.weiyang.bean.SignPerform;
import com.zuoyue.weiyang.dao.SignPerformDao;
import com.zuoyue.weiyang.service.SignPerformService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class SignPerformServiceImpl implements SignPerformService {

    @Autowired
    SignPerformDao signPerformDao;

    @Override
    public int insert(SignPerform... signPerforms) {
        return signPerformDao.insert(signPerforms);
    }

    @Override
    public List<SignPerform> list(SignPerform signPerform) {
        return signPerformDao.list(signPerform);
    }

    @Override
    public List<SignPerform> selectAffirm(SignPerform signPerform) {
        return signPerformDao.selectAffirm(signPerform);
    }

    @Override
    public List<SignPerform> selectById(Long id) {
        return signPerformDao.selectById(id);
    }

    @Override
    public int updateByIds(SignPerform signPerform, Long... ids) {
        return signPerformDao.updateByIds(signPerform,ids);
    }

    @Override
    public int deleteByIds(Long... ids) {
        return signPerformDao.deleteByIds(ids);
    }
}
