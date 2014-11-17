USE sevensoul;

SET FOREIGN_KEY_CHECKS=0;

-- ----------------------------
-- Table structure for t_legion_boss
-- ----------------------------
DROP TABLE IF EXISTS `t_legion_boss`;
CREATE TABLE `t_legion_boss` (
  `id` int(11) NOT NULL DEFAULT '0',
  `remainBlood` int(11) DEFAULT NULL COMMENT '????',
  `bossState` int(11) DEFAULT NULL COMMENT '??',
  `joinPeopleNum` int(11) DEFAULT NULL COMMENT '??????',
  `killId` bigint(20) DEFAULT NULL COMMENT '??',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- ----------------------------
-- Table structure for t_legion_boss_role
-- ----------------------------
DROP TABLE IF EXISTS `t_legion_boss_role`;
CREATE TABLE `t_legion_boss_role` (
  `id` bigint(20) NOT NULL,
  `rank` int(11) NOT NULL DEFAULT '0',
  `name` varchar(50) DEFAULT NULL COMMENT '??',
  `damage` int(11) NOT NULL DEFAULT '0',
  `chargedstrikeRate` int(11) NOT NULL DEFAULT '0' COMMENT '???',
  `encourageRate` int(11) NOT NULL DEFAULT '0' COMMENT '???',
  `hasKillReward` tinyint(1) DEFAULT NULL COMMENT '????',
  `hasRankReward` tinyint(1) DEFAULT NULL COMMENT '????',
  `hasDamageReward` tinyint(1) DEFAULT NULL COMMENT '????',
  `isJoin` tinyint(1) DEFAULT '0',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- 插入数据版本信息
INSERT INTO t_version values ('20140120', NOW());
