USE sevensoul;
SET FOREIGN_KEY_CHECKS=0;

-- ----------------------------
-- Table structure for t_prison_log
-- ----------------------------
DROP TABLE IF EXISTS `t_prison_log`;
CREATE TABLE `t_prison_log` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT COMMENT 'id',
  `logType` tinyint(1) DEFAULT '0',
  `firstId` bigint(20) DEFAULT '0',
  `secondId` bigint(20) DEFAULT '0',
  `thirdId` bigint(20) DEFAULT '0',
  `content` varchar(100) DEFAULT NULL COMMENT '??',
  `result` tinyint(1) DEFAULT '0',
  `operateTime` bigint(20) DEFAULT '0',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=40 DEFAULT CHARSET=utf8;

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
  `lastBeInteractedTime` bigint(20) DEFAULT '0' COMMENT '针对奴隶来说',
  `lastInteractTime` bigint(20) DEFAULT '0' COMMENT '针对奴隶来说',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- 插入数据版本信息
INSERT INTO t_version values ('20131206', NOW());
