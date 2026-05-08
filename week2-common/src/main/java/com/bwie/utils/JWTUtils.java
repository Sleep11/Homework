package com.bwie.utils;

import io.jsonwebtoken.*;

import javax.servlet.http.HttpServletRequest;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

public class JWTUtils {

    /**
     * 密钥，建议从配置文件或环境变量中读取，以提高安全性
     */
    static String secretKey = "bw1234567890";
    /**
     * 密钥，建议从配置文件或环境变量中读取，以提高安全性
     */
    static byte[] key = secretKey.getBytes();

    /**
     * 生成包含用户名的 JWT
     * 
     * @param id 用户名
     * @param minutes  令牌有效期，单位：分钟
     * @return 生成的 JWT 字符串
     */
    public static String createToken(String id, int minutes) {
        // 当前时间
        Date now = new Date();
        // 计算令牌过期时间
        Date expiryDate = new Date(now.getTime() + minutes * 60 * 1000);
              // 创建一个包含用户名的声明（claim）
        Map<String, Object> claims = new HashMap<>();
        claims.put("id", id);

        // 构建并返回 JWT 字符串
        return Jwts.builder()
                .setClaims(claims) // 设置声明
                .setIssuedAt(now) // 设置签发时间
                .setExpiration(expiryDate) // 设置过期时间
                .signWith(SignatureAlgorithm.HS256, key) // 使用 HS256 算法和密钥进行签名
                .compact();//压缩和编码，生成最终的 JWT 令牌
    }

    /**
     * 验证 JWT 是否有效
     * 
     * @param token 待验证的 JWT 字符串
     * @return 如果有效返回 true，否则返回 false
     */
    public static boolean valid(String token) {
        try {
            // 解析并验证 JWT，如果解析失败或验证失败，将抛出异常
            Jws<Claims> jws = Jwts.parser().setSigningKey(key).parseClaimsJws(token);
            return true;
        } catch (JwtException | IllegalArgumentException e) {
            // 解析或验证失败，返回 false
            return false; 
        }
    }

    /**
     * 从 JWT 中获取用户id
     * 
     * @param token JWT 字符串
     * @return 提取的用户名，如果 JWT 无效或不包含用户名，返回 null
     */
    public static String getUserId(String token) {
        try {
            if(null ==token){
                // 解析失败，返回 null
                return null;
            }
            // 解析 JWT 并获取声明（claims）
            Claims claims = Jwts.parser().
                    setSigningKey(key).
                    parseClaimsJws(token).
                    getBody();
            // 从声明中提取用户名
            return claims.get("id", String.class);
        } catch (JwtException e) {
            e.printStackTrace();
            // 解析失败，返回 null
            return null;
        }
    }

    /**
     * 从 HttpServletRequest 中获取用户名
     * @param request
     * @return
     */
    public static String getUserIdFromRequest(HttpServletRequest request) {
        String token = request.getHeader("token");
        return getUserId(token);
    }
}
