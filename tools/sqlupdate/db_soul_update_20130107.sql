-- 大小写敏感
ALTER TABLE `t_account` MODIFY COLUMN `password` varchar(50) CHARACTER SET utf8 COLLATE utf8_bin NOT NULL DEFAULT '';
ALTER TABLE `t_account` MODIFY COLUMN `userName` varchar(50) CHARACTER SET utf8 COLLATE utf8_bin NOT NULL DEFAULT '';
-- 插入数据版本信息
INSERT INTO t_version values ('20130107', NOW());