use sevensoul;
alter table t_friend add column latestFriendIds varchar(3000) default NULL COMMENT '别人向自己发送的好友申请' ;

-- ----------------------------
-- Table structure for `t_friend_battle`
-- ----------------------------
DROP TABLE IF EXISTS `t_friend_battle`;
CREATE TABLE `t_friend_battle` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT COMMENT 'id',
  `roleId` bigint(20) NOT NULL DEFAULT '0',
  `roleName` varchar(50) DEFAULT NULL COMMENT '名称',
  `otherRoleId` bigint(20) NOT NULL DEFAULT '0',
  `otherRoleName` varchar(50) DEFAULT NULL COMMENT '其他角色名称',
  `win` tinyint(1) DEFAULT NULL COMMENT '是否战胜',
  `battleTime` bigint(20) DEFAULT NULL,
  `isChallenger` tinyint(1) DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=3 DEFAULT CHARSET=utf8;

-- 插入数据版本信息
INSERT INTO t_version values ('20130524', NOW());
