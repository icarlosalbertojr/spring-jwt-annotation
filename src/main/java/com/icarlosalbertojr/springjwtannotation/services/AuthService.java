package com.icarlosalbertojr.springjwtannotation.services;

import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.util.Date;

@Service
@Slf4j
public class AuthService {
    @Value("${jwt.expiration}")
    private String JWT_EXPIRATION;
    @Value("${jwt.secret}")
    private String JWT_SECRET;

    public String authenticate(String email, String password) {
        String compact = Jwts.builder()
                .setIssuer("spring-jwt-annotation")
                .setExpiration(new Date(System.currentTimeMillis() + Long.parseLong(JWT_EXPIRATION)))
                .setIssuedAt(new Date())
                .setSubject(email)
                .signWith(SignatureAlgorithm.HS256, JWT_SECRET)
                .compact();
        return compact;
    }

    public void validateToken(String token) {
        try {
            Jwts.parser().setSigningKey(JWT_SECRET).parseClaimsJws(token);
        } catch (Exception e) {
            throw new RuntimeException("Token invalid or expired!");
        }
    }

}
