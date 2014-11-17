USE sevensoul;

SET FOREIGN_KEY_CHECKS=0;

-- ----------------------------
-- Table structure for t_abattoir_room
-- ----------------------------
DROP TABLE IF EXISTS `t_abattoir_room`;
CREATE TABLE `t_abattoir_room` (
  `id` int(5) NOT NULL,
  `ownerId` bigint(20) NOT NULL,
  `ownerName` varchar(50) DEFAULT NULL,
  `ownerLevel` int(5) DEFAULT NULL,
  `lootTime` bigint(20) DEFAULT '0',
  `state` tinyint(1) DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- ----------------------------
-- Table structure for t_abattoir_log
-- ----------------------------
DROP TABLE IF EXISTS `t_abattoir_log`;
CREATE TABLE `t_abattoir_log` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT COMMENT 'id',
  `firstId` bigint(20) DEFAULT '0',
  `firstName` varchar(50) DEFAULT NULL,
  `firstLevel` int(5) DEFAULT NULL,
  `secondId` bigint(20) DEFAULT '0',
  `result` tinyint(1) DEFAULT '0',
  `lootTime` bigint(20) DEFAULT '0',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- ----------------------------
-- Table structure for t_abattoir_honor
-- ----------------------------
DROP TABLE IF EXISTS `t_abattoir_honor`;
CREATE TABLE `t_abattoir_honor` (
  `id` bigint(20) NOT NULL DEFAULT '0',
  `honor` int(11) NOT NULL DEFAULT '0',
  `lastExtractTime` bigint(20) NOT NULL DEFAULT '0',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

ALTER TABLE `t_human`
  ADD COLUMN `humanPubSprite` BLOB DEFAULT NULL AFTER `yellowVipRewardState`;
-- 插入数据版本信息
INSERT INTO t_version values ('20131216', NOW());
