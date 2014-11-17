USE sevensoul;

SET FOREIGN_KEY_CHECKS=0;

-- ----------------------------
-- Table structure for t_legion_mine
-- ----------------------------
DROP TABLE IF EXISTS `t_legion_mine`;
CREATE TABLE `t_legion_mine` (
  `id` int(3) NOT NULL DEFAULT '0',
  `isDouble` tinyint(1) DEFAULT '0',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- ----------------------------
-- Table structure for t_legion_mine_member
-- ----------------------------
DROP TABLE IF EXISTS `t_legion_mine_member`;
CREATE TABLE `t_legion_mine_member` (
  `id` bigint(20) NOT NULL,
  `mineIndex` int(11) NOT NULL DEFAULT '0',
  `warExploit` int(11) DEFAULT NULL,
  `occupyValue` int(11) DEFAULT NULL,
  `occupyTime` bigint(20) DEFAULT NULL,
  `encourageRate` int(11) DEFAULT '0' COMMENT '???',
  `attackRate` int(11) DEFAULT NULL,
  `defenseRate` int(11) DEFAULT NULL,
  `isQuit` tinyint(1) DEFAULT NULL,
  `isJoin` tinyint(1) DEFAULT '0',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- 插入数据版本信息
INSERT INTO t_version values ('20140123', NOW());
