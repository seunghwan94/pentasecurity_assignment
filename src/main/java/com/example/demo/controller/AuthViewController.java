package com.example.demo.controller;

import java.util.Optional;

import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.example.demo.entity.SystemUser;
import com.example.demo.repository.SystemUserRepository;

import jakarta.servlet.http.HttpServletRequest;
import lombok.AllArgsConstructor;

@Controller
@AllArgsConstructor
public class AuthViewController {

  private final SystemUserRepository userRepository;
  private final PasswordEncoder passwordEncoder;

  @GetMapping("/login")
  public String loginPage() {
    return "login"; 
  }

  @GetMapping("/signup")
  public String signupForm() {
    return "signup";
  }
  @PostMapping("/signup")
  public String signup(HttpServletRequest request, RedirectAttributes redirect) {
    String userId = request.getParameter("userId");
    String userPw = request.getParameter("userPw");
    String userNm = request.getParameter("userNm");

    if (userRepository.findByUserId(userId).isPresent()) {
      redirect.addFlashAttribute("error", "이미 존재하는 아이디입니다.");
      return "redirect:/signup";
    }

    SystemUser user = SystemUser.builder()
              .userId(userId)
              .userPw(passwordEncoder.encode(userPw)) 
              .userNm(userNm)
              .userAuth("USER")
            .build();

    userRepository.save(user);

    return "redirect:/login";
  }
  
  @GetMapping("/main")
  public String mainPage(Model model, Authentication authentication) {
    if (authentication == null) {
        return "redirect:/login";
    }

    String userId = authentication.getName();

    Optional<SystemUser> optionalUser = userRepository.findByUserId(userId);
    if (!optionalUser.isPresent()) {
      throw new UsernameNotFoundException("사용자 없음");
    }
    SystemUser user = optionalUser.get();

    model.addAttribute("userName", user.getUserNm());
    model.addAttribute("isAdmin", user.getUserAuth().equals("SYSTEM_ADMIN"));

    return "main";
  }

}
