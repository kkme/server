use sevensoul;

ALTER TABLE `t_human`
  DROP COLUMN `unharvestGemItems`;
  
-- 插入数据版本信息
INSERT INTO t_version values ('20130417', NOW());