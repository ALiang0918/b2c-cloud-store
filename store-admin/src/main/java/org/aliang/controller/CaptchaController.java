package org.aliang.controller;

import com.wf.captcha.utils.CaptchaUtil;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@Controller
@RequestMapping()
public class CaptchaController {

    @GetMapping("/captcha")
    @ResponseBody
    public void captcha(HttpServletRequest request, HttpServletResponse response){
        try {
            //默认四个字符长度的验证码
            //默认放入session key = captcha 的位置
            CaptchaUtil.out(request,response);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
}
