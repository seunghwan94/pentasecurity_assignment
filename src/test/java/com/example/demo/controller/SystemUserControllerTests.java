package com.example.demo.controller;


import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.*;

import com.example.demo.entity.SystemUser;
import com.fasterxml.jackson.databind.ObjectMapper;

import jakarta.transaction.Transactional;

@SpringBootTest
@Transactional
public class SystemUserControllerTests {
  private MockMvc mockMvc;

  @Autowired
  private ObjectMapper objectMapper; // JSON 변환용

  // 각각의 테스트 실행전 초기화
  @BeforeEach
  public void init(WebApplicationContext ctx) {
    mockMvc = MockMvcBuilders.webAppContextSetup(ctx).build();
  }

  @Test
  public void getUserByIdTest() throws Exception {
    String userId = "admin";

    mockMvc.perform(get("/api/v1/users/" + userId))
      .andExpect(status().isOk()) // 응답 상태
      .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE)) // 응답json인지 확인
    .andDo(print());  // 테스트 결과 출력
  }

  @Test
  public void searchUsersTest() throws Exception {
    String keyword = "ad";

    mockMvc.perform(get("/api/v1/users")
        .param("keyword", keyword))
      .andExpect(status().isOk()) // 응답 상태
      .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE)) // 응답json인지 확인
    .andDo(print());  // 테스트 결과 출력
  }
  
  @Test
  public void createUserTest() throws Exception {
    SystemUser createUser = SystemUser.builder()
      .userId("tester")
      .userPw("1234")
      .userNm("테스터")
      .userAuth("USER")
    .build();

    String jsonBody = objectMapper.writeValueAsString(createUser);

    mockMvc.perform(post("/api/v1/users")
        .contentType(MediaType.APPLICATION_JSON)
        .content(jsonBody))
      .andExpect(status().isOk())
      .andExpect(content().contentType(MediaType.APPLICATION_JSON))
    .andDo(print());
  }

  @Test
  public void updateUserTest() throws Exception {
    Integer userIdx = 2; 
    String newName = "수정된이름";

    mockMvc.perform(put("/api/v1/users/" + userIdx)
        .param("name", newName))
      .andExpect(status().isOk())
      .andExpect(content().contentType(MediaType.APPLICATION_JSON))
    .andDo(print());
  }

  @Test
  public void deleteUserTest() throws Exception {
    Integer userIdx = 2; 

    mockMvc.perform(delete("/api/v1/users/" + userIdx))
      .andExpect(status().isOk())
    .andDo(print());
  }
}
