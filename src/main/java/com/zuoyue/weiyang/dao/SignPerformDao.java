package com.zuoyue.weiyang.dao;

import com.zuoyue.weiyang.bean.SignPerform;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface SignPerformDao {

    int insert (SignPerform...signPerforms);

    List<SignPerform> list(SignPerform signPerform);

    List<SignPerform> selectAffirm(SignPerform signPerform);

    List<SignPerform> selectById(Long id);

    int updateByIds(@Param("param") SignPerform signPerform, @Param("ids")Long ...ids);

    int deleteByIds(Long... ids);

}
