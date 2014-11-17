USE sevensoul;

ALTER TABLE `t_human`
  ADD COLUMN `humanSingleRechargeReward` BLOB DEFAULT NULL AFTER `humanSpriteSlot`;

-- 插入数据版本信息
INSERT INTO t_version values ('20131225', NOW());
