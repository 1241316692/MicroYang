package com.zuoyue.weiyang.service;

import com.zuoyue.weiyang.bean.Perform;

import java.util.List;

public interface PerformService {

    int insert (Perform...performs);

    List<Perform> list(Perform perform);

    List<Perform> listAccept(Perform perform);

    Perform selectById(Long id);

    int updateByIds(Perform perform,Long ...ids);

    int deleteByIds(Long... ids);

}
