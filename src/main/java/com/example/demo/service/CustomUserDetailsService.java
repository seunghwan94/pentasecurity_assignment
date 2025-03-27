package com.example.demo.service;

import java.util.Collections;
import java.util.Optional;

import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.security.core.userdetails.User;

import com.example.demo.entity.SystemUser;
import com.example.demo.repository.SystemUserRepository;

import lombok.AllArgsConstructor;

@Service
@AllArgsConstructor
public class CustomUserDetailsService implements UserDetailsService {
  private final SystemUserRepository userRepository;

  @Override
  public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
    Optional<SystemUser> optionalUser = userRepository.findByUserId(username);

    if (!optionalUser.isPresent()) {
      throw new UsernameNotFoundException("사용자 없음");
    }

    SystemUser user = optionalUser.get();

    return new User(user.getUserId(), user.getUserPw(),
                    Collections.singletonList(new SimpleGrantedAuthority("ROLE_" + user.getUserAuth()))
    );
  }
}
