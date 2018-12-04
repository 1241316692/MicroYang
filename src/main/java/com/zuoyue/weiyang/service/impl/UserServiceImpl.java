package com.zuoyue.weiyang.service.impl;

import com.zuoyue.weiyang.bean.User;
import com.zuoyue.weiyang.dao.UserDao;
import com.zuoyue.weiyang.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class UserServiceImpl implements UserService {

    @Autowired
   private UserDao userDao;

    @Override
    public int insert(User... users) {
        return userDao.insert(users);
    }

    @Override
    public List<User> list(User user) {
        return userDao.list(user);
    }

    @Override
    public User selectById(Long id) {
        return userDao.selectById(id);
    }

    @Override
    public User selectByUname(Long studentId) {
        return userDao.selectByUname(studentId);
    }

    @Override
    public User selectByUsername(String username) {
        return userDao.selectByUsername(username);
    }

    @Override
    public int updateByIds(User user, Long... ids) {
        return userDao.updateByIds(user,ids);
    }

    @Override
    public int deleteByIds(Long... ids) {
        return userDao.deleteByIds(ids);
    }
}
