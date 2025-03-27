package com.example.demo.controller;

import java.util.Collections;
import java.util.Map;
import java.util.Optional;

import org.springframework.http.ResponseEntity;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.example.demo.entity.SystemUser;
import com.example.demo.jwt.JwtUtil;
import com.example.demo.repository.SystemUserRepository;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.AllArgsConstructor;

@RestController
@RequestMapping("/api/v1/auth")
@AllArgsConstructor
@Tag(name = "인증 API", description = "로그인 및 토큰 발급 API입니다.")
public class AuthController {

  private final JwtUtil jwtUtil;
  private final SystemUserRepository userRepository;

  @Operation(
  summary = "로그인",
  description = """
  회원 아이디(userId)와 비밀번호(userPw)를 통해 로그인을 시도하고, 
  성공 시 JWT 토큰을 반환합니다.

  - 실패 시: 400 (비밀번호 틀림, 아이디 없음)
  - 성공 시: { "token": "..." }
  """)
  @PostMapping("/login")
  public ResponseEntity<?> login(@RequestBody Map<String, String> request) {
    String userId = request.get("userId");
    String userPw = request.get("userPw");

    Optional<SystemUser> optionalUser = userRepository.findByUserId(userId);
    if (!optionalUser.isPresent()) {
      throw new UsernameNotFoundException("사용자 없음");
    }
    SystemUser user = optionalUser.get();

    if (user.getUserPw() != null && !user.getUserPw().isEmpty()) {
      if (!user.getUserPw().equals(userPw)) {
        throw new IllegalArgumentException("비밀번호 불일치");
      }
    }

    String token = jwtUtil.generateToken(userId);

    return ResponseEntity.ok(Collections.singletonMap("token", token));
  }
}