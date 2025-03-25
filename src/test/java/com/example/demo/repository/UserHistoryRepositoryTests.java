package com.example.demo.repository;

import java.util.List;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import com.example.demo.entity.UserHistory;

@SpringBootTest
public class UserHistoryRepositoryTests {
  @Autowired
  private UserHistoryRepository repository;

  @Test
  void findAllTest(){
    List<UserHistory> users = repository.findAll();
    
   if (users.isEmpty()) {
      System.out.println("history X");
    } else {
      for (UserHistory user : users) {
        System.out.println("HistoryIdx: " + user.getHistoryIdx());
      }
    }
  }

}
