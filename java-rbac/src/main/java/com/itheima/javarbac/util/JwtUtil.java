package com.itheima.javarbac.util;


import com.auth0.jwt.JWT;
import com.auth0.jwt.JWTVerifier;
import com.auth0.jwt.algorithms.Algorithm;
import com.auth0.jwt.exceptions.JWTVerificationException;
import com.auth0.jwt.interfaces.DecodedJWT;

import java.io.UnsupportedEncodingException;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

public class JwtUtil {
    //设置过期时间
    private static final long EXPIRE_DATE = 30 * 60 * 100000;
    //token秘钥
    private static final String TOKEN_SECRET = "SmTicket";

    /**
     * 加密Token
     *
     * @Author: RZH
     * @Date: 2020/4/6 9:25
     */
    public static String token(Integer userId) {
        String token;
        try {
            //过期时间
            Date date = new Date(System.currentTimeMillis() + EXPIRE_DATE);
            //秘钥及加密算法
            Algorithm algorithm = Algorithm.HMAC256(TOKEN_SECRET);
            //设置头部信息
            Map<String, Object> header = new HashMap<>();
            header.put("typ", "JWT");
            header.put("alg", "HS256");
            //携带UserId信息，生成签名
            token = JWT.create()
                    .withHeader(header)
                    .withClaim("userId", userId)
                    .withExpiresAt(date)
                    .sign(algorithm);
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
        return token;
    }

    /**
     * JWT解密
     *
     * @Author: RZH
     * @Date: 2020/4/6 9:30
     */
    public static Integer decodeToken(final String token) {
        Integer userId = null;
        try {
            JWTVerifier verifier = null;
            try {
                verifier = JWT.require(Algorithm.HMAC256(TOKEN_SECRET))
                        .build();
            } catch (UnsupportedEncodingException e) {
                e.printStackTrace();
            }
            DecodedJWT jwt = verifier.verify(token);
            if (jwt != null) {
                userId = Integer.valueOf(jwt.getClaim("userId").asInt());
            }
        } catch (JWTVerificationException exception) {
            exception.printStackTrace();
        } catch (IllegalArgumentException e) {
            e.printStackTrace();
        }
        return userId;
    }

    public static void main(String[] args) {
        String zzg = token(9);
        System.out.println(decodeToken(zzg));
    }
}
