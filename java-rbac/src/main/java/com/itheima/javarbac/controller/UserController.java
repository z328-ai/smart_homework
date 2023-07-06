package com.itheima.javarbac.controller;

import com.itheima.javarbac.Service.UserService;
import com.itheima.javarbac.pojo.Result;
import com.itheima.javarbac.pojo.User;
import com.itheima.javarbac.util.SysLog;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;

@RestController
public class UserController {
    @Autowired
    private UserService userService;

    @PostMapping("/register")
    @SysLog(operate = "进行注册")
    public Result register(@RequestBody User user){
        return userService.register(user);
    }

    @PostMapping("/login1")
    @SysLog(operate = "进行登录获取验证码")
    public Result login(@RequestBody User user){
        return userService.login1(user);
    }

    @GetMapping("/login2")
    @SysLog(operate = "进行验证")
    public Result login1(){
        return userService.login2();
    }

    @PutMapping("/updatePassword")
    @SysLog(operate = "修改密码")
    public Result updatePassword(String password){
        System.out.println(password);
        return userService.updatePassword(password);
    }

    @GetMapping("/loginOut")
    @SysLog(operate = "注销")
    public Result loginOut(){
        return userService.loginOut();
    }
}
