USE sevensoul;

SET FOREIGN_KEY_CHECKS=0;

-- ----------------------------
-- Table structure for t_abattoir_room
-- ----------------------------
ALTER TABLE `t_human`
  ADD COLUMN `humanStarMap` BLOB DEFAULT NULL AFTER `humanSpriteBagCell`;
ALTER TABLE `t_human`
  ADD COLUMN `humanSign` BLOB DEFAULT NULL AFTER `humanStarMap`;
-- 插入数据版本信息
INSERT INTO t_version values ('20131223', NOW());
