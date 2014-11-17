use sevensoul;
ALTER TABLE `t_human_question`
  ADD COLUMN `answeredQuestionIds` varchar(50) DEFAULT NULL AFTER `lastWeeklyResetTime`;
-- 插入数据版本信息
INSERT INTO t_version values ('20130320', NOW());