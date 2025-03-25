package com.example.demo.jwt;

import java.security.Key;
import java.util.Base64;
import java.util.Date;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.JwtException;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.security.Keys;
import jakarta.annotation.PostConstruct;
import jakarta.servlet.http.HttpServletRequest;

@Component
public class JwtTokenProvider {
  @Value("${jwt.secret}")
  private String secretKey;

  @Value("${jwt.token-validity-in-seconds}")
  private long tokenValidityInSeconds;

  private Key key;

  // 초기화 시 키 세팅
  @PostConstruct
  protected void init() {
    byte[] keyBytes = Base64.getEncoder().encode(secretKey.getBytes());
    key = Keys.hmacShaKeyFor(keyBytes);
  }

  // 토큰 생성
  public String createToken(String userId, String role) {
    Claims claims = Jwts.claims().setSubject(userId);
    claims.put("role", role);

    Date now = new Date();
    Date validity = new Date(now.getTime() + tokenValidityInSeconds * 1000);

    return Jwts.builder()
              .setClaims(claims)
              .setIssuedAt(now)
              .setExpiration(validity)
              .signWith(key, SignatureAlgorithm.HS256)
            .compact();
  }

  // 토큰에서 사용자 ID 추출
  public String getUserId(String token) {
    return Jwts.parserBuilder().setSigningKey(key).build()
            .parseClaimsJws(token).getBody().getSubject();
  }

  // 토큰 유효성 검사
  public boolean validateToken(String token) {
    try {
      Jwts.parserBuilder().setSigningKey(key).build().parseClaimsJws(token);
      return true;
    } catch (JwtException | IllegalArgumentException e) {
      return false;
    }
  }

  // Request Header 에서 토큰 추출
  public String resolveToken(HttpServletRequest request) {
    String bearerToken = request.getHeader("Authorization");
    if (bearerToken != null && bearerToken.startsWith("Bearer ")) {
      return bearerToken.substring(7);
    }
    return null;
  }
}
