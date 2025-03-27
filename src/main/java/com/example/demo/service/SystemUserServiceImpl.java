package com.example.demo.service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

import org.springframework.stereotype.Service;

import com.example.demo.entity.SystemUser;
import com.example.demo.entity.UserHistory;
import com.example.demo.entity.UserHistory.ActionType;
import com.example.demo.repository.SystemUserRepository;
import com.example.demo.repository.UserHistoryRepository;

import lombok.AllArgsConstructor;

@Service
@AllArgsConstructor
public class SystemUserServiceImpl implements SystemUserService {
  
  private final SystemUserRepository userRepository;
  private final UserHistoryRepository historyRepository;

  @Override
  public SystemUser createUser(SystemUser user, String ipAddress) {
    SystemUser saved = userRepository.save(user);
    saveHistory("/api/v1/users", ActionType.C, saved.getUserIdx(), ipAddress);
    return saved;
  }

  @Override
  public void deleteUser(Integer userIdx, String ipAddress) {
    userRepository.deleteById(userIdx);
    saveHistory("/admin/users/" + userIdx, ActionType.D, userIdx, ipAddress);
  }

  @Override
  public SystemUser getUserById(String userId) {
    Optional<SystemUser> optionalUser = userRepository.findByUserId(userId);
    if (!optionalUser.isPresent()) {
      throw new IllegalArgumentException("User not found");
    }
    return optionalUser.get();
  }

  @Override
  public List<SystemUser> searchUsers(String keyword) {
    return userRepository.searchBy(keyword);
  }

  @Override
  public SystemUser updateUser(Integer userIdx, String newName, String ipAddress) {
    Optional<SystemUser> optionalUser = userRepository.findById(userIdx);

    if (!optionalUser.isPresent()) {
      throw new IllegalArgumentException("User not found");
    }

    SystemUser user = optionalUser.get();
    user.setUserNm(newName);

    SystemUser updated = userRepository.save(user);

    saveHistory("/admin/users/" + userIdx, ActionType.U, userIdx, ipAddress);

    return updated;
  }
  
  
  private void saveHistory(String url, ActionType action, Integer userIdx, String ip) {
    UserHistory history = UserHistory.builder()
                            .url(url)
                            .actionType(action)
                            .regUserIdx(userIdx)
                            .regIp(ip)
                            .regDt(LocalDateTime.now())
                          .build();
    historyRepository.save(history);
  }
}
