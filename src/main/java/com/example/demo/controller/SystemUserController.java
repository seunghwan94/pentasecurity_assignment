package com.example.demo.controller;

import java.util.List;
import java.util.Map;

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

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.servlet.http.HttpServletRequest;
import lombok.AllArgsConstructor;

@RestController
@RequestMapping("/api/v1/users")
@AllArgsConstructor
@SecurityRequirement(name = "bearerAuth")
@Tag(name = "회원 관리 API", description = "회원 정보 조회, 수정, 삭제")
public class SystemUserController {
  private final SystemUserService userService;

  // 유저 조회 (userId)
  @GetMapping("/{userId}")
  @Operation(summary = "회원 ID로 조회", description = "회원 아이디에 해당하는 회원 정보를 조회합니다.")
  public ResponseEntity<SystemUser> getUserById(@PathVariable String userId) {
    SystemUser user = userService.getUserById(userId);
    return ResponseEntity.ok(user);
  }

  // 검색 (keyword)
  @GetMapping
  @Operation(summary = "회원 검색", description = "회원 ID 또는 이름으로 검색된 회원 목록을 반환합니다.")
  public ResponseEntity<List<SystemUser>> searchUsers(@RequestParam("keyword") String keyword) {
    List<SystemUser> users = userService.searchUsers(keyword);
    return ResponseEntity.ok(users);
  }

  // 회원 등록
  @PostMapping
  @Operation(summary = "회원 등록", description = "회원 정보를 등록합니다.")
  public ResponseEntity<SystemUser> createUser(@RequestBody SystemUser user, HttpServletRequest request) {
    String ip = request.getRemoteAddr();
    SystemUser created = userService.createUser(user, ip);
    return ResponseEntity.ok(created);
  }

  // 회원 수정
  @PutMapping("/{userIdx}")
  @Operation(summary = "회원 수정", description = "회원 ID에 해당하는 회원 이름을 수정합니다.")
  public ResponseEntity<SystemUser> updateUser(@PathVariable Integer userIdx, @RequestBody Map<String, String> body, HttpServletRequest request) {
    String newName = body.get("name");
    String ip = request.getRemoteAddr();

    SystemUser updated = userService.updateUser(userIdx, newName, ip);
    return ResponseEntity.ok(updated);
  }

  // 회원 삭제
  @DeleteMapping("/{userIdx}")
  @Operation(summary = "회원 삭제", description = "회원 ID에 해당하는 회원을 삭제합니다.")
  public ResponseEntity<?> deleteUser(@PathVariable Integer userIdx, HttpServletRequest request) {
    String ip = request.getRemoteAddr();
    userService.deleteUser(userIdx, ip);
    return ResponseEntity.ok("삭제 완료");
  }
}
