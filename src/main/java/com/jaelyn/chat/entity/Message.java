package com.jaelyn.chat.entity;

import java.util.Date;

/**
 * @author jingling.zhang@ucarinc.com
 * @description TODO
 * @date 2018/12/20 15:47
 **/
public class Message {
    private Integer id;
    private Integer fromUserId;
    private Integer toUserId;
    private String createDate;
    private String message;
    /**
     * 发送人的呢称
     */
    private String nickName;
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

    public Integer getFromUserId() {
        return fromUserId;
    }

    public void setFromUserId(Integer fromUserId) {
        this.fromUserId = fromUserId;
    }

    public Integer getToUserId() {
        return toUserId;
    }

    public void setToUserId(Integer toUserId) {
        this.toUserId = toUserId;
    }

    public String getCreateDate() {
        return createDate;
    }

    public void setCreateDate(String createDate) {
        this.createDate = createDate;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public String getNickName() {
        return nickName;
    }

    public void setNickName(String nickName) {
        this.nickName = nickName;
    }

    public String getHeadIcon() {
        return headIcon;
    }

    public void setHeadIcon(String headIcon) {
        this.headIcon = headIcon;
    }
}
