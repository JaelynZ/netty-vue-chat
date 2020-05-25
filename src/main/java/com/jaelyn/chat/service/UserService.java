package com.jaelyn.chat.service;

import com.jaelyn.chat.entity.User;

import java.util.List;

/**
 * @author jaelynz@gmail.com
 * @description TODO
 * @date 2018/12/19 11:07
 **/
public interface UserService {

    void updateName();

    List<User> findAllUser();

    User login(User user);

    void updateUserLoginInfo(User user);

    User getUser(User user);

    void updateOnlineInfo(User user);
}
