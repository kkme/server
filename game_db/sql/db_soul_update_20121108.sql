
USE sevensoul;
ALTER TABLE `t_human`
  ADD COLUMN `eliteStageInfo` BLOB DEFAULT NULL AFTER `meditation`;
-- 插入数据版本信息
INSERT INTO t_version values ('20121108', NOW());