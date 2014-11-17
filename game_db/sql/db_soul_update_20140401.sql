USE sevensoul;

SET FOREIGN_KEY_CHECKS=0;

ALTER TABLE `t_legion_mine_member` DROP COLUMN `hasRankReward`;
ALTER TABLE `t_legion_mine_member`
  ADD COLUMN `rank` INT(11) DEFAULT 0 AFTER `isJoin`;


-- 插入数据版本信息
INSERT INTO t_version values ('20140401', NOW());