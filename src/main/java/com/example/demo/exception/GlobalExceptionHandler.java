package com.example.demo.exception;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.HttpRequestMethodNotSupportedException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import jakarta.servlet.http.HttpServletRequest;

@RestControllerAdvice
public class GlobalExceptionHandler {
  // 잘못된 요청 (ex: 없는 유저, 잘못된 입력)
  @ExceptionHandler(IllegalArgumentException.class)
  public ResponseEntity<ErrorResponse> handleBadRequest(IllegalArgumentException e, HttpServletRequest request) {
    ErrorResponse response = new ErrorResponse(400, e.getMessage(), request.getRequestURI());
    return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(response);
  }

  // 405 - 지원하지 않는 HTTP 메서드
  @ExceptionHandler(HttpRequestMethodNotSupportedException.class)
  public ResponseEntity<ErrorResponse> handle405(HttpRequestMethodNotSupportedException e, HttpServletRequest request) {
    return ResponseEntity.status(HttpStatus.METHOD_NOT_ALLOWED)
            .body(new ErrorResponse(405, "허용되지 않은 HTTP 메서드입니다.", request.getRequestURI()));
  }

  // 그 외 모든 서버 에러
  @ExceptionHandler(Exception.class)
  public ResponseEntity<ErrorResponse> handle500(Exception e, HttpServletRequest request) {
    e.printStackTrace(); 
    return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
            .body(new ErrorResponse(500, "서버 내부 오류가 발생했습니다.", request.getRequestURI()));
  }
}
