USE sevensoul;
ALTER TABLE `t_human`
  ADD COLUMN `humanSkillSlots` blob DEFAULT NULL AFTER `costNotifys`;
-- 插入数据版本信息
INSERT INTO t_version values ('20121031', NOW());
