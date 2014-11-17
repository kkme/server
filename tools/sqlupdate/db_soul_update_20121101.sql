
USE sevensoul;
ALTER TABLE `t_human`
  ADD COLUMN `meditation` BLOB DEFAULT NULL AFTER `humanSkillSlots`;
-- 插入数据版本信息
INSERT INTO t_version values ('20121101', NOW());