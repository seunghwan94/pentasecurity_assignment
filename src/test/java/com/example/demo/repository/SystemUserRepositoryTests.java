package com.example.demo.repository;

import java.util.List;
import java.util.Optional;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import com.example.demo.entity.SystemUser;

@SpringBootTest
public class SystemUserRepositoryTests {
  @Autowired
  private SystemUserRepository repository;

  @Test
  void findByUserIdTest(){
    Optional<SystemUser> user = repository.findByUserId("admin");
    
    if (user.isPresent()) {
      System.out.println("user : " + user.get().getUserNm());
    } else {
      System.out.println("user X");
    }
  }

  @Test
  void searchByTest(){
    List<SystemUser> users = repository.searchBy("admin");
    
    if (users.isEmpty()) {
      System.out.println("search X");
    } else {
      for (SystemUser user : users) {
        System.out.println("user: " + user.getUserNm() + " / id: " + user.getUserId());
      }
    }
  }

}
