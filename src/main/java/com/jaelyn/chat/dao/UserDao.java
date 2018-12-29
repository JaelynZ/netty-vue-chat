package com.jaelyn.chat.dao;

import com.jaelyn.chat.entity.User;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;
import java.util.Map;

/**
 * @author jingling.zhang@ucarinc.com
 * @description TODO
 * @date 2018/12/19 11:08
 **/
@Mapper
public interface UserDao {
    int updateName(User user);

    List<User> findAllUser();

    User login(User user);

    void updateUserLoginInfo(User user);

    User getUser(User user);

    void updateOnlineInfo(User user);
}
