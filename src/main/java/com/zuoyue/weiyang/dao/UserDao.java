package com.zuoyue.weiyang.dao;

import com.zuoyue.weiyang.bean.User;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface UserDao {

    int insert (User...users);

    List<User> list(User user);

    User selectById(Long id);

    User selectByIdMb(Long user_id);

    User selectByUname(Long studentId);

    User selectByUsername(String username);

    int updateByIds(@Param("param") User user,@Param("ids")Long ...ids);

    int deleteByIds(Long... ids);

}
