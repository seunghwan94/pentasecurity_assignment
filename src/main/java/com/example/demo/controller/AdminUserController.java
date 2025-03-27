package com.example.demo.controller;

import java.util.List;
import java.util.Optional;

import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.example.demo.entity.SystemUser;
import com.example.demo.repository.SystemUserRepository;
import com.example.demo.service.SystemUserService;

import jakarta.servlet.http.HttpServletRequest;
import lombok.AllArgsConstructor;

@Controller
@AllArgsConstructor
public class AdminUserController {
  private final SystemUserRepository userRepository;
  private final SystemUserService userService;

  @PreAuthorize("hasRole('SYSTEM_ADMIN')")
  @GetMapping("/admin/users")
  public String userList(Model model) {
    List<SystemUser> users = userRepository.findAll();
    model.addAttribute("users", users);
    return "admin/user-list";
  }

  @PreAuthorize("hasRole('SYSTEM_ADMIN')")
  @GetMapping("/admin/users/{id}")
  public String userDetail(@PathVariable("id") Integer userIdx, Model model) {

    Optional<SystemUser> optionalUser = userRepository.findById(userIdx);
    if (!optionalUser.isPresent()) {
      throw new UsernameNotFoundException("사용자 없음");
    }
    SystemUser user = optionalUser.get();

    model.addAttribute("user", user);
    return "admin/user-detail";
  }


  @PreAuthorize("hasRole('SYSTEM_ADMIN')")
  @PostMapping("/admin/users/{id}/delete")
  public String deleteUser(@PathVariable("id") Integer userIdx, HttpServletRequest request) {
    String ip = request.getRemoteAddr();
    userService.deleteUser(userIdx, ip);
    return "redirect:/admin/users";
  }

  @PreAuthorize("hasRole('SYSTEM_ADMIN')")
  @PostMapping("/admin/users/{id}/update")
  public String updateUser(@PathVariable("id") Integer userIdx, @RequestParam("userNm") String newName, HttpServletRequest request) {
    String ip = request.getRemoteAddr();
    userService.updateUser(userIdx, newName, ip);
    return "redirect:/admin/users/" + userIdx;
  }

}
