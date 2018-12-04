package com.zuoyue.weiyang.service;

import com.zuoyue.weiyang.bean.User;

import java.util.List;

public interface UserService {

    int insert (User...users);

    List<User> list(User user);

    User selectById(Long id);

    User selectByUname(Long studentId);

    User selectByUsername(String username);

    int updateByIds( User user, Long ...ids);

    int deleteByIds(Long... ids);

}
