USE sevensoul;
ALTER TABLE `t_mail_send`
ADD COLUMN `itemNums`  varchar(200) NULL COMMENT '附件物品数量';

-- 更新旧邮件物品数量默认值（两个物品的邮件）
update t_mail_send set itemNums='1,1' 
where t_mail_send.id in (select id from (select id from t_mail_send where itemIds like '%,%') tableTemp);
-- 更新旧邮件物品数量默认值（一个物品的邮件）
update t_mail_send set itemNums='1' 
where t_mail_send.id in (select id from (select id from t_mail_send where itemNums is NULL) tableTemp);
-- 插入数据版本信息
INSERT INTO t_version values ('20130812', NOW());