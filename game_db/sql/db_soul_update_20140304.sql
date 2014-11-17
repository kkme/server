USE sevensoul;

SET FOREIGN_KEY_CHECKS=0;

ALTER TABLE `t_human`
  ADD COLUMN `humanMagicPaper` BLOB DEFAULT NULL AFTER `humanLegionTask`;

-- 插入数据版本信息
INSERT INTO t_version values ('20140304', NOW());
