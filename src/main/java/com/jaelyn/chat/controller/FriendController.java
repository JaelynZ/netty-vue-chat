package com.jaelyn.chat.controller;

import com.jaelyn.chat.common.dto.Result;
import com.jaelyn.chat.entity.FriendGroup;
import com.jaelyn.chat.entity.Message;
import com.jaelyn.chat.entity.User;
import com.jaelyn.chat.service.FriendService;
import com.jaelyn.chat.service.MsgService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * @author jingling.zhang@ucarinc.com
 * @description TODO
 * @date 2018/12/19 11:07
 **/
@RestController
@RequestMapping("/friend")
public class FriendController {

    private static Logger LOGGER = LoggerFactory.getLogger(FriendController.class);

    @Autowired
    private FriendService friendService;

    @ResponseBody
    @RequestMapping(value = "/getFriendListByUserId", method = RequestMethod.POST)
    public Result getFriendListByUserId(@RequestBody User user) {
        Result result = new Result();
        List<User> friends = friendService.getFriendListByUserId(user.getId());

        result.setData(friends);
        result.setMsg("成功");
        result.setCode(0);
        return result;
    }

    @ResponseBody
    @RequestMapping(value = "/getFriendGroupListByUserId", method = RequestMethod.POST)
    public Result getFriendGroupListByUserId(@RequestBody User user) {
        Result result = new Result();
        List<FriendGroup> groups = friendService.getFriendGroupListByUserId(user.getId());

        result.setData(groups);
        result.setMsg("成功");
        result.setCode(0);
        return result;
    }

    @ResponseBody
    @RequestMapping(value = "/getUserListByGroupId", method = RequestMethod.POST)
    public Result getUserListByGroupId(@RequestBody FriendGroup group) {
        Result result = new Result();
        List<User> users = friendService.getUserListByGroupId(group.getId());

        result.setData(users);
        result.setMsg("成功");
        result.setCode(0);
        return result;
    }

}
