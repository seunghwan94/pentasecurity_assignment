package com.example.demo.service;

import java.util.Collection;
import java.util.Collections;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import com.example.demo.entity.SystemUser;

import lombok.Getter;

@Getter
public class CustomUserDetails implements UserDetails {

    private final SystemUser user;

    public CustomUserDetails(SystemUser user) {
        this.user = user;
    }

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        // Spring Security는 ROLE_ 접두사를 권한에 붙여서 사용함
        return Collections.singletonList(new SimpleGrantedAuthority("ROLE_" + user.getUserAuth()));
    }

    @Override
    public String getPassword() {
        return user.getUserPw();
    }

    @Override
    public String getUsername() {
        return user.getUserId(); // 사용자 식별자
    }

    @Override
    public boolean isAccountNonExpired() { return true; }

    @Override
    public boolean isAccountNonLocked() { return true; }

    @Override
    public boolean isCredentialsNonExpired() { return true; }

    @Override
    public boolean isEnabled() { return true; }
  
}
