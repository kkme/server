USE sevensoul;
ALTER TABLE `t_human`
  ADD COLUMN `refineMaps` BLOB DEFAULT NULL AFTER `gift`;
ALTER TABLE `t_human`
  ADD COLUMN `refineStages` BLOB DEFAULT NULL AFTER `refineMaps`;
-- 插入数据版本信息
INSERT INTO t_version values ('20130530', NOW());