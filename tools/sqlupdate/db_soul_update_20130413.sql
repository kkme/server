use sevensoul;
ALTER TABLE `t_boss_role` CHANGE `id` `id` BIGINT(20) NOT NULL;
  
-- 插入数据版本信息
INSERT INTO t_version values ('20130413', NOW());


