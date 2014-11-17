USE sevensoul;

SET FOREIGN_KEY_CHECKS=0;

-- ----------------------------
-- Table structure for t_rank
-- ----------------------------
DROP TABLE IF EXISTS `t_rank`;
CREATE TABLE `t_rank` (
  `id` bigint(20) NOT NULL COMMENT 'id',
  `humanName` varchar(50) DEFAULT NULL COMMENT '角色名称',
  `occupation` int(4) DEFAULT NULL COMMENT '职业',
  `level` int(4) DEFAULT NULL COMMENT '等级',
  `legionId` bigint(20) DEFAULT '0',
  `legionName` varchar(20) DEFAULT NULL,
  `honor` int(11) DEFAULT '0',
  `isValid` bit(1) DEFAULT b'1' COMMENT '是否有效',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- ----------------------------
-- Table structure for t_rank_honor
-- ----------------------------
DROP TABLE IF EXISTS `t_rank_honor`;
CREATE TABLE `t_rank_honor` (
  `id` bigint(20) NOT NULL COMMENT 'id',
  `humanName` varchar(50) DEFAULT NULL COMMENT '????',
  `occupation` int(4) DEFAULT NULL COMMENT '??',
  `honor` int(11) DEFAULT '0',
  `level` int(4) DEFAULT NULL COMMENT '??',
  `legionId` bigint(20) DEFAULT '0',
  `legionName` varchar(20) DEFAULT NULL,
  `isValid` bit(1) DEFAULT b'1' COMMENT '????',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- ----------------------------
-- Table structure for t_rank_title
-- ----------------------------
DROP TABLE IF EXISTS `t_rank_title`;
CREATE TABLE `t_rank_title` (
  `id` bigint(20) NOT NULL COMMENT 'id',
  `humanName` varchar(50) DEFAULT NULL COMMENT '????',
  `occupation` int(4) DEFAULT NULL COMMENT '??',
  `title` int(4) DEFAULT NULL,
  `level` int(4) DEFAULT NULL COMMENT '??',
  `legionId` bigint(20) DEFAULT '0',
  `legionName` varchar(20) DEFAULT NULL,
  `honor` int(11) DEFAULT '0',
  `isValid` bit(1) DEFAULT b'1' COMMENT '????',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- ----------------------------
-- Table structure for t_rank_vip
-- ----------------------------
DROP TABLE IF EXISTS `t_rank_vip`;
CREATE TABLE `t_rank_vip` (
  `id` bigint(20) NOT NULL COMMENT 'id',
  `humanName` varchar(50) DEFAULT NULL COMMENT '????',
  `occupation` int(4) DEFAULT NULL COMMENT '??',
  `vipLevel` int(4) DEFAULT NULL,
  `level` int(4) DEFAULT NULL COMMENT '??',
  `legionId` bigint(20) DEFAULT '0',
  `legionName` varchar(20) DEFAULT NULL,
  `honor` int(11) DEFAULT '0',
  `isValid` bit(1) DEFAULT b'1' COMMENT '????',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;


-- 插入数据版本信息
INSERT INTO t_version values ('20131210', NOW());
