USE sevensoul;

-- ----------------------------
-- Table structure for `t_legion`
-- ----------------------------
DROP TABLE IF EXISTS `t_legion`;
CREATE TABLE `t_legion` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT,
  `legionName` varchar(50) NOT NULL,
  `commanderId` bigint(20) NOT NULL,
  `commanderName` varchar(255) DEFAULT NULL,
  `legionLevel` int(11) DEFAULT NULL,
  `memberLimit` int(11) DEFAULT NULL,
  `memberNum` int(11) DEFAULT NULL,
  `experience` int(11) DEFAULT NULL,
  `notice` varchar(200) DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- ----------------------------
-- Table structure for `t_legion_apply`
-- ----------------------------
DROP TABLE IF EXISTS `t_legion_apply`;
CREATE TABLE `t_legion_apply` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT COMMENT 'id',
  `applyHumanGuid` bigint(20) DEFAULT NULL,
  `applyLegionId` bigint(20) DEFAULT NULL COMMENT '????',
  `applyHumanName` varchar(50) DEFAULT NULL COMMENT '??',
  `applyHumanLevel` int(11) DEFAULT NULL,
  `applyArenaRank` int(11) DEFAULT NULL COMMENT '??',
  `applyStatus` tinyint(1) DEFAULT '1' COMMENT '????',
  `applyTime` bigint(20) DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- ----------------------------
-- Table structure for `t_legion_log`
-- ----------------------------
DROP TABLE IF EXISTS `t_legion_log`;
CREATE TABLE `t_legion_log` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT COMMENT 'id',
  `legionId` bigint(20) NOT NULL DEFAULT '0',
  `content` varchar(100) DEFAULT NULL COMMENT '??',
  `operateTime` bigint(20) DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- ----------------------------
-- Table structure for t_legion_member
-- ----------------------------
DROP TABLE IF EXISTS `t_legion_member`;
CREATE TABLE `t_legion_member` (
  `id` bigint(20) NOT NULL COMMENT 'id',
  `legionId` varchar(50) DEFAULT '0' COMMENT '????',
  `memberName` varchar(50) DEFAULT NULL COMMENT '??',
  `level` int(4) DEFAULT '0',
  `position` tinyint(3) DEFAULT '0',
  `positionName` varchar(10) DEFAULT NULL,
  `totalContribution` int(11) DEFAULT '0' COMMENT '??',
  `todayContribution` int(11) DEFAULT '0' COMMENT '????',
  `offLineTime` bigint(20) DEFAULT '0',
  `todayTaskScore` int(11) DEFAULT NULL,
  `medal` int(11) DEFAULT NULL,
  `yesterdayScoreRank` int(11) DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- 插入数据版本信息
INSERT INTO t_version values ('20131114', NOW());