USE sevensoul;
ALTER TABLE `t_human`
  ADD COLUMN `stageStars` BLOB DEFAULT NULL AFTER `mainCityInfo`;

-- 插入数据版本信息
INSERT INTO t_version values ('20130219', NOW());
