-- 기본 관리자 계정: ID = admin, PW = 1234
INSERT INTO `SYSTEM_USER` (`user_idx`, `user_id`, `user_pw`, `user_nm`, `user_auth`) VALUES
(1, 'admin', '$2a$10$v3lXeBBnLUFkl3olmhf3suRrKOyXvWHAPWRaear.Vo0fmaGiChhO6', '관리자', 'SYSTEM_ADMIN');