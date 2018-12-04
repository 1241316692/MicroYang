package com.zuoyue.weiyang.dao;

import com.zuoyue.weiyang.bean.Perform;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface PerformDao {

    int insert (Perform...performs);

    List<Perform> list(Perform perform);

    List<Perform> listAccept(Perform perform);

    Perform selectById(Long id);

    int updateByIds(@Param("param") Perform perform, @Param("ids")Long ...ids);

    int deleteByIds(Long... ids);

}
