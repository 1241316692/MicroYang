package com.zuoyue.weiyang.service.impl;

import com.zuoyue.weiyang.bean.Member;
import com.zuoyue.weiyang.dao.MemberDao;
import com.zuoyue.weiyang.service.MemberService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class MemberServiceImpl implements MemberService {

    @Autowired
    MemberDao memberDao;

    @Override
    public int insert(Member... members) {
        return memberDao.insert(members);
    }

    @Override
    public List<Member> list(Member member) {
        return memberDao.list(member);
    }

    @Override
    public Member selectById(Long id) {
        return memberDao.selectById(id);
    }

    @Override
    public int updateByIds(Member member, Long... ids) {
        return memberDao.updateByIds(member,ids);
    }

    @Override
    public int deleteByIds(Long... ids) {
        return memberDao.deleteByIds(ids);
    }
}
