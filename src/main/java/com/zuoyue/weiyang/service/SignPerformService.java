package com.zuoyue.weiyang.service;

import com.zuoyue.weiyang.bean.SignPerform;

import java.util.List;

public interface SignPerformService {

    int insert (SignPerform...signPerforms);

    List<SignPerform> list(SignPerform signPerform);

    List<SignPerform> selectAffirm(SignPerform signPerform);

    List<SignPerform> selectById(Long id);

    int updateByIds( SignPerform signPerform,Long ...ids);

    int deleteByIds(Long... ids);

}
