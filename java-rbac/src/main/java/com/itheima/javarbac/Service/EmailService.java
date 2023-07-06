package com.itheima.javarbac.Service;

import com.itheima.javarbac.pojo.Result;

import javax.servlet.http.HttpServletRequest;

public interface EmailService {
    public Result sendEmail(String email, HttpServletRequest request);

    public Result getCode(String email);
}
