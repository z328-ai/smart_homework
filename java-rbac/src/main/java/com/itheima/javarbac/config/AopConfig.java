package com.itheima.javarbac.config;

import com.itheima.javarbac.mapper.LogMapper;
import com.itheima.javarbac.pojo.User;
import com.itheima.javarbac.util.SysLog;

import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.Signature;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.aspectj.lang.annotation.Pointcut;
import org.aspectj.lang.reflect.MethodSignature;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;

import java.lang.reflect.Method;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

@Component
@Aspect
public class AopConfig {
    @Autowired
    private LogMapper logMapper;
   @Around("execution(public * com.itheima.javarbac.controller.*.*(..))")
    public Object log(ProceedingJoinPoint p){
       Object o = new Object();
       MethodSignature signature =(MethodSignature)p.getSignature();
       Method method = signature.getMethod();
       SysLog annotation = method.getAnnotation(SysLog.class);
       String operate = annotation.operate();
       Object principal = SecurityContextHolder.getContext().getAuthentication().getPrincipal();
       com.itheima.javarbac.pojo.SysLog sysLog = new com.itheima.javarbac.pojo.SysLog();
           if(principal instanceof User){
               User user = (User)principal;
               sysLog.setUsername(user.getName());
               sysLog.setRole(user.getRole());
               sysLog.setOperate(operate);
           }else{
                   sysLog.setUsername("无账号用户");
                   sysLog.setOperate(operate);
                   sysLog.setRole("游客");
           }

       long beginTime = System.currentTimeMillis();
       try {
         o = p.proceed();
       } catch (Throwable throwable) {
           throwable.printStackTrace();
       }
       long time = System.currentTimeMillis()-beginTime;
       DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy年MM月dd日 HH时mm分");
       String format = formatter.format(LocalDateTime.now());
       sysLog.setSpendTime(time);
       sysLog.setCreateTime(format);
       logMapper.insertLog(sysLog);
       System.out.println("切点");
       return o;
   }
}
