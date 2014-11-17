USE sevensoul;
ALTER TABLE `t_arena_notice`
  ADD COLUMN `battleTime` bigint(20) DEFAULT NULL AFTER `win`;
ALTER TABLE `t_arena_notice`
  ADD COLUMN `isChallenger` boolean DEFAULT NULL AFTER `battleTime`;
ALTER TABLE `t_arena_notice`
  ADD COLUMN `rank` INT DEFAULT NULL AFTER `isChallenger`;
-- 插入数据版本信息
INSERT INTO t_version values ('20121227', NOW());