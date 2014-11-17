use sevensoul;
drop table if exists `t_friend`;
create table `t_friend`
(
    id bigint(20) NOT NULL DEFAULT '0',
    humanUUID bigint(20) NOT NULL COMMENT '玩家id',
    humanName varchar(50) default NULL COMMENT '玩家名称' ,
    friendUUID bigint(20) default 0 COMMENT '好友id',
    friendName varchar(50) default NULL COMMENT '好友名称',
    friendLevel INT default 1 COMMENT '好友等级',
    friendOccupation INT default 0 COMMENT '好友职业',
    sendState boolean default false COMMENT '送出状态',
    getState INT default 1 COMMENT '领取状态',
    createTime bigint(20) default 0 COMMENT '创建时间',
    PRIMARY KEY (`id`)
)ENGINE=InnoDB DEFAULT CHARSET=utf8;

drop table if exists `t_friend_apply`;
create table `t_friend_apply`
(
    id bigint(20) NOT NULL DEFAULT '0',
    humanUUID bigint(20) NOT NULL COMMENT '玩家id',
    humanName varchar(50) default NULL COMMENT '玩家名称' ,
    applyUUID bigint(20) default 0 COMMENT '申请人id',
    applyName varchar(50) default NULL COMMENT '申请人名称',
    applyLevel INT default 1 COMMENT '申请人等级',
    applyOccupation INT default 0 COMMENT '申请人职业',
    applyTime bigint(20) default 0 COMMENT '申请时间',
    PRIMARY KEY (`id`)
)ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- 插入数据版本信息
INSERT INTO t_version values ('20130321', NOW());


