
use sevensoul;
-- ----------------------------
-- Table structure for `t_match_battle_role`
-- ----------------------------
DROP TABLE IF EXISTS `t_match_battle_role`;
CREATE TABLE `t_match_battle_role` (
  `id` bigint(20) NOT NULL COMMENT '角色id',
  `roleName` varchar(50) DEFAULT NULL COMMENT '角色名称',
  `consecutiveWinCount` int(4)  NULL DEFAULT '0' COMMENT '连胜次数',
  `maxConsecutiveWinCount` int(4) DEFAULT 0 COMMENT '最大连胜次数',
  `winCount` int(4) DEFAULT 0 COMMENT '胜利场数',
  `loseCount` int(4) DEFAULT 0 COMMENT '失败场数',
  `encourageRate` int(10) DEFAULT 0 COMMENT '鼓舞加成',
  `honourReward` int(10) DEFAULT 0 COMMENT '累计荣誉奖励',
  `coinReward` int(10) DEFAULT 0 COMMENT '累计金币奖励',
  `isWinInLastBattle` tinyint(1) DEFAULT '0' COMMENT '上一场战斗是否取得胜利',
  `streakWinRank` int(4) DEFAULT '0' COMMENT '连胜排行',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;


-- 插入数据版本信息
INSERT INTO t_version values ('20130602', NOW());