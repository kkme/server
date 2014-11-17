use sevensoul;
alter table t_bulletin modify id INT NOT NULL AUTO_INCREMENT COMMENT 'ID' ;
-- 插入数据版本信息
INSERT INTO t_version values ('20130515', NOW());
