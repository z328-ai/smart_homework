package com.itheima.javarbac.config;

import com.alibaba.fastjson.JSON;
import com.itheima.javarbac.pojo.Result;
import com.itheima.javarbac.pojo.User;
import com.itheima.javarbac.util.JwtUtil;
import com.itheima.javarbac.util.RedisCache;
import com.itheima.javarbac.util.WebUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;
import org.springframework.web.filter.OncePerRequestFilter;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.Objects;

@Component
public class JwtAuthenticationTokenFilter extends OncePerRequestFilter {

    @Autowired
    private RedisCache redisCache;
    @Value("${jwt.tokenHeader}")
    private String tokenHeader;


    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException, IOException, ServletException {
        //获取响应头value值
        String token = request.getHeader(tokenHeader);
        System.out.println("接收到token:"+token);
        /**
         * 请求头值为空文本或为null说明时登录请求直接放行
         */
        if (!StringUtils.hasText(token)) {
           filterChain.doFilter(request,response);
            System.out.println("aa");
            return;
        }
        /**
        /** 解析token得到用户名，当解析非法token
         */
       Integer id = null;
        try {
         id = JwtUtil.decodeToken(token);
        } catch (Exception e) {
            Result result = Result.error("你传了非法token");
            result.setStatus(401);
            String json = JSON.toJSONString(result);
            WebUtils.renderString(response,json);
            return;
        }

        //从redis中获取用户信息
        String redisKey = id+"";
        User user = redisCache.getCacheObject(redisKey);
        System.out.println("user"+user);
        /**
         * 如果得到userDetail为空说明未登录获得权限
         */
        if(Objects.isNull(user)){
            Result result = Result.error("对不起你未登录获得权限！");
            result.setStatus(401);
            String json = JSON.toJSONString(result);
            WebUtils.renderString(response,json);
            return;
        }
        //存入SecurityContextHolder，在鉴证过滤器会拿出令牌查看是否被认证，这里user是默认被认证的所以令牌也是默认被认证
        UsernamePasswordAuthenticationToken authenticationToken =
                new UsernamePasswordAuthenticationToken(user,null,user.getAuthorities());
        SecurityContextHolder.getContext().setAuthentication(authenticationToken);
        //放行
        filterChain.doFilter(request, response);
    }
}