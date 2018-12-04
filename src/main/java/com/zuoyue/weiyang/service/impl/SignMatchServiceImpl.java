package com.zuoyue.weiyang.service.impl;

import com.zuoyue.weiyang.bean.SignMatch;
import com.zuoyue.weiyang.dao.SignMatchDao;
import com.zuoyue.weiyang.service.SignMatchService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class SignMatchServiceImpl implements SignMatchService {

    @Autowired
    SignMatchDao signMatchDao;

    @Override
    public int insert(SignMatch... signMatches) {
        return signMatchDao.insert(signMatches);
    }

    @Override
    public List<SignMatch> list(SignMatch signMatch) {
        return signMatchDao.list(signMatch);
    }

    @Override
    public List<SignMatch> selectAffirm(SignMatch signMatch) {
        return signMatchDao.selectAffirm(signMatch);
    }

    @Override
    public List<SignMatch> selectById(Long id) {
        return signMatchDao.selectById(id);
    }

    @Override
    public int updateByIds(SignMatch signMatch, Long... ids) {
        return signMatchDao.updateByIds(signMatch,ids);
    }

    @Override
    public int deleteByIds(Long... ids) {
        return signMatchDao.deleteByIds(ids);
    }
}
