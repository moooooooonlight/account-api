CREATE TABLE users (
       user_id VARCHAR(50) NOT NULL PRIMARY KEY,
       user_email VARCHAR(200) NULL,
       user_password VARCHAR(200) NOT NULL,
       user_name VARCHAR(50) NOT NULL,
       user_status ENUM('ACTIVE', 'WITHDRAWN', 'DORMANT') NOT NULL
);