USE sevensoul;

ALTER TABLE `t_human`
  ADD COLUMN `humanTarget` BLOB DEFAULT NULL AFTER `humanMarsLoser`;

-- 插入数据版本信息
INSERT INTO t_version values ('20140113', NOW());
