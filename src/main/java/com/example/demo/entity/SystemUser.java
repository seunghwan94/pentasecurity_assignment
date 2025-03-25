package com.example.demo.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Table(name = "SYSTEM_USER")
@Getter 
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class SystemUser {
  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  @Column(name = "user_idx")
  private Integer userIdx;

  @Column(name = "user_id", nullable = false, length = 30)
  private String userId;

  @Column(name = "user_pw", nullable = false, length = 100)
  private String userPw;

  @Column(name = "user_nm", nullable = false, length = 100)
  private String userNm;

  @Column(name = "user_auth", nullable = false, length = 20)
  private String userAuth;
  
}
