USE sevensoul;

ALTER TABLE `t_human`
  ADD COLUMN `humanTotalRechargeReward` BLOB DEFAULT NULL AFTER `humanSingleRechargeReward`;

-- 插入数据版本信息
INSERT INTO t_version values ('20131226', NOW());
