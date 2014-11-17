
USE sevensoul;

ALTER TABLE `t_boss_role`
  DROP COLUMN `hasReward`;
ALTER TABLE `t_boss_role`
  ADD COLUMN `hasKillReward` boolean NULL COMMENT '击杀奖励' AFTER `encourageRate`;
ALTER TABLE `t_boss_role`
  ADD COLUMN `hasRankReward` boolean NULL COMMENT '排名奖励' AFTER `hasKillReward`;
ALTER TABLE `t_boss_role`
  ADD COLUMN `hasDamageReward` boolean NULL COMMENT '伤害奖励' AFTER `hasRankReward`;

-- 插入数据版本信息
INSERT INTO t_version values ('20121210', NOW());