USE sevensoul;

SET FOREIGN_KEY_CHECKS=0;

-- ----------------------------
-- Table structure for t_abattoir_room
-- ----------------------------
ALTER TABLE `t_human`
  ADD COLUMN `humanSprite` BLOB DEFAULT NULL AFTER `humanPubSprite`;
ALTER TABLE `t_human`
  ADD COLUMN `humanSpriteBuff` BLOB DEFAULT NULL AFTER `humanSprite`;
ALTER TABLE `t_human`
  ADD COLUMN `humanSpriteBagCell` BLOB DEFAULT NULL AFTER `humanSpriteBuff`;
ALTER TABLE `t_human`
  ADD COLUMN `humanSpriteSlot` BLOB DEFAULT NULL AFTER `humanSpriteBagCell`;
-- 插入数据版本信息
INSERT INTO t_version values ('20131222', NOW());
