package com.jaelyn.chat.service;

import com.jaelyn.chat.dto.getChatHistoryParam;
import com.jaelyn.chat.entity.GroupMessage;
import com.jaelyn.chat.entity.Message;
import com.jaelyn.chat.entity.User;

import java.util.List;

/**
 * @author jaelynz@gmail.com
 * @description TODO
 * @date 2018/12/19 11:07
 **/
public interface MsgService {

    void sendMsg(Message msg);

    void sendGroupMsg(GroupMessage msg);

    List<Message> getSingleChatHistory(getChatHistoryParam param);

    List<GroupMessage> getGroupChatHistory(getChatHistoryParam param);
}
