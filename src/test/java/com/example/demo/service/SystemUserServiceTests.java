package com.example.demo.service;

import java.util.List;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

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
  private String ipAddress;

  @BeforeEach
  void setUp() {
    ipAddress = "127.0.0.1";
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
