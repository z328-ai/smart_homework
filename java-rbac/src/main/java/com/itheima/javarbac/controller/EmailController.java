package com.itheima.javarbac.controller;

import com.itheima.javarbac.Service.EmailService;
import com.itheima.javarbac.pojo.Result;
import com.itheima.javarbac.util.SysLog;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;

@RestController
public class EmailController {
    @Autowired
    private EmailService emailService;


    @GetMapping("/email")
    @SysLog(operate = "用户发送邮箱请求获得密码")
    public Result sendEmail(String email, HttpServletRequest request){
        if(!StringUtils.hasText(email)){
           return Result.error("请输入邮箱");
        }
    return emailService.sendEmail(email,request);
    }
}
