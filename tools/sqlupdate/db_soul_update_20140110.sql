USE sevensoul;

SET FOREIGN_KEY_CHECKS=0;

-- ----------------------------
-- Table structure for t_mars_member
-- ----------------------------
DROP TABLE IF EXISTS `t_mars_member`;
CREATE TABLE `t_mars_member` (
  `id` bigint(20) NOT NULL DEFAULT '0',
  `humanName` varchar(50) DEFAULT NULL COMMENT '??',
  `humanLevel` int(11) DEFAULT '0' COMMENT '??',
  `occupation` tinyint(1) DEFAULT '0',
  `todayKillValue` int(11) DEFAULT '0' COMMENT '????id',
  `totalKillValue` int(11) DEFAULT '0' COMMENT '????????',
  `rewardCoin` int(11) DEFAULT NULL,
  `rewardPrestige` int(11) DEFAULT NULL,
  `rewardState` int(11) DEFAULT NULL,
  `faction` tinyint(1) DEFAULT '0',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;


-- 插入数据版本信息
INSERT INTO t_version values ('20140110', NOW());
