USE sevensoul;
ALTER TABLE `t_recharge`
ADD COLUMN `rechargeType`  int NULL COMMENT '充值类型',
ADD COLUMN `memo`  varchar(200) NULL COMMENT '充值备注';
-- 插入数据版本信息
INSERT INTO t_version values ('20130708', NOW());