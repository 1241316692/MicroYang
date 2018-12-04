package com.zuoyue.weiyang.dao;


import com.zuoyue.weiyang.bean.Match;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface MatchDao {

    int insert (Match...matches);

    List<Match> list(Match match);

    Match selectById(Long id);

    int updateByIds(@Param("param") Match match, @Param("ids")Long ...ids);

    int deleteByIds(Long... ids);

}
