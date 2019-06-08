package com.mall.webapi.auth;

import com.auth0.jwt.JWT;
import com.auth0.jwt.JWTVerifier;
import com.auth0.jwt.algorithms.Algorithm;
import com.auth0.jwt.exceptions.JWTCreationException;
import com.auth0.jwt.exceptions.JWTVerificationException;
import com.auth0.jwt.interfaces.Claim;
import com.auth0.jwt.interfaces.DecodedJWT;
import com.java.utils.date.DateUtils;
import lombok.extern.slf4j.Slf4j;

import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * JWT工具类
 */
@Slf4j
public class JWTUtil {

    private String SECRET = "secret";
    private int expireTime = 2;//两小时

    private Algorithm algorithm = Algorithm.HMAC256(SECRET);

    private String LOGIN_NAME_KEY = "LOGIN_NAME";
    private String ISSUER = "SERVICE";
    private String SUBJECT = "this is test token";
    private String AUDIENCE = "APP";

    /**
     * 生成token
     * @return
     */
    public String createToken(String openId){
        String token = null;
        try {
            //头部信息
            Map<String,Object> header = new HashMap<String,Object>();
            header.put("alg","HS256");
            header.put("typ","JWT");

            Date now = new Date();
            Date expireDate = DateUtils.getAfterDate(now,0,0,0,expireTime,0,0);

            token= JWT.create()
                    .withHeader(header)//设置头部信息 Header
                    .withClaim(LOGIN_NAME_KEY,openId)//设置 载荷 Payload
                    .withIssuer(ISSUER)//签名是由谁生成 例如 服务器
                    .withSubject(SUBJECT)//签名的主题
                    .withAudience(AUDIENCE)//签名的观众 也可以理解谁接受签名的
                    .withIssuedAt(now)//生成签名的时间
                    .withExpiresAt(expireDate)//签名过期的时间
                    .sign(algorithm);
        }catch (JWTCreationException e){
            log.error("生成jwt token异常",e);
        }
        System.out.println("generate token:{}"+token);
        return token;
    }


    public void verfyToken(String token){
        try {
            JWTVerifier verifier = JWT.require(algorithm)
                    .withIssuer(ISSUER)
                    .build();
            DecodedJWT jwt = verifier.verify(token);
            String subject = jwt.getSubject();
            Map<String,Claim> claims = jwt.getClaims();
            Claim claim = claims.get(LOGIN_NAME_KEY);
            List<String> audience = jwt.getAudience();
//            log.warn("subject:{}",subject);
//            log.warn("jwt token claim:{}",claim.asString());
//            log.warn("audience:{}",audience.toString());
            System.out.println("subject:{}"+subject);
            System.out.println("jwt token claim:{}"+claim.asString());
            System.out.println("audience:{}"+audience.toString());
        }catch (JWTVerificationException e){
            log.error("校验jwt token异常",e);
        }
    }


    public static void main(String[] args) {
//        JWTUtil jwtUtil = new JWTUtil();
//        String token = jwtUtil.createToken();
//        jwtUtil.verfyToken(token);
    }


}
