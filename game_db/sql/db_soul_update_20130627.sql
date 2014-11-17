USE sevensoul;
ALTER TABLE `t_human`
ADD COLUMN `warriorInfo`  blob NULL COMMENT '勇士之路信息' AFTER `specialLoginRewards`;
-- 插入数据版本信息
INSERT INTO t_version values ('20130627', NOW());

