
-- add column `beSlaveSelfLevel` ,`beSlaveMasterLevel`

USE sevensoul;

SET FOREIGN_KEY_CHECKS=0;

-- ----------------------------
-- Table structure for t_prisoner
-- ----------------------------
DROP TABLE IF EXISTS `t_prisoner`;
CREATE TABLE `t_prisoner` (
  `id` bigint(20) NOT NULL DEFAULT '0',
  `humanName` varchar(50) DEFAULT NULL COMMENT '??',
  `humanLevel` int(11) DEFAULT '0' COMMENT '??',
  `identityType` tinyint(1) DEFAULT '1' COMMENT '??',
  `legionId` bigint(20) DEFAULT '0' COMMENT '??',
  `legionName` varchar(50) DEFAULT NULL COMMENT '????id',
  `masterId` bigint(20) DEFAULT '0',
  `buyArrestNum` int(11) DEFAULT '0',
  `refreshedArrestNum` int(11) DEFAULT '0',
  `arrestedNum` int(11) DEFAULT '0',
  `unlockedRoomNum` int(11) DEFAULT '0',
  `rescuedNum` int(11) DEFAULT '0',
  `interactedNum` int(11) DEFAULT '0',
  `extractedExperience` int(11) DEFAULT '0',
  `forHelpedNum` int(11) DEFAULT '0',
  `enemyIds` varchar(200) DEFAULT NULL,
  `arrestIds` varchar(200) DEFAULT NULL,
  `beSlaveTime` bigint(20) DEFAULT '0',
  `lastBeExtractedTime` bigint(20) DEFAULT '0',
  `lastBeInteractedTime` bigint(20) DEFAULT '0' COMMENT '??????',
  `lastInteractTime` bigint(20) DEFAULT '0' COMMENT '??????',
  `beSlaveSelfLevel` int(11) DEFAULT '0',
  `beSlaveMasterLevel` int(11) DEFAULT '0',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- 插入数据版本信息
INSERT INTO t_version values ('20131231', NOW());
