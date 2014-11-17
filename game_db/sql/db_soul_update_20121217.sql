USE sevensoul;
ALTER TABLE `t_human`
  ADD COLUMN `mainCityInfo` BLOB DEFAULT NULL AFTER `specialShopItems`;
-- 插入数据版本信息
INSERT INTO t_version values ('20121217', NOW());
