package com.jaelyn.chat.dao;

import com.jaelyn.chat.dto.getChatHistoryParam;
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
public interface MsgDao {
    int sendMsg(Message msg);

    int sendGroupMsg(GroupMessage msg);

    List<Message> getSingleChatHistory(getChatHistoryParam param);

    List<GroupMessage> getGroupChatHistory(getChatHistoryParam param);
}
