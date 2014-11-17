USE sevensoul;
ALTER TABLE `t_human`
  ADD COLUMN `mine` BLOB DEFAULT NULL AFTER `eliteStageInfo`;
-- 插入数据版本信息
INSERT INTO t_version values ('20121212', NOW());
