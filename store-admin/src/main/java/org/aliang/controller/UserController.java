package org.aliang.controller;

import org.aliang.param.CartListParam;
import org.aliang.param.PageParam;
import org.aliang.pojo.User;
import org.aliang.service.UserService;
import org.aliang.utils.R;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;

@RestController
@RequestMapping("/user")
public class UserController {

    @Resource
    private UserService userService;

    @GetMapping("/list")
    public R userList(PageParam pageParam){
        return userService.userList(pageParam);
    }

    @PostMapping("/remove")
    public R userRemove(CartListParam cartListParam){
        return userService.userRemove(cartListParam);
    }

    @PostMapping("/update")
    public R updateUserInfo(User user){
        return userService.updateUserInfo(user);
    }

    @PostMapping("/save")
    public R save(User user){
        return userService.save(user);
    }
}
