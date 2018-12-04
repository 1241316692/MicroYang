package com.zuoyue.weiyang.service;

import com.zuoyue.weiyang.bean.SignMatch;

import java.util.List;

public interface SignMatchService {

    int insert (SignMatch...signMatches);

    List<SignMatch> list(SignMatch signMatch);

    List<SignMatch> selectAffirm(SignMatch signMatch);

    List<SignMatch> selectById(Long id);

    int updateByIds(SignMatch signMatch,Long ...ids);

    int deleteByIds(Long... ids);

}
