USE sevensoul;

SET FOREIGN_KEY_CHECKS=0;

-- ----------------------------
-- Table structure for t_escort
-- ----------------------------
DROP TABLE IF EXISTS `t_escort`;
CREATE TABLE `t_escort` (
  `id` bigint(20) NOT NULL COMMENT 'id',
  `monsterType` tinyint(1) DEFAULT NULL,
  `ownerId` bigint(20) DEFAULT '0' COMMENT '????',
  `ownerName` varchar(50) DEFAULT NULL COMMENT '??',
  `ownerLevel` int(4) DEFAULT '0',
  `helperId` bigint(20) DEFAULT '0' COMMENT '????',
  `helperName` varchar(50) DEFAULT NULL COMMENT '??',
  `helperLevel` int(4) DEFAULT '0',
  `beRobbedNum` int(4) DEFAULT '0',
  `encourageRate` int(11) DEFAULT NULL,
  `escortTime` bigint(20) DEFAULT NULL,
  `endTime` bigint(20) DEFAULT '0' COMMENT '??',
  `escortRewardCoin` int(11) DEFAULT NULL,
  `helpLevelDiff` int(11) DEFAULT NULL,
  `escortRewardState` tinyint(1) DEFAULT NULL,
  `escortState` tinyint(1) DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- ----------------------------
-- Table structure for t_escort_help
-- ----------------------------
DROP TABLE IF EXISTS `t_escort_help`;
CREATE TABLE `t_escort_help` (
  `id` bigint(20) NOT NULL COMMENT 'id',
  `remainHelpNum` int(11) DEFAULT NULL,
  `rewardCoin` int(11) DEFAULT '0' COMMENT '????',
  `rewardState` tinyint(11) DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- ----------------------------
-- Table structure for t_escort_invite
-- ----------------------------
DROP TABLE IF EXISTS `t_escort_invite`;
CREATE TABLE `t_escort_invite` (
  `id` bigint(20) NOT NULL COMMENT 'id',
  `inviteFriendId` bigint(20) DEFAULT '0' COMMENT '????',
  `inviteState` bigint(20) DEFAULT '0' COMMENT '????',
  `agreeEndTime` bigint(20) DEFAULT '0' COMMENT '??',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- ----------------------------
-- Table structure for t_escort_legion_pray
-- ----------------------------
DROP TABLE IF EXISTS `t_escort_legion_pray`;
CREATE TABLE `t_escort_legion_pray` (
  `id` bigint(20) NOT NULL COMMENT 'id',
  `prayMemberId` bigint(20) DEFAULT NULL,
  `prayMemberName` varchar(50) DEFAULT NULL,
  `endTime` bigint(20) DEFAULT '0' COMMENT '????',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- ----------------------------
-- Table structure for t_escort_log
-- ----------------------------
DROP TABLE IF EXISTS `t_escort_log`;
CREATE TABLE `t_escort_log` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT COMMENT 'id',
  `firstId` bigint(20) DEFAULT '0' COMMENT '????',
  `firstName` varchar(50) DEFAULT NULL COMMENT '??',
  `secondId` bigint(20) DEFAULT '0' COMMENT '????',
  `secondName` varchar(50) DEFAULT '0',
  `monserName` varchar(50) DEFAULT NULL COMMENT '??',
  `robCoin` int(4) DEFAULT '0',
  `insertTime` bigint(20) DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- ----------------------------
-- Table structure for t_escort_rob_rank
-- ----------------------------
DROP TABLE IF EXISTS `t_escort_rob_rank`;
CREATE TABLE `t_escort_rob_rank` (
  `id` bigint(20) NOT NULL COMMENT 'id',
  `robberName` varchar(50) DEFAULT NULL,
  `yesterdayRobCoin` int(11) DEFAULT NULL,
  `todayRobCoin` int(11) DEFAULT '0' COMMENT '????',
  `rewardState` tinyint(11) DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;


-- 插入数据版本信息
INSERT INTO t_version values ('20140221', NOW());
