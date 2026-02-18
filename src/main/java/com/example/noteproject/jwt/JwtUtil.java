package com.example.noteproject.jwt;

import io.jsonwebtoken.*;
import io.jsonwebtoken.security.Keys;
import io.jsonwebtoken.security.SignatureException;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Component;
import org.springframework.web.server.ResponseStatusException;

import java.nio.charset.StandardCharsets;
import java.security.Key;
import java.util.Date;

@Component
public class JwtUtil {
    private final Key secretKey;

    private final long accessTokenValiditySeconds = 3600000;

    private final long refreshTokenValiditySeconds = 604800000;

    public JwtUtil(@Value("${jwt.secret}")String secretKey) {
        this.secretKey = Keys.hmacShaKeyFor(secretKey.getBytes(StandardCharsets.UTF_8));
    }

    //엑세스 토큰 생성
    public String generateAccessToken(String username){
        Date now = new Date();
        Date expiration = new Date(now.getTime() + accessTokenValiditySeconds);

        return Jwts.builder()
                .setSubject(username)
                .claim("type","access")
                .setIssuedAt(now)
                .setExpiration(expiration)
                .signWith(secretKey, SignatureAlgorithm.HS256)
                .compact();
    }

    // 리프레쉬 토큰 생성
    public String generateRefreshToken(String username){
        Date now = new Date();
        Date expiration = new Date(now.getTime() + refreshTokenValiditySeconds);

        return Jwts.builder()
                .setSubject(username)
                .claim("type","refresh")
                .setIssuedAt(now)
                .setExpiration(expiration)
                .signWith(secretKey, SignatureAlgorithm.HS256)
                .compact();
    }

    public String getUsername(String token) {
        try {
            return Jwts.parserBuilder()
                    .setSigningKey(secretKey)
                    .build()
                    .parseClaimsJws(token)
                    .getBody()
                    .getSubject();
        } catch (ExpiredJwtException e) {
            throw new ResponseStatusException(HttpStatus.UNAUTHORIZED, "토큰 만료");
        } catch (MalformedJwtException | SignatureException | IllegalArgumentException e) {
            throw new ResponseStatusException(HttpStatus.UNAUTHORIZED, "유효하지 않은 토큰");
        }
    }

}
