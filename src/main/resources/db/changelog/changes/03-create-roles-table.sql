CREATE TABLE IF NOT EXISTS roles (
id BIGINT AUTO_INCREMENT PRIMARY KEY,
role_name ENUM('USER', 'ADMIN') NOT NULL
);
