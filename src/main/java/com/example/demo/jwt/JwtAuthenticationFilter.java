package com.example.demo.jwt;

import java.io.IOException;

import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;

import jakarta.servlet.FilterChain;
import jakarta.servlet.GenericFilter;
import jakarta.servlet.ServletException;
import jakarta.servlet.ServletRequest;
import jakarta.servlet.ServletResponse;
import jakarta.servlet.http.HttpServletRequest;

@Component
public class JwtAuthenticationFilter extends GenericFilter {

  private final JwtUtil jwtUtil;

  public JwtAuthenticationFilter(JwtUtil jwtUtil) {
      this.jwtUtil = jwtUtil;
  }

  @Override
  public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain) throws IOException, ServletException {
      HttpServletRequest httpReq = (HttpServletRequest) request;
      String authHeader = httpReq.getHeader("Authorization");
      String uri = httpReq.getRequestURI();

      // Swagger 관련 요청은 필터 타지 않도록 예외처리
      if (uri.startsWith("/v3/api-docs") || uri.startsWith("/swagger") || uri.startsWith("/swagger-ui")) {
        chain.doFilter(request, response);
        return;
      }

      if (authHeader != null && authHeader.startsWith("Bearer ")) {
          try {
              String token = authHeader.substring(7);
              String userId = jwtUtil.validateAndGetUserId(token);

              // 간단한 인증 객체 생성 (실제 권한은 여기서 처리하지 않음)
              Authentication auth = new UsernamePasswordAuthenticationToken(userId, null, null);
              SecurityContextHolder.getContext().setAuthentication(auth);
          } catch (Exception e) {
              // 토큰 오류 시 아무 것도 인증하지 않고 넘어감
          }
      }

      chain.doFilter(request, response);
  }
}

