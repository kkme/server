USE sevensoul;

SET FOREIGN_KEY_CHECKS=0;


ALTER TABLE `t_human`
  ADD COLUMN `humanMarsRoom` BLOB DEFAULT NULL AFTER `humanTotalRechargeReward`;
ALTER TABLE `t_human`
  ADD COLUMN `humanMarsLoser` BLOB DEFAULT NULL AFTER `humanMarsRoom`;


-- 插入数据版本信息
INSERT INTO t_version values ('20140107', NOW());
