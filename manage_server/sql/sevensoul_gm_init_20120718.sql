/*
Navicat MySQL Data Transfer

Source Server         : john
Source Server Version : 50520
Source Host           : localhost:3306
Source Database       : sevensoul_gm

Target Server Type    : MYSQL
Target Server Version : 50520
File Encoding         : 65001

Date: 2012-07-18 18:00:23
*/

CREATE DATABASE /*!32312 IF NOT EXISTS*/ sevensoul_gm DEFAULT CHARSET=utf8;

USE sevensoul_gm;

SET FOREIGN_KEY_CHECKS=0;

-- ----------------------------
-- Table structure for `t_user_info`
-- ----------------------------
DROP TABLE IF EXISTS `t_user_info`;
CREATE TABLE `t_user_info` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `userName` varchar(50) DEFAULT NULL,
  `password` varchar(50) DEFAULT NULL,
  `permissions` text,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=3 DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records of t_user_info
-- ----------------------------
INSERT INTO `t_user_info` VALUES ('2', 'msadmin', '42ec66a78d5f7151553391b7631e87=8', '1,2,3,4,5,6,8,9,10,7,11,12');
