package com.ljz.compilationVSM.utils;


import com.ljz.compilationVSM.entity.User;
import io.jsonwebtoken.*;
import io.jsonwebtoken.security.SignatureException;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;

public class TokenHandler {
    private static String secretKey="b496393b52d53cde55444edff330b896dfd3c0ca1713667347bcf2b8bb63872d";
    public static String getToken(User user){
//        JwtBuilder jwtBuilder = Jwts.builder()
//                // 设置jti
//                .setId(user.getId().toString())
//                // 设置用户sub
//                .setSubject(user.getUserName())
//                // 设置签发时间ita
//                .setIssuedAt(new Date())
//                // 设置token过期时间 1分钟
//                .setExpiration(new Date(System.currentTimeMillis()+1000*3600))
//                // 设置签名算法和盐
//                .signWith(SignatureAlgorithm.HS256,"mitsuha");


        Map<String,Object> claims =new HashMap<>();
        claims.put("id",user.getId());
        claims.put("name",user.getUserName());

        String jwt= Jwts.builder()
                .signWith(SignatureAlgorithm.HS256,secretKey)
                .setClaims(claims)
                .setExpiration(new Date(System.currentTimeMillis()+1000*3600))
                .compact();
        return jwt;
    }
    public static boolean verifyToken(String jwt) {
        try{
            //解析JWT字符串中的数据，并进行最基础的验证
            Jwts.parser()
                    .setSigningKey(secretKey)
                    .build()
                    .parseClaimsJws(jwt);    //jwt是JWT字符串
            return true;
        }
        catch (Exception e){
            return false;
        }
    }
}
