package com.example.demo.repository;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.example.demo.entity.SystemUser;

public interface SystemUserRepository extends JpaRepository<SystemUser, Integer> {
  Optional<SystemUser> findByUserId(String userId);

  @Query(value = "SELECT * FROM SYSTEM_USER WHERE user_id LIKE %:keyword% OR user_nm LIKE %:keyword%", nativeQuery = true)
  List<SystemUser> searchBy(@Param("keyword") String keyword);
}
