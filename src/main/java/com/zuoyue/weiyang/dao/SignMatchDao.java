package com.zuoyue.weiyang.dao;

import com.zuoyue.weiyang.bean.SignMatch;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface SignMatchDao {

    int insert (SignMatch...signMatches);

    List<SignMatch> list(SignMatch match);

    List<SignMatch> selectAffirm(SignMatch signMatch);

    List<SignMatch> selectById(Long id);

    int updateByIds(@Param("param") SignMatch signMatch, @Param("ids")Long ...ids);

    int deleteByIds(Long... ids);

}
