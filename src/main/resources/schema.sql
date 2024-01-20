CREATE DATABASE IF NOT EXISTS `bookstore` DEFAULT CHARACTER SET utf8mb4;

USE `bookstore`;

DROP TABLE IF EXISTS `user`;
CREATE TABLE `user`
(
    `user_id`  int          NOT NULL AUTO_INCREMENT,
    `username` varchar(255) NULL DEFAULT NULL unique,
    `password` varchar(255) NULL DEFAULT NULL,
    `name`     varchar(255) NULL DEFAULT NULL,
    `email`    varchar(255) NULL DEFAULT NULL,
    `age`      int          NULL DEFAULT NULL,
    `gender`   varchar(20)  NULL DEFAULT NULL,
    `role`     varchar(20)  NULL DEFAULT NULL,
    PRIMARY KEY (`user_id`)
) ENGINE = InnoDB
  AUTO_INCREMENT = 3
  CHARACTER SET = utf8mb4
  COLLATE = utf8mb4_general_ci
  ROW_FORMAT = Dynamic;

DROP TABLE IF EXISTS `book`;
CREATE TABLE `book`
(
    `book_id`     int            NOT NULL AUTO_INCREMENT,
    `title`       varchar(255)   NULL DEFAULT NULL,
    `author`      varchar(255)   NULL DEFAULT NULL,
    `price`       decimal(10, 2) NULL DEFAULT NULL,
    `category`    varchar(255)   NULL DEFAULT NULL,
    `description` text           NULL,
    `sales`       int            NULL DEFAULT 0,
    `stock`       int            NULL DEFAULT 0,
    PRIMARY KEY (`book_id`)
) ENGINE = InnoDB
  AUTO_INCREMENT = 21
  CHARACTER SET = utf8mb4
  COLLATE = utf8mb4_general_ci
  ROW_FORMAT = Dynamic;