
USE sevensoul;

ALTER TABLE `t_arena_member`
  ADD COLUMN `occupation` INT default 0 AFTER `rankRewardState`;

-- 插入数据版本信息
INSERT INTO t_version values ('20121127', NOW());