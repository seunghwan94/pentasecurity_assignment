package com.example.demo.service;

import java.util.List;

import com.example.demo.entity.SystemUser;

public interface SystemUserService {
  SystemUser createUser(SystemUser user, String ipAddress);
  SystemUser updateUser(Integer userIdx, String newName, String ipAddress);
  void deleteUser(Integer userIdx, String ipAddress);
  List<SystemUser> searchUsers(String keyword);
  SystemUser getUserById(String userId);
}
