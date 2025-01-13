-- data.sql
-- insert into member values (1, 'member', 'password', 'temp','kim','M','2024-12-16 14:30:00','2024-12-16 14:30:00');
INSERT INTO member VALUES (1, 'test', '$2a$10$003m6CEo6xjGK8UT.ga9Ye9T8SlQZEiIknepq.yvRMO4rhM3sv1ue', '테스트','테스트','M','2024-12-16 14:30:00','2024-12-16 14:30:00');
INSERT INTO roles (role_name) VALUES ('ROLE_ADMIN'),('ROLE_USER');
INSERT INTO MEMBER_ROLES (member_no, role_id, reg_dt) VALUES (1, 1, NOW());
INSERT INTO MEMBER_ROLES (member_no, role_id, reg_dt) VALUES (1, 2, NOW());