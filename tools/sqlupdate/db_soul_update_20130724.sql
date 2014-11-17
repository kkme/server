USE sevensoul;
ALTER TABLE `t_friend`
ADD COLUMN `isYellowHighVip`  bit NULL default 0 COMMENT '是否为豪华黄钻会员';
-- 插入数据版本信息
INSERT INTO t_version values ('20130724', NOW());