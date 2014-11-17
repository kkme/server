use sevensoul;
alter table t_mail_send change column `itemId` `itemIds` varchar(200) NULL COMMENT '附件物品id列表' ;
-- 插入数据版本信息
INSERT INTO t_version values ('20130605', NOW());

