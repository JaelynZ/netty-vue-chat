package com.jaelyn.chat.dao;

import com.jaelyn.chat.entity.FriendGroup;
import com.jaelyn.chat.entity.GroupMessage;
import com.jaelyn.chat.entity.Message;
import com.jaelyn.chat.entity.User;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

/**
 * @author jaelynz@gmail.com
 * @description TODO
 * @date 2018/12/19 11:08
 **/
@Mapper
public interface FriendDao {
    List<User> getFriendListByUserId(Integer userId);

    void updateFriendLastChatDate(Message msg);

    void updateGroupLastChatDate(GroupMessage msg);

    List<FriendGroup> getFriendGroupListByUserId(Integer userId);

    List<User> getUserListByGroupId(Integer groupId);
}
