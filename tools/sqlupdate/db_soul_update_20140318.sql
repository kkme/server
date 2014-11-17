USE sevensoul;

SET FOREIGN_KEY_CHECKS=0;

ALTER TABLE `t_legion_boss_role`
  ADD COLUMN `stageReward` INT(11) DEFAULT 0 AFTER `isJoin`;



-- 插入数据版本信息
INSERT INTO t_version values ('20140318', NOW());