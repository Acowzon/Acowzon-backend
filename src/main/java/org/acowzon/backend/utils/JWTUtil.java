package org.acowzon.backend.utils;

import com.auth0.jwt.JWT;
import com.auth0.jwt.algorithms.Algorithm;
import com.auth0.jwt.interfaces.DecodedJWT;

import java.util.Calendar;

public class JWTUtil {
    // 私钥
    private static final String TOKEN = "$d!mSmKeBGpJGXL30GqAqbpT4bxxZV2d";
    // 过期天数
    private static final int DAYS = 7;

    // 创建一个token
    public static String createToken(String userId, String username){
        // 生成过期时间
        Calendar instance = Calendar.getInstance();
        instance.add(Calendar.DATE, DAYS);
        // 生成token
        String token = JWT.create()
                .withClaim("userId", userId) // 设置payload
                .withClaim("username", username)
                .withExpiresAt(instance.getTime())  // 设置过期时间
                .sign(Algorithm.HMAC256(TOKEN));    // 设置签名

        return token;
    }

    // 验证一个token是否合法,不合法会直接抛出异常
    public static DecodedJWT verify(String token){
        return JWT.require(Algorithm.HMAC256(TOKEN)).build().verify(token);
    }
}
