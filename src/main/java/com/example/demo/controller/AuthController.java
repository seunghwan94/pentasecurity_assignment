package com.example.demo.controller;

import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.example.demo.dto.LoginRequest;
import com.example.demo.dto.TokenResponse;
import com.example.demo.entity.SystemUser;
import com.example.demo.jwt.JwtTokenProvider;
import com.example.demo.repository.SystemUserRepository;

import lombok.AllArgsConstructor;

@RestController
@RequestMapping("/api/v1/auth")
@AllArgsConstructor
public class AuthController {
  private final JwtTokenProvider jwtTokenProvider;
  private final SystemUserRepository userRepository;
  private final PasswordEncoder passwordEncoder;

  @PostMapping("/login")
  public ResponseEntity<?> login(@RequestBody LoginRequest request) {

    SystemUser user = userRepository.findByUserId(request.getUserId())
            .orElseThrow(() -> new IllegalArgumentException("존재하지 않는 사용자입니다."));

    if (!passwordEncoder.matches(request.getUserPw(), user.getUserPw())) {
      return ResponseEntity.badRequest().body("비밀번호가 일치하지 않습니다.");
    }

    String token = jwtTokenProvider.createToken(user.getUserId(), user.getUserAuth());
    return ResponseEntity.ok(new TokenResponse(token));
  }
}
