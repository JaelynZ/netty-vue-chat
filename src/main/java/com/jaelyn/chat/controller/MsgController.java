package com.jaelyn.chat.controller;

import com.jaelyn.chat.common.dto.Result;
import com.jaelyn.chat.dto.getChatHistoryParam;
import com.jaelyn.chat.entity.GroupMessage;
import com.jaelyn.chat.entity.Message;
import com.jaelyn.chat.entity.User;
import com.jaelyn.chat.service.MsgService;
import com.jaelyn.chat.service.UserService;
import com.jaelyn.chat.utils.IpUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.List;

/**
 * @author jaelynz@gmail.com
 * @description TODO
 * @date 2018/12/19 11:07
 **/
@RestController
@RequestMapping("/msg")
public class MsgController {

    private static Logger LOGGER = LoggerFactory.getLogger(MsgController.class);

    @Autowired
    private MsgService msgService;

    @ResponseBody
    @RequestMapping(value = "/sendMsg", method = RequestMethod.POST)
    public void sendMsg(@RequestBody Message msg) {
        msgService.sendMsg(msg);
    }

    @ResponseBody
    @RequestMapping(value = "/sendGroupMsg", method = RequestMethod.POST)
    public void sendGroupMsg(@RequestBody GroupMessage msg) {
        msgService.sendGroupMsg(msg);
    }

    @ResponseBody
    @RequestMapping(value = "/getChatHistory", method = RequestMethod.POST)
    public Result getChatHistory(@RequestBody getChatHistoryParam param) {
        Result result = new Result();
        if(param.getChatType().equals(1)){
            List<Message> messages = msgService.getSingleChatHistory(param);
            result.setData(messages);
        }else {
            List<GroupMessage> groupMessages = msgService.getGroupChatHistory(param);
            result.setData(groupMessages);
        }

        result.setMsg("成功");
        result.setCode(0);
        return result;

    }

}
