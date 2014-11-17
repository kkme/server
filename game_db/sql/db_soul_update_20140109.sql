drop table if exists `t_faction_member`;
create table `t_faction_member`
(
    guid bigint(20) NOT NULL DEFAULT '0',
    factionType INT default 1 COMMENT '阵营类型',
    PRIMARY KEY (`guid`)
)ENGINE=InnoDB DEFAULT CHARSET=utf8;


-- 插入数据版本信息
INSERT INTO t_version values ('20140109', NOW());
