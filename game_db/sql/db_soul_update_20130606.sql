USE sevensoul;
ALTER TABLE `t_human`
  ADD COLUMN `specialLoginRewards` BLOB DEFAULT NULL AFTER `refineStages`;
-- 插入数据版本信息
INSERT INTO t_version values ('20130606', NOW());

