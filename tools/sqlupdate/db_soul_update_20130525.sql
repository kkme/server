USE sevensoul;
ALTER TABLE `t_human`
  ADD COLUMN `gift` BLOB DEFAULT NULL AFTER `stageStarRewards`;
-- 插入数据版本信息
INSERT INTO t_version values ('20130525', NOW());