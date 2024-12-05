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
    proxyName VARCHAR(30) NULL,
    user_agent VARCHAR(255) NOT NULL,
    headerOrder TINYINT NOT NULL CHECK (headerOrder > 0 AND headerOrder <= 100),
    headerValue VARCHAR(255) NOT NULL,
    created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    PRIMARY KEY (header, uid),
    INDEX idx_request_header_headerOrder_user_agent_uid_proxy_headerValue (request, header, headerOrder, user_agent, uid, proxy, headerValue)
);

-- The index's columns exceeded the 3072 byte limit until we changed some columns to latin1
-- By default the columns, (with the exception of uid since it is of type CHAR)
-- are of type utf8mb3 which has 3 bytes per character or utf8mb4 which is 4 bytes per character,
-- while latin1 has one byte per character.
