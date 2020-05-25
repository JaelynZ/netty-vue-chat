package com.jaelyn.chat.entity;

/**
 * @author jaelynz@gmail.com
 * @description TODO
 * @date 2018/12/27 17:26
 **/
public class GroupMessage {
    private Integer id;
    private Integer groupId;
    private Integer userId;
    /**
     * 发送方呢称
     */
    private String nickName;
    private String createDate;
    private String message;
    /**
     * 发送方头像
     */
    private String headIcon;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Integer getGroupId() {
        return groupId;
    }

    public void setGroupId(Integer groupId) {
        this.groupId = groupId;
    }

    public Integer getUserId() {
        return userId;
    }

    public void setUserId(Integer userId) {
        this.userId = userId;
    }

    public String getCreateDate() {
        return createDate;
    }

    public void setCreateDate(String createDate) {
        this.createDate = createDate;
    }

    public String getNickName() {
        return nickName;
    }

    public void setNickName(String nickName) {
        this.nickName = nickName;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public String getHeadIcon() {
        return headIcon;
    }

    public void setHeadIcon(String headIcon) {
        this.headIcon = headIcon;
    }
}
