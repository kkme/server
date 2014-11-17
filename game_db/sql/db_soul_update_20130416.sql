use sevensoul;
drop TABLE if exists `t_friend`;
drop TABLE if exists `t_friend_apply`;
drop table if exists `t_friend`;
create table `t_friend`
(
    id bigint(20) NOT NULL DEFAULT '0',
    humanName varchar(50) default NULL COMMENT '玩家名称' ,
    humanLevel INT default 1 COMMENT '好友等级',
    humanOccupation INT default 0 COMMENT '好友职业',
    friendIds varchar(3000) default NULL COMMENT '好友列表' ,
    sendEnergyToSelfFriendIds varchar(3000) default NULL COMMENT '给自己送体力的好友列表' ,
    sendEnergyToOtherFriendIds varchar(3000) default NULL COMMENT '自己送出体力的好友列表' ,
    recievedEnergyFriendIds varchar(3000) default NULL COMMENT '自己已经接收过体力的好友列表' ,
    selfSendFriendApplyIds varchar(3000) default NULL COMMENT '自己发出的好友请求' ,
    otherSendFriendApplyIds varchar(3000) default NULL COMMENT '别人向自己发送的好友申请' ,
    PRIMARY KEY (`id`)
)ENGINE=InnoDB DEFAULT CHARSET=utf8;
  
-- 插入数据版本信息
INSERT INTO t_version values ('20130416', NOW());

