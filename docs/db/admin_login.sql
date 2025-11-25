-- CQWM admin login schema & seed data
CREATE DATABASE IF NOT EXISTS `cqwm`
    DEFAULT CHARACTER SET utf8mb4
    COLLATE utf8mb4_general_ci;

USE `cqwm`;

CREATE TABLE IF NOT EXISTS `cqwm_admin_user`
(
    `id`             BIGINT UNSIGNED NOT NULL AUTO_INCREMENT,
    `username`       VARCHAR(64)     NOT NULL COMMENT '登录账号',
    `password_hash`  VARCHAR(100)    NOT NULL COMMENT 'BCrypt 哈希',
    `full_name`      VARCHAR(64)     NOT NULL COMMENT '姓名/昵称',
    `phone`          VARCHAR(20)              DEFAULT NULL,
    `email`          VARCHAR(128)             DEFAULT NULL,
    `status`         VARCHAR(16)     NOT NULL DEFAULT 'ENABLED' COMMENT 'ENABLED/DISABLED',
    `last_login_ip`  VARCHAR(64)              DEFAULT NULL,
    `last_login_at`  DATETIME                DEFAULT NULL,
    `created_at`     DATETIME         NOT NULL,
    `updated_at`     DATETIME         NOT NULL,
    `is_deleted`     TINYINT(1)      NOT NULL DEFAULT 0,
    PRIMARY KEY (`id`),
    UNIQUE KEY `uk_admin_username` (`username`)
) ENGINE = InnoDB
  DEFAULT CHARSET = utf8mb4
  COLLATE = utf8mb4_general_ci;

INSERT INTO `cqwm_admin_user`
(`username`, `password_hash`, `full_name`, `phone`, `email`, `status`, `created_at`, `updated_at`)
VALUES ('admin',
        '$2b$12$QEsRLVOdnFP/z1rdv7HrxupZIiIJTIvZnWZ.vFXedr3267SQfv3LK', -- 密码：Admin@123
        '平台管理员',
        '13800000000',
        'admin@example.com',
        'ENABLED',
        NOW(),
        NOW())
ON DUPLICATE KEY UPDATE username = VALUES(`username`);
