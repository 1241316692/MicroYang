package com.zuoyue.weiyang.service.impl;

import com.zuoyue.weiyang.bean.Match;
import com.zuoyue.weiyang.dao.MatchDao;
import com.zuoyue.weiyang.service.MatchService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class MatchServiceImpl implements MatchService {

    @Autowired
    MatchDao matchDao;

    @Override
    public int insert(Match... matches) {
        return matchDao.insert(matches);
    }

    @Override
    public List<Match> list(Match match) {
        return matchDao.list(match);
    }

    @Override
    public Match selectById(Long id) {
        return matchDao.selectById(id);
    }

    @Override
    public int updateByIds(Match match, Long... ids) {
        return matchDao.updateByIds(match,ids);
    }

    @Override
    public int deleteByIds(Long... ids) {
        return matchDao.deleteByIds(ids);
    }
}
