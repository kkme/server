USE sevensoul;

-- 添加字段 isUpFiveAndHigherVsLower
-- ----------------------------
-- Table structure for t_arena_notice
-- ----------------------------
DROP TABLE IF EXISTS `t_arena_notice`;
CREATE TABLE `t_arena_notice` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT COMMENT 'id',
  `roleId` bigint(20) NOT NULL DEFAULT '0',
  `roleName` varchar(50) DEFAULT NULL COMMENT '??',
  `otherRoleId` bigint(20) NOT NULL DEFAULT '0',
  `otherRoleName` varchar(50) DEFAULT NULL COMMENT '??????',
  `content` varchar(200) DEFAULT NULL COMMENT '??',
  `win` tinyint(1) DEFAULT NULL COMMENT '????',
  `battleTime` bigint(20) DEFAULT NULL,
  `isChallenger` tinyint(1) DEFAULT NULL,
  `rank` int(11) DEFAULT NULL,
  `isUpFiveAndHigherVsLower` tinyint(1) DEFAULT '0',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=53 DEFAULT CHARSET=utf8;

-- 插入数据版本信息
INSERT INTO t_version values ('20131213', NOW());
