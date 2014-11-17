USE sevensoul;

SET FOREIGN_KEY_CHECKS=0;

ALTER TABLE `t_legion_mine_member` drop COLUMN `warExploit`;
ALTER TABLE `t_legion_mine_member`
  ADD COLUMN `hasRankReward` TINYINT(1) DEFAULT 0 AFTER `isJoin`;
ALTER TABLE `t_human`
  ADD COLUMN `humanLegionMineBattleReward` BLOB DEFAULT NULL AFTER `humanMagicPaper`;



-- 插入数据版本信息
INSERT INTO t_version values ('20140326', NOW());