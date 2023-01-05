package org.aliang.controller;

import org.aliang.param.AdminUserParam;
import org.aliang.pojo.AdminUser;
import org.aliang.service.AdminUserService;
import org.aliang.utils.R;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;


import javax.annotation.Resource;
import javax.servlet.http.HttpSession;

@RestController
@RequestMapping("/user")
public class AdminUserController {

    @Resource
    private AdminUserService adminUserService;

    @PostMapping("/login")
    public R login(@Validated AdminUserParam adminUserParam, BindingResult result, HttpSession session){
        //参数校验
        if (result.hasErrors()){
            return R.fail("参数异常，校验失败！");
        }
        //验证码校验
        String captcha = (String) session.getAttribute("captcha");
        String verCode = adminUserParam.getVerCode();
        if (!verCode.equals(captcha)){
            return R.fail("登录失败,验证码错误!");
        }
        //登录校验
        R r = adminUserService.login(adminUserParam);
        AdminUser adminUser = (AdminUser) r.getData();
        session.setAttribute("userInfo",adminUser);
        return r;
    }

    @GetMapping("/logout")
    public R logout(HttpSession session){
        //清空session数据
        session.invalidate();
        return R.ok("退出登录成功");
    }

}
