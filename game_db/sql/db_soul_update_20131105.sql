USE sevensoul;

-- ----------------------------
-- Table structure for `t_title_rank`
-- ----------------------------
DROP TABLE IF EXISTS `t_title_rank`;
CREATE TABLE `t_title_rank` (
  `id` bigint(20) NOT NULL COMMENT 'id',
  `humanName` varchar(50) DEFAULT NULL COMMENT '角色名称',
  `occupation` int(4) DEFAULT NULL COMMENT '职业',
  `title` int(4) DEFAULT NULL,
  `level` int(4) DEFAULT NULL COMMENT '等级',
  `isValid` bit(1) DEFAULT b'1' COMMENT '是否有效',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- 插入数据版本信息
INSERT INTO t_version values ('20131105', NOW());