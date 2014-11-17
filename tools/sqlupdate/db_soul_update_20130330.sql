use sevensoul;
ALTER TABLE `t_account`
  ADD COLUMN `permissionType` int(11) unsigned zerofill DEFAULT 1 AFTER `state`;
  
-- 插入数据版本信息
INSERT INTO t_version values ('20130330', NOW());


