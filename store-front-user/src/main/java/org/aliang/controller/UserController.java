package org.aliang.controller;

import org.aliang.param.UserCheckParam;
import org.aliang.param.UserLoginParam;
import org.aliang.pojo.User;
import org.aliang.service.UserService;
import org.aliang.utils.R;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/user")
public class UserController {

    @Autowired
    private UserService userService;

    @PostMapping("/check")
    public R check(@RequestBody UserCheckParam userCheckParam){
        return userService.check(userCheckParam);
    }

    @PostMapping("/register")
    public R register(@RequestBody User user){
        return userService.register(user);
    }

    @PostMapping("/login")
    public R login(@RequestBody UserLoginParam userLoginParam){
        return userService.login(userLoginParam);
    }
}
