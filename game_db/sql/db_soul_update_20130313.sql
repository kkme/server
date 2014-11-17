use sevensoul;
drop table if exists `t_market_activity_config`;
create table `t_market_activity_config`
(
    id int(10) primary key AUTO_INCREMENT COMMENT 'id',
    type tinyint NOT NULL COMMENT '活动系统类型',
    enable tinyint default 0 NULL COMMENT '开启状态' ,
    roleLevel tinyint default 0 NULL COMMENT '开启等级限制',
    vipLevel tinyint default 0 NULL COMMENT '开启的vip等级限制'
);

insert into t_market_activity_config (type) values (1);

-- 插入数据版本信息
INSERT INTO t_version values ('20130313', NOW());


