package com.itheima.javarbac.Service.impl;

import com.itheima.javarbac.Service.EmailService;
import com.itheima.javarbac.Service.UserService;
import com.itheima.javarbac.mapper.PermissionMapper;
import com.itheima.javarbac.mapper.UserMapper;
import com.itheima.javarbac.pojo.Result;
import com.itheima.javarbac.pojo.User;
import com.itheima.javarbac.util.JwtUtil;
import com.itheima.javarbac.util.RedisCache;
import org.jasypt.encryption.pbe.StandardPBEStringEncryptor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.servlet.http.HttpServletRequest;
import java.util.HashMap;

@Service
public class UserServiceImpl implements UserService {
    @Autowired
    private UserMapper userMapper;
    @Autowired
    private PermissionMapper permissionMapper;
    @Autowired
    private UserDetailsService userDetailsService;
    @Autowired
    private RedisCache redisCache;
    @Autowired
    private EmailService emailService;
    @Autowired
    private StandardPBEStringEncryptor standardPBEStringEncryptor;
    private static User userDetail;
    @Override
    public Result register(User user) {
        //如果数据库能查到账户，说明已注册
        if(userMapper.selectUser(user.getUsername())!=null){
            return Result.error("该账号已注册");
        }
        //如果没有，加密进行注册
        user.setPassword(standardPBEStringEncryptor.encrypt(user.getPassword()));
        Integer result = userMapper.insertUser(user);
        if(result==0){
            return Result.error("注册失败");
        }
        return Result.success(200,"注册成功");
    }

    @Override
    public Result login1(User user) {
        //userDetailsService中获取Details，
        userDetail = (User)userDetailsService.loadUserByUsername(user.getUsername());
        //将数据库密码解密
        String oldPassword = standardPBEStringEncryptor.decrypt(userDetail.getPassword());
        System.out.println(oldPassword);
        if(userDetail!=null){
            //如果用户输入的密码和数据的密码一致时，我们发送验证码进行邮箱验证
            if(oldPassword.equals(user.getPassword())){
              return emailService.getCode(userDetail.getEmail());
            }
            return Result.error("密码错误");
        }
        return Result.error("输入正确账号");
    }

    public Result login2(){
                userDetail.setPermissions(permissionMapper.selectUserMenu(userDetail.getId()));
//        通过解析userid获得token
                String token = JwtUtil.token(userDetail.getId());
                HashMap<String, Object> map = new HashMap<>();
                map.put("user",userDetail);
                map.put("token",token);
     //  userDatail放入redis
                redisCache.setCacheObject(userDetail.getId()+"",userDetail);
                return Result.success(200,"欢迎来到主页",map);
    }

    //  从数据库查出userDetail
    public User loadUserByUsername(String username){
         return userMapper.selectUser(username);
    }

    // 修改密码
    @Override
    @Transactional
    public Result updatePassword(String password){
        User user = (User) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
//        加密密码
        String encryptPassword = standardPBEStringEncryptor.encrypt(password);
        Integer integer = userMapper.updatePassword(encryptPassword,user.getId());
        if(integer==null){
            return Result.error("修改密码失败");
        }
        return Result.success(200,"修改成功");
    }

//    退出登录
    @Override
    public Result loginOut() {
        User user = (User) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        if(redisCache.deleteObject(user.getId() + "")){
            return Result.success(200,"退出成功");
        };
        return Result.error("注销失败");
    }
}
