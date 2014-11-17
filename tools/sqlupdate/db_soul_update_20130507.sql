USE sevensoul;
ALTER TABLE `t_human`
  ADD COLUMN `stageStarRewards` BLOB DEFAULT NULL AFTER `stageStars`;
-- 插入数据版本信息
INSERT INTO t_version values ('20130507', NOW());
