USE sevensoul;

ALTER TABLE `t_human`
  ADD COLUMN `humanNostrum` BLOB DEFAULT NULL AFTER `humanTarget`;

-- 插入数据版本信息
INSERT INTO t_version values ('20140217', NOW());
