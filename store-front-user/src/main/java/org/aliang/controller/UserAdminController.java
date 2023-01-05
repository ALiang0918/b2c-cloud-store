package org.aliang.controller;

import org.aliang.param.CartListParam;
import org.aliang.param.PageParam;
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
public class UserAdminController {

    @Autowired
    private UserService userService;

    @PostMapping("/admin/list")
    public R listPage(@RequestBody PageParam pageParam){
        return userService.listPage(pageParam);
    }

    @PostMapping("/admin/remove")
    public R removeById(@RequestBody CartListParam cartListParam){
        return userService.removeById(cartListParam.getUserId());
    }

    @PostMapping("/admin/update")
    public R update(@RequestBody User user){
        return userService.update(user);
    }

    @PostMapping("/admin/save")
    public R save(@RequestBody User user){
        return userService.saveUser(user);
    }
}
