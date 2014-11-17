USE sevensoul;

ALTER TABLE `t_human`
  ADD COLUMN `humanGodsoul` BLOB DEFAULT NULL AFTER `humanTechnology`;

-- 插入数据版本信息
INSERT INTO t_version values ('20131119', NOW());
