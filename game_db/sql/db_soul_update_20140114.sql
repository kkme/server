USE sevensoul;

ALTER TABLE `t_prisoner`
  ADD COLUMN `revoltedNum` INT(11) DEFAULT 0 AFTER `forHelpedNum`;

-- 插入数据版本信息
INSERT INTO t_version values ('20140114', NOW());
