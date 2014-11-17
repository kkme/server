-- change column state to ownerType


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
  `ownerType` tinyint(1) DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- ----------------------------
-- Table structure for t_blood_temple_room
-- ----------------------------
DROP TABLE IF EXISTS `t_blood_temple_room`;
CREATE TABLE `t_blood_temple_room` (
  `id` int(5) NOT NULL,
  `ownerId` bigint(20) DEFAULT NULL,
  `ownerOccupationType` tinyint(1) DEFAULT '0',
  `ownerName` varchar(50) DEFAULT NULL,
  `ownerLevel` int(5) DEFAULT NULL,
  `ownerTitleId` int(5) DEFAULT '0',
  `lootTime` bigint(20) DEFAULT '0',
  `ownerType` tinyint(1) DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;
-- 插入数据版本信息
INSERT INTO t_version values ('20131220', NOW());
