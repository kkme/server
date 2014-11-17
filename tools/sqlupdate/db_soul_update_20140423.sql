USE sevensoul;

SET FOREIGN_KEY_CHECKS=0;

ALTER TABLE `t_mars_member` DROP COLUMN `faction` ;


-- 插入数据版本信息
INSERT INTO t_version values ('20140423', NOW());