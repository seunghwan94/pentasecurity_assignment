package com.example.demo.service;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.util.List;
import java.util.Optional;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.security.crypto.password.PasswordEncoder;

import com.example.demo.entity.SystemUser;
import com.example.demo.repository.SystemUserRepository;

import jakarta.transaction.Transactional;

@SpringBootTest
@Transactional
public class SystemUserServiceTests {
  @Autowired
  private SystemUserService service;
  @Autowired
  private SystemUserRepository userRepository;
  @Autowired
  private PasswordEncoder passwordEncoder;
  private String ipAddress;

  @BeforeEach
  void setUp() {
    ipAddress = "127.0.0.1";
  }

  @Test
  void createAdminAndUser() {
    // 관리자
    SystemUser admin = SystemUser.builder()
              .userId("admin_test")
              .userPw(passwordEncoder.encode("admin123"))
              .userNm("관리자 테스트")
              .userAuth("SYSTEM_ADMIN")
              .build();
            service.createUser(admin, ipAddress);

    // 일반회원
    SystemUser user = SystemUser.builder()
              .userId("user_test")
              .userPw(passwordEncoder.encode("user123"))
              .userNm("일반회원 테스트")
              .userAuth("USER")
              .build();
            service.createUser(user, ipAddress);

    // 검증
    Optional<SystemUser> savedAdmin = userRepository.findByUserId("admin_test");
    Optional<SystemUser> savedUser = userRepository.findByUserId("user_test");

    assertTrue(savedAdmin.isPresent());
    assertTrue(savedUser.isPresent());

    assertEquals("관리자 테스트", savedAdmin.get().getUserNm());
    assertEquals("일반회원 테스트", savedUser.get().getUserNm());
    assertEquals("SYSTEM_ADMIN", savedAdmin.get().getUserAuth());
    assertEquals("USER", savedUser.get().getUserAuth());
  }

  @Test
  void getUserByIdTest() {
    SystemUser user = service.getUserById("admin");
    System.out.println("user: " + user.getUserNm());
  }

  @Test
  void searchUsersTest() {
    List<SystemUser> users = service.searchUsers("ad");
    if (users.isEmpty()) {
      System.out.println("검색 결과 없음");
    } else {
      users.forEach(u -> System.out.println("검색 결과과: " + u.getUserId()));
    }
  }

  @Test
  void updateUserTest() {
    SystemUser user = service.getUserById("admin");
    SystemUser updated = service.updateUser(user.getUserIdx(), "관리자수정", ipAddress);
    System.out.println("수정된 이름: " + updated.getUserNm());
  }

  @Test
  void deleteUserTest() {
    SystemUser user = service.getUserById("admin");
    service.deleteUser(user.getUserIdx(), ipAddress);
    System.out.println("삭제 완료");

    // 삭제 확인
    boolean exists = userRepository.findByUserId("admin").isPresent();
    System.out.println("history 확인: " + exists);
  }
}
