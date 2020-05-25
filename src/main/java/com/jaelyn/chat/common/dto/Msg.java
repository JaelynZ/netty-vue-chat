package com.jaelyn.chat.common.dto;

import com.jaelyn.chat.entity.User;

import java.util.List;

/**
 * @author jaelynz@gmail.com
 * @description TODO
 * @date 2018/12/20 15:35
 **/
public class Msg {
    /**
     * 用户
     */
    private User user;
    /**
     * 聊天内容
     */
    private String content;
    /**
     * 好友
     */
    private User friend;
    /**
     * 好友组
     */
    private List<User> friends;
    /**
     * 1连接，2关闭，3接收消息，4发送消息
     */
    private Integer flag;
    /**
     * 聊天类别：1单人聊天，2群聊
     */
    private Integer chatType;

    private Integer groupId;

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public Integer getFlag() {
        return flag;
    }

    public void setFlag(Integer flag) {
        this.flag = flag;
    }

    public User getFriend() {
        return friend;
    }

    public void setFriend(User friend) {
        this.friend = friend;
    }

    public List<User> getFriends() {
        return friends;
    }

    public void setFriends(List<User> friends) {
        this.friends = friends;
    }

    public Integer getChatType() {
        return chatType;
    }

    public void setChatType(Integer chatType) {
        this.chatType = chatType;
    }

    public Integer getGroupId() {
        return groupId;
    }

    public void setGroupId(Integer groupId) {
        this.groupId = groupId;
    }
}
