package com.example.demo.controller;

import java.util.List;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.example.demo.entity.SystemUser;
import com.example.demo.service.SystemUserService;

import jakarta.servlet.http.HttpServletRequest;
import lombok.AllArgsConstructor;

@RestController
@RequestMapping("/api/v1/users")
@AllArgsConstructor
public class SystemUserController {
  private final SystemUserService userService;

  // 유저 조회 (userId)
  @GetMapping("/{userId}")
  public ResponseEntity<SystemUser> getUserById(@PathVariable String userId) {
    SystemUser user = userService.getUserById(userId);
    return ResponseEntity.ok(user);
  }

  // 검색 (keyword)
  @GetMapping
  public ResponseEntity<List<SystemUser>> searchUsers(@RequestParam("keyword") String keyword) {
    List<SystemUser> users = userService.searchUsers(keyword);
    return ResponseEntity.ok(users);
  }

  // 회원 등록
  @PostMapping
  public ResponseEntity<SystemUser> createUser(@RequestBody SystemUser user, HttpServletRequest request) {
    String ip = request.getRemoteAddr();
    SystemUser created = userService.createUser(user, ip);
    return ResponseEntity.ok(created);
  }

  // 회원 수정
  @PutMapping("/{userIdx}")
  public ResponseEntity<SystemUser> updateUser(@PathVariable Integer userIdx,
                                                @RequestParam("name") String newName,
                                                HttpServletRequest request) {
    String ip = request.getRemoteAddr();
    SystemUser updated = userService.updateUser(userIdx, newName, ip);
    return ResponseEntity.ok(updated);
  }

  // 회원 삭제
  @DeleteMapping("/{userIdx}")
  public ResponseEntity<?> deleteUser(@PathVariable Integer userIdx, HttpServletRequest request) {
    String ip = request.getRemoteAddr();
    userService.deleteUser(userIdx, ip);
    return ResponseEntity.ok("삭제 완료");
  }
}
