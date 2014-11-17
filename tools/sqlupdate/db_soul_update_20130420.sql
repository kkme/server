use sevensoul;

ALTER TABLE `t_boss_role`
  ADD COLUMN `isJoin` boolean default false;
  
-- 插入数据版本信息
INSERT INTO t_version values ('20130420', NOW());