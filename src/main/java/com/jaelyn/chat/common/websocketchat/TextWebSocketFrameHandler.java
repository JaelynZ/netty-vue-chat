package com.jaelyn.chat.common.websocketchat;


import com.alibaba.fastjson.JSON;
import com.google.common.collect.Maps;
import com.jaelyn.chat.common.dto.Msg;
import com.jaelyn.chat.entity.GroupMessage;
import com.jaelyn.chat.entity.Message;
import com.jaelyn.chat.entity.User;
import com.jaelyn.chat.utils.HttpUtil;
import io.netty.channel.Channel;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.SimpleChannelInboundHandler;
import io.netty.channel.group.ChannelGroup;
import io.netty.channel.group.DefaultChannelGroup;
import io.netty.handler.codec.http.websocketx.TextWebSocketFrame;
import io.netty.util.concurrent.GlobalEventExecutor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

/**
 * 处理TextWebSocketFrame
 *
 * @author waylau.com
 * 2015年3月26日
 */
@Service
public class TextWebSocketFrameHandler extends SimpleChannelInboundHandler<TextWebSocketFrame> {

    private static Logger LOGGER = LoggerFactory.getLogger(TextWebSocketFrameHandler.class);

    public static ChannelGroup channels = new DefaultChannelGroup(GlobalEventExecutor.INSTANCE);

    public static Map<Integer, Map<User, Channel>> userChatGroup = new ConcurrentHashMap<>();

    @Override
    protected void channelRead0(ChannelHandlerContext ctx,
                                TextWebSocketFrame msgText) throws Exception { // (1)

        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        String currentTime = dateFormat.format( new Date() );

        Channel incoming = ctx.channel();
        //解析用户信息和用户发送消息内容
        Msg msg = JSON.parseObject(msgText.text(), Msg.class);
        //绑定用户
        if (msg.getFlag().equals(1)) {
            if (!userChatGroup.containsKey(msg.getUser().getId())) {
                Map<User, Channel> map = Maps.newHashMap();
                map.put(msg.getUser(), incoming);
                userChatGroup.put(msg.getUser().getId(), map);
            }
        } else if (msg.getFlag().equals(2)) {
            userChatGroup.remove(msg.getUser().getId());
        } else if (msg.getFlag().equals(3)) {

        } else if (msg.getFlag().equals(4)) {

            if(msg.getChatType().equals(1)){
                //单人聊天

                //不管好友是否上线，均需要广播到用户自己的channel
                Map<User, Channel> user = userChatGroup.get(msg.getUser().getId());
                if(user != null){
                    //只有一个user和channel
                    for (Map.Entry<User, Channel> entry : user.entrySet()) {
                        Channel channel = entry.getValue();
                        channel.writeAndFlush(new TextWebSocketFrame(JSON.toJSONString(msg)));
                    }
                }

                //插入到数据库中
                Message message = new Message();
                message.setFromUserId(msg.getUser().getId());
                message.setToUserId(msg.getFriend().getId());
                message.setMessage(msg.getContent());
                HttpUtil.sendPost("http://localhost:7070/msg/sendMsg", message);

                //如果是自己和自己聊，不需要广播两次
                if(msg.getUser().getId().equals(msg.getFriend().getId())){
                    return;
                }

                //friend为空，表明好友没有上线，未注册channel
                Map<User, Channel> friend = userChatGroup.get(msg.getFriend().getId());
                if(friend != null){
                    //只有一个user和channel
                    for (Map.Entry<User, Channel> entry : friend.entrySet()) {
                        Channel channel = entry.getValue();
                        channel.writeAndFlush(new TextWebSocketFrame(JSON.toJSONString(msg)));
                    }
                }

            }else if(msg.getChatType().equals(2)){
                //群聊
                //插入到数据库中--实际群聊记录不能存储到该表中，应该另外一张表。
                GroupMessage groupMessage = new GroupMessage();
                groupMessage.setGroupId(msg.getGroupId());
                groupMessage.setUserId(msg.getUser().getId());
                groupMessage.setMessage(msg.getContent());
                HttpUtil.sendPost("http://localhost:7070/msg/sendGroupMsg", groupMessage);

                for(User friend : msg.getFriends()){
                    Map<User, Channel> friendInfo = userChatGroup.get(friend.getId());
                    if(friendInfo != null){
                        //只有一个user和channel
                        for (Map.Entry<User, Channel> entry : friendInfo.entrySet()) {
                            Channel channel = entry.getValue();
                            channel.writeAndFlush(new TextWebSocketFrame(JSON.toJSONString(msg)));
                        }
                    }
                }

            }
        }
    }

    @Override
    public void handlerAdded(ChannelHandlerContext ctx) throws Exception {  // (2)
        Channel incoming = ctx.channel();

//        channels.add(incoming);
        System.out.println("Client:" + incoming.remoteAddress() + "加入");
    }

    @Override
    public void handlerRemoved(ChannelHandlerContext ctx) throws Exception {  // (3)
        Channel incoming = ctx.channel();

        for (Map.Entry<Integer, Map<User, Channel>> entry : userChatGroup.entrySet()) {
            Integer userId = entry.getKey();
            Map<User, Channel> value = entry.getValue();

            //只有一个user和channel
            for (Map.Entry<User, Channel> entry1 : value.entrySet()) {
                Channel channel = entry1.getValue();

                if(incoming == channel){
                    userChatGroup.remove(userId);

                    //下线了，更新用户在线状态
                    User userInfo = new User();
                    userInfo.setId(userId);
                    userInfo.setIsOnline(0);
                    HttpUtil.sendPost("http://localhost:7070/user/updateOnlineInfo", userInfo);
                }
            }
        }


        System.out.println("Client:" + incoming.remoteAddress() + "离开");

        // A closed Channel is automatically removed from ChannelGroup,
        // so there is no need to do "channels.remove(ctx.channel());"
    }

    @Override
    public void channelActive(ChannelHandlerContext ctx) throws Exception { // (5)
        Channel incoming = ctx.channel();
        System.out.println("Client:" + incoming.remoteAddress() + "在线");
    }

    @Override
    public void channelInactive(ChannelHandlerContext ctx) throws Exception { // (6)
        Channel incoming = ctx.channel();
        System.out.println("Client:" + incoming.remoteAddress() + "掉线");
    }

    @Override
    public void exceptionCaught(ChannelHandlerContext ctx, Throwable cause)    // (7)
            throws Exception {
        Channel incoming = ctx.channel();
        System.out.println("Client:" + incoming.remoteAddress() + "异常");
        // 当出现异常就关闭连接
        cause.printStackTrace();
        ctx.close();
    }

}
