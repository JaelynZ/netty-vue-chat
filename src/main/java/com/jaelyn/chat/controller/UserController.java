package com.jaelyn.chat.controller;

import com.jaelyn.chat.common.dto.Result;
import com.jaelyn.chat.entity.User;
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
@RequestMapping("/user")
public class UserController {

    private static Logger LOGGER = LoggerFactory.getLogger(UserController.class);

    @Autowired
    private UserService userService;

    @ResponseBody
    @RequestMapping(value = "/updateName", method = RequestMethod.POST)
    public String updateName() {
        userService.updateName();
        return "updateName";
    }

    @ResponseBody
    @RequestMapping(value = "/findAllUser", method = RequestMethod.POST)
    public Result findAllUser() {
        Result result = new Result();
        List<User> users = userService.findAllUser();

        result.setData(users);
        result.setMsg("成功");
        result.setCode(0);
        return result;
    }

    @ResponseBody
    @RequestMapping(value = "/login", method = RequestMethod.POST)
    public Result login(HttpServletRequest request, HttpServletResponse response, @RequestBody User user) {
        Result result = new Result();

        User userData = userService.login(user);
        if (userData != null) {
            //效验成功
            //获取IP地址
            String ipAddress = IpUtil.getIpAddr(request);
            userData.setLastLoginIp(ipAddress);
            //更新登录信息
            userService.updateUserLoginInfo(userData);

            result.setData(userService.getUser(userData));
            result.setMsg("成功");
            result.setCode(0);
        }else {
            result.setMsg("账号密码错误");
            result.setCode(1);
        }

        return result;
    }

    @ResponseBody
    @RequestMapping(value = "/updateOnlineInfo", method = RequestMethod.POST)
    public Result updateOnlineInfo(HttpServletRequest request, HttpServletResponse response, @RequestBody User user) {
        Result result = new Result();

        userService.updateOnlineInfo(user);
        result.setMsg("成功");
        result.setCode(0);

        return result;
    }


}
