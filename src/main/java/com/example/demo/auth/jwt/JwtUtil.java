package com.example.demo.auth.jwt;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.JwtBuilder;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.impl.DefaultJws;
import io.jsonwebtoken.security.Keys;
import java.util.Base64;
import javax.crypto.SecretKey;
import lombok.experimental.UtilityClass;

@UtilityClass
public class JwtUtil {
    public static SecretKey secretKey() {
        byte[] bytes = Base64.getDecoder().decode("NwDDyzuXUkReFuwPyMjb3r6aDQAoj63Yu2FjiJjS3wFbvjK7");
        return Keys.hmacShaKeyFor(bytes);
    }

    public static JwtBuilder builder() {
        return Jwts.builder().signWith(JwtUtil.secretKey());
    }

    @SuppressWarnings("unchecked")
    public static DefaultJws<Claims> parse(String token) {
        return (DefaultJws<Claims>) Jwts.parser()
            .verifyWith(JwtUtil.secretKey()).build()
            .parse(token);
    }
}
