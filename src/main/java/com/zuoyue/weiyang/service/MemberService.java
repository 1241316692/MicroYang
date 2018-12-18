package com.zuoyue.weiyang.service;

import com.zuoyue.weiyang.bean.Member;

import java.util.List;

public interface MemberService {

    int insert (Member...members);

    List<Member> list(Member member);

    Member selectById(Long id);

    int updateByIds(Member member,Long ...ids);

    int deleteByIds(Long... ids);

}
