USE sevensoul;
ALTER TABLE `t_human`
  ADD COLUMN `yellowVipRewardState` BLOB NULL DEFAULT NULL COMMENT '黄钻礼包领取状态信息' AFTER `warriorInfo`;
 
ALTER TABLE `t_friend`
ADD COLUMN `yellowVipLevel`  int NULL default 0 COMMENT '黄钻等级',
ADD COLUMN `isYearYellowVip`  bit NULL default 0 COMMENT '是否为年费黄钻会员';
-- 插入数据版本信息
INSERT INTO t_version values ('20130723', NOW());