USE sevensoul;

SET FOREIGN_KEY_CHECKS=0;

ALTER TABLE t_legion ADD COLUMN `legionCoin` int(11) DEFAULT '0' AFTER `notice`;

-- ----------------------------
-- Table structure for t_legion_building
-- ----------------------------
DROP TABLE IF EXISTS `t_legion_building`;
CREATE TABLE `t_legion_building` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT COMMENT 'id',
  `legionId` varchar(50) DEFAULT '0' COMMENT '????',
  `buildingType` int(11) DEFAULT '0' COMMENT '??',
  `buildingLevel` int(4) DEFAULT '0',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- ----------------------------
-- Table structure for t_legion_honor
-- ----------------------------
DROP TABLE IF EXISTS `t_legion_honor`;
CREATE TABLE `t_legion_honor` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT COMMENT 'id',
  `legionId` varchar(50) DEFAULT '0' COMMENT '????',
  `titleId` int(11) DEFAULT '0' COMMENT '??',
  `exchangeNum` int(11) DEFAULT '0',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- ----------------------------
-- Table structure for t_legion_meditation_log
-- ----------------------------
DROP TABLE IF EXISTS `t_legion_meditation_log`;
CREATE TABLE `t_legion_meditation_log` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT COMMENT 'id',
  `legionId` bigint(20) NOT NULL DEFAULT '0',
  `content` varchar(100) DEFAULT NULL COMMENT '??',
  `operateTime` bigint(20) DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=2 DEFAULT CHARSET=utf8;

-- ----------------------------
-- Table structure for t_legion_shop
-- ----------------------------
DROP TABLE IF EXISTS `t_legion_shop`;
CREATE TABLE `t_legion_shop` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT COMMENT 'id',
  `legionId` varchar(50) DEFAULT '0' COMMENT '????',
  `itemId` int(11) DEFAULT '0' COMMENT '??',
  `buyNum` int(11) DEFAULT '0',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- ----------------------------
-- Table structure for t_legion_technology
-- ----------------------------
DROP TABLE IF EXISTS `t_legion_technology`;
CREATE TABLE `t_legion_technology` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT COMMENT 'id',
  `legionId` varchar(50) DEFAULT '0' COMMENT '????',
  `technologyType` int(11) DEFAULT '0' COMMENT '??',
  `technologyLevel` int(11) DEFAULT '0',
  `currentCoin` int(11) DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- 插入数据版本信息
INSERT INTO t_version values ('20140226', NOW());
