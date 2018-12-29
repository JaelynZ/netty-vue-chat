package com.jaelyn.chat.dto;

/**
 * @author jingling.zhang@ucarinc.com
 * @description TODO
 * @date 2018/12/24 10:24
 **/
public class getChatHistoryParam {

    private Integer fromUserId;
    private Integer toUserId;

    private Integer groupId;

    private Integer chatType;

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

    public Integer getGroupId() {
        return groupId;
    }

    public void setGroupId(Integer groupId) {
        this.groupId = groupId;
    }

    public Integer getChatType() {
        return chatType;
    }

    public void setChatType(Integer chatType) {
        this.chatType = chatType;
    }
}
