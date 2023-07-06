package com.itheima.javarbac.Service;

import com.itheima.javarbac.pojo.Result;
import com.itheima.javarbac.pojo.User;

import javax.servlet.http.HttpServletRequest;

public interface UserService {
    public Result register(User user);

    public Result login1(User user);

    public User  loadUserByUsername(String username);

    public Result login2();

    public Result updatePassword(String password);

    public Result loginOut();
}
