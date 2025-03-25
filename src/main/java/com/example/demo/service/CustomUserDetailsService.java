package com.example.demo.service;

import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import com.example.demo.entity.SystemUser;
import com.example.demo.repository.SystemUserRepository;

import lombok.AllArgsConstructor;

@Service
@AllArgsConstructor
public class CustomUserDetailsService implements UserDetailsService {

    private final SystemUserRepository userRepository;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        SystemUser user = userRepository.findByUserId(username)
                .orElseThrow(() -> new UsernameNotFoundException("존재하지 않는 사용자입니다."));
        return new CustomUserDetails(user);
    }
  
}
