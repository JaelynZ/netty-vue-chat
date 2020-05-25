package com.jaelyn.chat.service;

import com.jaelyn.chat.entity.FriendGroup;
import com.jaelyn.chat.entity.Message;
import com.jaelyn.chat.entity.User;

import java.util.List;

/**
 * @author jaelynz@gmail.com
 * @description TODO
 * @date 2018/12/19 11:07
 **/
public interface FriendService {

    List<User> getFriendListByUserId(Integer userId);

    List<FriendGroup> getFriendGroupListByUserId(Integer userId);

    List<User> getUserListByGroupId(Integer groupId);
}
