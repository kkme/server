USE sevensoul;
-- 创建索引
ALTER TABLE t_rank ADD INDEX rank_level_index(level);
-- 插入数据版本信息
INSERT INTO t_version values ('20130102', NOW());