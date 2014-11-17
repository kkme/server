/*
Navicat MySQL Data Transfer

Source Server         : john
Source Server Version : 50520
Source Host           : localhost:3306
Source Database       : sevensoul

Target Server Type    : MYSQL
Target Server Version : 50520
File Encoding         : 65001

Date: 2012-06-09 11:40:48
*/

--
-- Create schema sevensoul
--
DROP DATABASE IF EXISTS sevensoul;
CREATE DATABASE /*!32312 IF NOT EXISTS*/ sevensoul DEFAULT CHARSET=utf8;

USE sevensoul;

SET FOREIGN_KEY_CHECKS=0;

-- ----------------------------
-- Table structure for `t_account`
-- ----------------------------
DROP TABLE IF EXISTS `t_account`;
CREATE TABLE `t_account` (
  `id` bigint(20) NOT NULL DEFAULT '0',
  `userName` varchar(50) DEFAULT NULL,
  `password` varchar(50) DEFAULT NULL,
  `state` int(11) unsigned zerofill DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records of t_account
-- ----------------------------
INSERT INTO `t_account` VALUES ('8888', 'john', 'john',1);
INSERT INTO `t_account` VALUES ('9999', 'dick', 'dick',1);

-- ----------------------------
-- Table structure for `t_human`
-- ----------------------------
DROP TABLE IF EXISTS `t_human`;
CREATE TABLE `t_human` (
  `id` bigint(20) NOT NULL DEFAULT '0',
  `name` varchar(50) DEFAULT NULL,
  `passportId` bigint(20) DEFAULT NULL,
  `baseProperties` blob,
  `otherProperties` blob,
  `humanQuestData` blob,
  `humanQuestFinishData` blob,
  `humanItems` blob,
  `humanBuildings` blob,
  `humanFriends` blob,
  `humanHoroscope` blob,
  `humanStargazer` blob,
  `humanTechnology` blob,
  `humanDailyRewardBox` blob,
  `humanGuide` blob,
  `humanStageReward` blob,
  `humanStageMapState` blob,
  `humanStageDrama` blob,
  `humanCd` blob,
  `humanFriendReward` blob,
  `humanCarriedSkills` blob,
  `unharvestGemItems` blob,
  `loginRewards` blob,
  `costNotifys` blob,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 ROW_FORMAT=COMPRESSED;
		
-- ----------------------------
-- Table structure for `t_friend_chat`
-- ----------------------------
DROP TABLE IF EXISTS `t_friend_chat`;
CREATE TABLE `t_friend_chat` (
  `id` bigint(20) NOT NULL DEFAULT '0',
  `fromRoleName` varchar(50) DEFAULT NULL,
  `fromRoleId` bigint(20) DEFAULT NULL,
  `toRoleId` bigint(20) DEFAULT NULL,
  `content` varchar(100) DEFAULT NULL,
  `chatDate` bigint(20) DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- -----------------------------------------------------
-- Table structure for `t_rank`
-- -----------------------------------------------------
DROP TABLE IF EXISTS `t_rank`;
CREATE  TABLE `t_rank` (
  `id` BIGINT(20) NOT NULL COMMENT 'id' ,
  `humanName` VARCHAR(50) NULL DEFAULT NULL COMMENT '角色名称' ,
  `occupation` INT(4) NULL DEFAULT NULL  COMMENT '职业' ,
  `level` INT(4) NULL DEFAULT NULL  COMMENT '等级' ,
  `isValid` BIT(1) NULL DEFAULT b'1'  COMMENT '是否有效' ,
  PRIMARY KEY (`id`) 
 )ENGINE = InnoDB DEFAULT CHARSET = utf8;
 
  -- -----------------------------------------------------
-- Table structure for `t_mail_send`
-- -----------------------------------------------------
DROP TABLE IF EXISTS `t_mail_send`;
 CREATE  TABLE `t_mail_send` (
  `id` BIGINT(20) NOT NULL ,
  `sendHumanId` BIGINT(20) NULL COMMENT '发送人ID' ,
`sendHumanName` VARCHAR(50) NULL COMMENT '发送人姓名' ,
  `theme` VARCHAR(50) NULL COMMENT '标题' ,
  `content` VARCHAR(1000) NULL COMMENT '邮件内容' ,
  `mailType` TINYINT NULL COMMENT '邮件类型 [系统邮件（1）; 用户邮件（2）]' ,
  `isAttachment` BIT NULL COMMENT '是否有附件' ,
  `itemId` INT NULL COMMENT '物品1Id' ,
  `sendTime` DATETIME NULL COMMENT '发送时间' ,
  `receiveHumanId` VARCHAR(4000) NULL ,
  `sendMemo` VARCHAR(200) NULL COMMENT '发送备注' ,
    `expireDate` DATETIME NULL COMMENT '领取物品截止日期' ,
  PRIMARY KEY (`id`) )
ENGINE = InnoDB  DEFAULT CHARSET = utf8;


-- -----------------------------------------------------
-- Table structure for  `t_mail_receive`
-- -----------------------------------------------------
DROP TABLE IF EXISTS `t_mail_receive` ;
CREATE  TABLE `t_mail_receive` (
  `id` BIGINT NOT NULL ,
  `mailId` BIGINT NULL ,
  `receiveHumanId` BIGINT NULL ,
  `isRead` BIT NULL COMMENT '是否已读' ,
  `isPickUp` BIT NULL COMMENT '是否已领取' ,
  PRIMARY KEY (`id`) )
ENGINE = InnoDB  DEFAULT CHARSET = utf8;

-- -----------------------------------------------------
-- Table structure for  `t_bulletin`
-- -----------------------------------------------------
DROP TABLE IF EXISTS `t_bulletin` ;
CREATE  TABLE `t_bulletin` (
  `id` INT NOT NULL ,
  `content` VARCHAR(4000) NULL  COMMENT '公告内容',
  `publishTime` DATETIME NULL COMMENT '发布时间',
  `showTime` INT NULL  COMMENT '客户端显示时长(s)',
  `level` INT NULL COMMENT '公告级别' ,
  `bulletinType` INT NULL COMMENT '公告类型' ,
  `valid` BIT NULL COMMENT '是否有效' ,
  `startDate` DATETIME NULL COMMENT '开始日期',
  `endDate` DATETIME NULL COMMENT '结束日期',
  `startTime` DATETIME NULL COMMENT '开始时间',
  `endTime` DATETIME NULL COMMENT '结束时间',
  `lastPublishTime` DATETIME NULL  COMMENT '上次发布时间',
  `publishInterval` INT NULL COMMENT '发布时间间隔' ,
  `createTime` DATETIME NULL COMMENT '公告创建时间' ,
  PRIMARY KEY (`id`) )
ENGINE = InnoDB  DEFAULT CHARSET = utf8;

-- -----------------------------------------------------
-- Table structure for  `t_human_question`
-- -----------------------------------------------------
DROP TABLE IF EXISTS `t_human_question` ;
CREATE  TABLE  `t_human_question` (
  `id` INT NOT NULL AUTO_INCREMENT COMMENT 'ID' ,
  `humanId` BIGINT(20) NULL COMMENT '角色id' ,
  `totalScore` INT NULL COMMENT '问答积分总数' ,
  `questionIndex` INT NULL COMMENT '今日答题序号' ,
  `questionId` INT NULL COMMENT '题目id' ,
  `usableBlessNum` INT NULL COMMENT '可用的祈福次数' ,
  `buyBlessTimes` INT NULL COMMENT '购买祈福次数' ,
  `scoreExchangeState` VARCHAR(20) NULL COMMENT '积分兑换情况' ,
  `lastDailyResetTime` BIGINT(20) NULL COMMENT '上次每日重置时间',
  `lastWeeklyResetTime` BIGINT(20) NULL COMMENT '上次每周重置时间',
  PRIMARY KEY (`id`) )
ENGINE = InnoDB  DEFAULT CHARSET = utf8 COMMENT = '玩家的问答活动数据';

-- -----------------------------------------------------
-- Table structure for  `t_recharge`
-- -----------------------------------------------------
DROP TABLE IF EXISTS `t_recharge` ;
CREATE  TABLE `t_recharge` (
  `id` INT AUTO_INCREMENT NOT NULL ,
  `humanId` BIGINT(20) NULL  COMMENT '角色id',
  `passportId` BIGINT(20) NULL COMMENT '账户id',
  `rechargeCost` INT NULL COMMENT '充值花费' ,
  `rechargeNum` INT NULL  COMMENT '充值获得的二级货币总数',
  `rewardNum` INT NULL COMMENT '奖励的二级货币数量' ,
  `isFirstRecharge` BIT NULL COMMENT '是否首充' ,
  `rechargeTime` DATETIME NULL COMMENT '充值时间' ,
  PRIMARY KEY (`id`) )
ENGINE = InnoDB  DEFAULT CHARSET = utf8;

-- -----------------------------------------------------
-- Table structure for `t_turntable`
-- -----------------------------------------------------
DROP TABLE IF EXISTS `t_turntable`;
CREATE  TABLE `t_turntable` (
  `id` BIGINT(20) NOT NULL AUTO_INCREMENT COMMENT 'id' ,
  `roleName` VARCHAR(50) NULL DEFAULT NULL COMMENT '角色名称' ,
  `rewardName` VARCHAR(100) NULL DEFAULT NULL  COMMENT '奖品名称' ,
  PRIMARY KEY (`id`) 
 )ENGINE = InnoDB DEFAULT CHARSET = utf8;

DROP TABLE IF EXISTS `t_version`;
CREATE TABLE `t_version` (
  `version` varchar(32) NOT NULL,
  `updateTime` datetime NOT NULL,
  PRIMARY KEY (`version`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- 插入数据版本信息
INSERT INTO t_version values ('20120408', NOW());
