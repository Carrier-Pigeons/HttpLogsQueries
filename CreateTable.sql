CREATE DATABASE IF NOT EXISTS wordpress_logs;
USE wordpress_logs;
-- CREATE TABLE custom_log (
--     id INT AUTO_INCREMENT PRIMARY KEY,
--     host VARCHAR(255) NOT NULL,
--     user_agent VARCHAR(255) NOT NULL,
--     accept VARCHAR(255) NOT NULL,
--     connection VARCHAR(255) NOT NULL,
--     created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP
-- );

CREATE TABLE header (
    header VARCHAR(30) NOT NULL,
    uid CHAR(17) NOT NULL,
    request VARCHAR(255) CHARACTER SET latin1 NOT NULL,
    proxy BOOLEAN NOT NULL,
    user_agent VARCHAR(255) NOT NULL,
    headerOrder TINYINT NOT NULL CHECK (headerOrder > 0 AND headerOrder <= 100),
    headerValue VARCHAR(255) NOT NULL,
    created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    PRIMARY KEY (header, uid),
    INDEX idx_proxy_request_header_headerOrder_user_agent_uid_headerValue (proxy, request, header, headerOrder, user_agent, uid, headerValue)
);

