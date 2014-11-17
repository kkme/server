use sevensoul;
ALTER TABLE `t_human`
  DROP COLUMN `humanBuildings`;
ALTER TABLE `t_human`
  DROP COLUMN `humanFriends`;
ALTER TABLE `t_human`
  DROP COLUMN `humanFriendReward`;
  
-- 插入数据版本信息
INSERT INTO t_version values ('20130411', NOW());


