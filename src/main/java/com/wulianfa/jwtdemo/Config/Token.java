package com.wulianfa.jwtdemo.Config;

import com.auth0.jwt.JWT;
import com.auth0.jwt.algorithms.Algorithm;
import com.wulianfa.jwtdemo.Entity.User;

/**
 * @Author wulianfa
 * @Description 生成Token方式
 * @Date 23:35 2019/6/20
 */
public class Token {
    public static String getToken(User user) {
        String token = "";
        //withAudience:携带信息 sign:签名方式（密钥是用户密码）
        token = JWT.create().withAudience(user.getId()).sign(Algorithm.HMAC256(user.getPassword()));
        
        return token;
    }
}
