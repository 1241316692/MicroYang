package com.zuoyue.weiyang.dao;

import com.zuoyue.weiyang.bean.Member;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface MemberDao {

    int insert (Member...members);

    List<Member> list(Member member);

    List<Member> selectByDp(Long id);

    Member selectById(Long id);

    int updateByIds(@Param("param") Member member, @Param("ids")Long ...ids);

    int deleteByIds(Long... ids);

}
