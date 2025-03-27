package com.example.demo.jwt;

import java.security.Key;
import java.util.Date;

import org.springframework.stereotype.Component;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.security.Keys;

@Component
public class JwtUtil {
  private final String secret = "this-is-a-super-secure-jwt-key-123456789012345"; // 32자 이상 필요
  private final long expirationMs = 1000 * 60 * 60; // 1시간
  private final Key key = Keys.hmacShaKeyFor(secret.getBytes());

  public String generateToken(String userId) {
      return Jwts.builder()
              .setSubject(userId)
              .setIssuedAt(new Date())
              .setExpiration(new Date(System.currentTimeMillis() + expirationMs))
              .signWith(key)
              .compact();
  }

  public String validateAndGetUserId(String token) {
      Claims claims = Jwts.parserBuilder()
              .setSigningKey(key)
              .build()
              .parseClaimsJws(token)
              .getBody();

      return claims.getSubject(); // userId
  }
}
