package com.jaelyn.chat.service.impl;

import com.jaelyn.chat.dao.FriendDao;
import com.jaelyn.chat.dao.MsgDao;
import com.jaelyn.chat.dao.UserDao;
import com.jaelyn.chat.dto.getChatHistoryParam;
import com.jaelyn.chat.entity.GroupMessage;
import com.jaelyn.chat.entity.Message;
import com.jaelyn.chat.entity.User;
import com.jaelyn.chat.service.MsgService;
import com.jaelyn.chat.service.UserService;
import org.springframework.stereotype.Service;
import org.springframework.util.NumberUtils;

import javax.annotation.Resource;
import java.util.List;

/**
 * @author jaelynz@gmail.com
 * @description TODO
 * @date 2018/12/19 11:08
 **/
@Service
public class MsgServiceImpl implements MsgService {
    @Resource
    private MsgDao msgDao;

    @Resource
    private FriendDao friendDao;

    @Override
    public void sendMsg(Message msg) {
        //如果接收方为空，则发送方和接收方均为本人
        if(msg.getToUserId() == null){
            msg.setToUserId(msg.getFromUserId());
        }
        msgDao.sendMsg(msg);
        friendDao.updateFriendLastChatDate(msg);
    }

    @Override
    public void sendGroupMsg(GroupMessage msg){
        msgDao.sendGroupMsg(msg);
        friendDao.updateGroupLastChatDate(msg);
    }

    @Override
    public List<Message> getSingleChatHistory(getChatHistoryParam param){
        return msgDao.getSingleChatHistory(param);
    }

    @Override
    public List<GroupMessage> getGroupChatHistory(getChatHistoryParam param){
        return msgDao.getGroupChatHistory(param);
    }

}
