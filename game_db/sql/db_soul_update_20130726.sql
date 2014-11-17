USE sevensoul;
-- ----------------------------
-- Table structure for `t_qq_recharge`
-- ----------------------------
DROP TABLE IF EXISTS `t_qq_recharge`;
CREATE TABLE `t_qq_recharge` (
  `id` varchar(200) DEFAULT NULL COMMENT 'openId加&加billno',
  `humanId` bigint(20) DEFAULT NULL COMMENT '角色UUID',
  `openId` varchar(100) DEFAULT NULL COMMENT '腾讯下发的',
  `ts` varchar(100) DEFAULT NULL COMMENT '充值流水时间记录',
  `payItem` varchar(200) DEFAULT NULL COMMENT '购买物品信息',
  `token` varchar(200) DEFAULT NULL COMMENT '交易token',
  `billno` varchar(200) DEFAULT NULL COMMENT '交易流水号',
  `amt` varchar(200) DEFAULT NULL COMMENT '扣款金额(0.1Q点为单位)',
  `payamtCoins` varchar(200) DEFAULT NULL COMMENT '扣取的游戏币总数，单位为Q点',
  `pubacctPayamtCoins` varchar(200) DEFAULT NULL COMMENT '扣取的抵用券总金额，单位为Q点',
  `sended` bit(1) DEFAULT NULL COMMENT '是否已经给玩家发了奖励',
  `jsonValue` varchar(2000) DEFAULT NULL COMMENT '腾讯返回来的整体信息',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- add by will 
-- ----------------------------
-- Table structure for `t_tencent_user_info`
-- ----------------------------
DROP TABLE IF EXISTS `t_tencent_user_info`;
CREATE TABLE `t_tencent_user_info` (
`id` bigint(20) NOT NULL DEFAULT 0 COMMENT '主键：角色id',
`passportId` bigint(20) NULL COMMENT '账户id',
`openId` varchar(50) NULL COMMENT '腾讯开放平台用户Id',
`yellowVipLevel` int(11) NULL DEFAULT 0 COMMENT '黄钻等级',
`isYearYellowVip` int(11) NULL DEFAULT 0 COMMENT '是否为年费黄钻用户',
`isYellowHighVip` int(11) NULL DEFAULT 0 COMMENT '是否为豪华黄钻用户',
PRIMARY KEY (`id`)
)
ENGINE=InnoDB DEFAULT CHARACTER SET=utf8 ;

ALTER TABLE `t_friend`
DROP COLUMN `yellowVipLevel` ,
DROP COLUMN `isYearYellowVip`,
DROP COLUMN `isYellowHighVip`;

-- 插入数据版本信息
INSERT INTO t_version values ('20130726', NOW());