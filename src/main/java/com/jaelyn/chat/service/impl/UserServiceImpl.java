package com.jaelyn.chat.service.impl;

import com.jaelyn.chat.dao.UserDao;
import com.jaelyn.chat.entity.User;
import com.jaelyn.chat.service.UserService;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
import java.util.List;

/**
 * @author jingling.zhang@ucarinc.com
 * @description TODO
 * @date 2018/12/19 11:08
 **/
@Service
public class UserServiceImpl implements UserService {
    @Resource
    private UserDao userDao;

    @Override
    public void updateName() {
        User user = new User();
        user.setId(1);
        user.setNickName("长大了");
        userDao.updateName(user);
    }

    @Override
    public List<User> findAllUser() {
        return userDao.findAllUser();
    }

    @Override
    public User login(User user){
        return userDao.login(user);
    }

    @Override
    public void updateUserLoginInfo(User user){
        userDao.updateUserLoginInfo(user);
    }

    @Override
    public User getUser(User user){
        return userDao.getUser(user);
    }

    @Override
    public void updateOnlineInfo(User user){
        userDao.updateOnlineInfo(user);
    }
}
