package com.example.demo.entity;

import java.time.LocalDateTime;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
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
@Table(name = "USER_HISTORY")
@Getter 
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class UserHistory {
  
  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  @Column(name = "history_idx")
  private Integer historyIdx;

  @Column(name = "url", columnDefinition = "TEXT", nullable = false)
  private String url;

  @Enumerated(EnumType.STRING)
  @Column(name = "action_type", nullable = false)
  private ActionType actionType;

  @Column(name = "reg_user_idx", nullable = false)
  private Integer regUserIdx;

  @Column(name = "reg_ip", length = 16, nullable = false)
  private String regIp;

  @Column(name = "reg_dt", nullable = false)
  private LocalDateTime regDt;

  public enum ActionType {
    C, U, D
  }

}