use sevensoul;

-- ----------------------------
-- Table structure for `t_mail_draft`
-- ----------------------------
DROP TABLE IF EXISTS `t_mail_draft`;
CREATE TABLE `t_mail_draft` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT,
  `sendHumanId` bigint(20) DEFAULT NULL COMMENT '发送人ID',
  `sendHumanName` varchar(50) DEFAULT NULL COMMENT '发送人姓名',
  `theme` varchar(50) DEFAULT NULL COMMENT '标题',
  `content` varchar(1000) DEFAULT NULL COMMENT '邮件内容',
  `isAttachment` bit(1) DEFAULT NULL COMMENT '是否有附件',
  `itemIds` varchar(200) DEFAULT NULL COMMENT '附件物品id列表',
  `sendTime` datetime DEFAULT NULL COMMENT '发送时间',
  `receiveHumanId` varchar(4000) DEFAULT NULL,
  `sendMemo` varchar(200) DEFAULT NULL COMMENT '发送备注',
  `expireDate` datetime DEFAULT NULL COMMENT '领取物品截止日期',
  `valid` bit(1) DEFAULT NULL COMMENT '是否有效',
  `sendState` tinyint(4) DEFAULT NULL COMMENT '发送状态：待发送，已发送，已取消',
  `createTime` datetime DEFAULT NULL COMMENT '创建时间',
  `lastEditTime` datetime DEFAULT NULL COMMENT '最近修改时间',
  `planSendTime` datetime DEFAULT NULL COMMENT '计划发送时间',
  `isTimingMail` bit(1) DEFAULT NULL COMMENT '是否为定时邮件',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=8 DEFAULT CHARSET=utf8;

-- 插入数据版本信息
INSERT INTO t_version values ('20130701', NOW());

