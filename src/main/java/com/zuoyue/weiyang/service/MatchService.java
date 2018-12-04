package com.zuoyue.weiyang.service;

import com.zuoyue.weiyang.bean.Match;

import java.util.List;

public interface MatchService {

    int insert (Match...matches);

    List<Match> list(Match match);

    Match selectById(Long id);

    int updateByIds(Match match,Long ...ids);

    int deleteByIds(Long... ids);

}
