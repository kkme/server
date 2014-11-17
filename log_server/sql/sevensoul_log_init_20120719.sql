/*
Navicat MySQL Data Transfer

Source Server         : cfh
Source Server Version : 50520
Source Host           : localhost:3306
Source Database       : sevensoul_log

Target Server Type    : MYSQL
Target Server Version : 50520
File Encoding         : 65001

Date: 2012-07-19 18:00:23
*/

CREATE DATABASE /*!32312 IF NOT EXISTS*/ sevensoul_log DEFAULT CHARSET=utf8;

USE sevensoul_log;

SET FOREIGN_KEY_CHECKS=0;

-- ----------------------------
-- Table structure for `user_online_time`
-- ----------------------------
DROP TABLE IF EXISTS `user_online_time`;
CREATE TABLE `user_online_time` (
  `id` int AUTO_INCREMENT COMMENT '主键id',
  `account_id` bigint(20) NOT NULL COMMENT '账号id',
  `account_name` varchar(50) NOT NULL COMMENT '账号名称',
  `char_id` bigint(20) NOT NULL COMMENT '角色id',
  `char_name` varchar(50) DEFAULT NULL COMMENT '角色名称',
  `online_time` int(11) DEFAULT NULL COMMENT '在线时长（分钟计）',
  `biz_date` DATETIME DEFAULT NULL COMMENT '业务日期',
  `create_time` DATETIME DEFAULT null  COMMENT '创建日期',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;
