## 펜타시큐리티 사전 과제

### 개발 환경
- Java 17
- Spring Boot (3.4.5-SNAPSHOT)
- MariaDB 10.3 (Docker 사용)
  - 로컬 개발의 편의성을 위해 Docker 사용했습니다.
- Gradle
- Thymeleaf
  - 템플릿 엔진으로 로그인 페이지 구현에 적합하다 생각했습니다.
- Spring Security
  - 세션 기반 로그인 및 권한 제어, JWT 인증 보안 처리를 위해 사용했습니다.
- Swagger
  - API 명세서로 Spring 환경에 적합한 Swagger를 사용했습니다.

---

### 회원 관리 API (과제 1)
- 회원 CRUD (등록, 조회, 수정, 삭제)
- JWT 기반 인증 적용
- Swagger 기반 API 명세

### 로그인·로그아웃 및 페이지 구현 (과제 2)
- 로그인 / 로그아웃 (Security - 세션 기반)
- 회원가입 (일반회원만 가능)
- 메인 페이지
  - 일반 회원: [회원명]님 환영합니다 문구 출력
  - 관리자: 회원 목록 메뉴 노출
- 회원 목록 / 상세 / 수정 / 삭제 (관리자 전용)
- 변경 이력 (USER_HISTORY 테이블: C/U/D)

---

### 실행 방법
```bash
# docker container 실행
docker-compose up -d

# docker container 중지
docker-compose down

# 실행 확인
docker ps

# IDE에서 DemoApplication.java 직접 실행.
```

### 링크
login 페이지: (http://localhost:8080/logout)

Swagger: (http://localhost:8080/swagger-ui/index.html)

test 코드 경로: /src/test/java/com/example/demo/SystemUserControllerTests.java

![image](https://github.com/user-attachments/assets/9dca82e9-e029-4f59-b895-42b3b54b32ae)
