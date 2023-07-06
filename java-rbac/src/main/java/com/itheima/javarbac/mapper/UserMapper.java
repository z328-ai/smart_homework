package com.itheima.javarbac.mapper;

import com.itheima.javarbac.pojo.User;
import org.apache.ibatis.annotations.Param;

public interface UserMapper {
    public abstract User selectUser(String username);

    public abstract Integer insertUser(User user);

    public abstract Integer updatePassword(@Param("password") String password,@Param("uid") Integer uid);
}
