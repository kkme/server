-- 此脚本用于清理所有数据库的数据;
use sevensoul;
-- 角色数据
truncate table t_human;
-- 竞技场数据
truncate table t_arena_member;
-- 竞技场公告数据
truncate table t_arena_notice;
-- 问答数据
truncate table t_human_question;
-- 接受邮件数据
truncate table t_mail_receive;
-- 发送邮件数据
truncate table t_mail_send;
-- 邮件草稿箱
truncate table t_mail_draft;
-- 排名数据
truncate table t_rank;
-- 充值
truncate table t_recharge;
-- 抽奖
truncate table t_turntable;
-- boss战
truncate table t_boss;
-- boss战
truncate table t_boss_role;
-- 全局活动状态
truncate table t_activity;
-- 神秘商店公告信息
truncate table t_special_shop_notify;
-- 全局变量
truncate table t_global_keyvalue;
-- 公告信息清除
truncate table t_bulletin;
-- 好友
truncate table t_friend;
-- 问答
truncate table t_question;
-- 好友战斗
truncate table t_friend_battle;
-- 好友聊天
truncate table t_friend_chat;
-- 匹配战
truncate table t_match_battle_role;
-- 充值表
truncate table t_qq_recharge;
-- 充值表
truncate table t_tencent_user_info;
-- 角斗场相关数据
truncate table t_abattoir_honor;
truncate table t_abattoir_log;
truncate table t_abattoir_room;
-- 嗜血神殿相关数据
truncate table t_blood_temple_honor; 
truncate table t_blood_temple_log;
truncate table t_blood_temple_room;
-- 军团相关数据
truncate table t_legion;
truncate table t_legion_member;
truncate table t_legion_apply;
truncate table t_legion_log;
-- 战神之巅成员
truncate table t_mars_member;
-- 个人排行榜
truncate table t_rank_honor;
truncate table t_rank_title;
truncate table t_rank_vip;
-- 战俘营
truncate table t_prison_log;
truncate table t_prisoner;
-- 阵营
truncate table t_faction_member;
-- 押运
truncate table t_escort;
truncate table t_escort_help;
truncate table t_escort_invite;
truncate table t_escort_legion_pray;
truncate table t_escort_log;
truncate table t_escort_rob_rank;
 -- 军团新功能
truncate table t_legion_building;
truncate table t_legion_honor;
truncate table t_legion_meditation_log;
truncate table t_legion_shop;
truncate table t_legion_technology;
-- 军团boss战
truncate table t_legion_boss;
truncate table t_legion_boss_role;
-- 军团矿战
truncate table t_legion_mine;
truncate table t_legion_mine_member;
