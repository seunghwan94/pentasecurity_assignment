package com.example.demo.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.example.demo.entity.UserHistory;

public interface UserHistoryRepository extends JpaRepository<UserHistory, Integer> {
  
}
