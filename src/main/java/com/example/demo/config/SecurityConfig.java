package com.example.demo.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

import com.example.demo.jwt.JwtAuthenticationFilter;

import lombok.AllArgsConstructor;

@Configuration
@AllArgsConstructor
public class SecurityConfig {

  private final JwtAuthenticationFilter jwtFilter;

  @Bean
  public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
    http
      .csrf(csrf -> csrf.disable())
      .authorizeHttpRequests(auth -> auth
          .requestMatchers("/login", "/signup", "/css/**", "/js/**", "/images/**").permitAll()
          .requestMatchers("/api/v1/auth/**", "/swagger-ui/**", "/v3/api-docs/**","/swagger-resources/**", "/webjars/**").permitAll()
          .requestMatchers("/admin/**").hasRole("SYSTEM_ADMIN")
        .anyRequest().authenticated())
      .formLogin(form -> form
          .loginPage("/login")                       
          .loginProcessingUrl("/login-process")      // 로그인 submit 처리
          .defaultSuccessUrl("/main", true) // 성공 시 이동 경로
          .failureUrl("/login?error=true")     // 실패 시 이동
        .permitAll())
      .logout(logout -> logout
        .logoutUrl("/logout")
        .logoutSuccessUrl("/login?logout=true")
        .permitAll());
    // API 요청은 JWT 필터로 보호
    http.addFilterBefore(jwtFilter, UsernamePasswordAuthenticationFilter.class);

    return http.build();
  }

  @Bean
  public PasswordEncoder passwordEncoder() {
    return new BCryptPasswordEncoder();
  }
}
