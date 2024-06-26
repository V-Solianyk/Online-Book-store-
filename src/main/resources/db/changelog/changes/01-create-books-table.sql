CREATE TABLE IF NOT EXISTS books (
id BIGINT AUTO_INCREMENT PRIMARY KEY,
title VARCHAR(255) NOT NULL,
author VARCHAR(255) NOT NULL,
isbn VARCHAR(255) NOT NULL UNIQUE,
price DECIMAL NOT NULL,
description VARCHAR(255),
cover_image VARCHAR(255),
is_deleted BOOLEAN DEFAULT FALSE
);