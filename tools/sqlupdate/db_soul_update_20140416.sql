USE sevensoul;

SET FOREIGN_KEY_CHECKS=0;

ALTER TABLE `t_legion_mine_member`
  ADD COLUMN `isLegionWin` INT(11) DEFAULT 0 AFTER `rank`;


-- 插入数据版本信息
INSERT INTO t_version values ('20140416', NOW());