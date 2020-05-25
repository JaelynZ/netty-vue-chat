package com.jaelyn.chat.service.impl;

import com.jaelyn.chat.dao.FriendDao;
import com.jaelyn.chat.entity.FriendGroup;
import com.jaelyn.chat.entity.User;
import com.jaelyn.chat.service.FriendService;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;

/**
 * @author jaelynz@gmail.com
 * @description TODO
 * @date 2018/12/19 11:08
 **/
@Service
public class FriendServiceImpl implements FriendService {
    @Resource
    private FriendDao friendDao;

    @Override
    public List<User> getFriendListByUserId(Integer userId) {
        return friendDao.getFriendListByUserId(userId);
    }

    @Override
    public List<FriendGroup> getFriendGroupListByUserId(Integer userId) {
        return friendDao.getFriendGroupListByUserId(userId);
    }

    @Override
    public List<User> getUserListByGroupId(Integer groupId) {
        return friendDao.getUserListByGroupId(groupId);
    }

}
