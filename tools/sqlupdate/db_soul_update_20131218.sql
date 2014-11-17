USE sevensoul;


SET FOREIGN_KEY_CHECKS=0;

-- ----------------------------
-- Table structure for t_blood_temple_honor
-- ----------------------------
DROP TABLE IF EXISTS `t_blood_temple_honor`;
CREATE TABLE `t_blood_temple_honor` (
  `id` bigint(20) NOT NULL DEFAULT '0',
  `honor` int(11) NOT NULL DEFAULT '0',
  `lastExtractTime` bigint(20) NOT NULL DEFAULT '0',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- ----------------------------
-- Table structure for t_blood_temple_log
-- ----------------------------
DROP TABLE IF EXISTS `t_blood_temple_log`;
CREATE TABLE `t_blood_temple_log` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT COMMENT 'id',
  `firstId` bigint(20) DEFAULT '0',
  `firstName` varchar(50) DEFAULT NULL,
  `firstLevel` int(5) DEFAULT NULL,
  `secondId` bigint(20) DEFAULT '0',
  `result` tinyint(1) DEFAULT '0',
  `lootTime` bigint(20) DEFAULT '0',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=2 DEFAULT CHARSET=utf8;

-- ----------------------------
-- Table structure for t_blood_temple_room
-- ----------------------------
DROP TABLE IF EXISTS `t_blood_temple_room`;
CREATE TABLE `t_blood_temple_room` (
  `id` int(5) NOT NULL,
  `ownerId` bigint(20) DEFAULT NULL,
  `ownerName` varchar(50) DEFAULT NULL,
  `ownerLevel` int(5) DEFAULT NULL,
  `ownerTitleId` int(5) DEFAULT '0',
  `lootTime` bigint(20) DEFAULT '0',
  `state` tinyint(1) DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- 插入数据版本信息
INSERT INTO t_version values ('20131218', NOW());
