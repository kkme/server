package com.hifun.soul.manageweb.log;

import java.util.HashMap;
import java.util.Map;

public enum ManageLogType {
	USER_ADD(1, "添加管理用户"),
	USER_DELETE(2, "删除管理用户"),
	USER_LIST(3, "查看用户列表"),
	USER_PERMISSION_UPDATE(4, "修改管理用户权限"),
	USER_LOGIN(5, "用户登录"),
	USER_LOGIN_OUT(6, "用户退出登录"),
	RUN_TIME_GET_REALM_INFO(7, "查看服务器运行信息"),
	RUN_TIME_START_EXTERNAL_SERVICE(8, "打开服务器对外服务"),
	RUN_TIME_STOP_EXTERNAL_SERVICE(9, "关闭服务器对外服务"),
	ACCOUNT_QUERY_ACCOUNT(10, "账号查询"),
	ACCOUNT_LOCK_ACCOUNT(11, "锁定账号"),	
	ACCOUNT_UNLOCK_ACCOUNT(12, "账号解锁"),
	CHARACTER_LIST(13,"查看角色信息"),
	CHARACTER_PROPERTY_INFO(14,"查看角色属性信息"),
	CHARACTER_BUILDING_INFO(15,"查看角色的建筑信息"),
	CHARACTER_ITEM_INFO(16,"查看角色的物品信息"),
	CHARACTER_FRIEND_INFO(17,"查看角色的好友信息"),
	CHARACTER_QUEST_INFO(18,"查看角色的任务信息"),
	CHARACTER_HOROSCOPE_INFO(19,"查看角色的星运信息"),
	CHARACTER_TECHNOLOGY_INFO(20,"查看角色的科技信息"),
	BULLETIN_LIST(21,"查看公告列表"),	
	BULLETIN_ADD(22,"添加公告"),
	BULLETIN_REMOVE(23,"移除公告"),
	SEND_MAIL_LIST(24,"查看邮件列表"),	
	SEND_MAIL(25,"发送邮件"),
	KICK_OFF_CHARACTER(26,"踢人下线"),
	QUERY_BASIC_PLAYER_LOG_LIST(27,"角色登录日志查询"),
	QUERY_BATTLE_LOG_LIST(28,"战斗日志查询"),
	QUERY_CHAT_LOG_LIST(29,"聊天日志查询"),
	QUERY_FRIEND_LOG_LIST(30,"好友日志查询"),
	QUERY_GM_COMMAND_LOG_LIST(31,"GM命令日志查询"),
	QUERY_HOROSCOPE_LOG_LIST(32,"占星日志查询"),
	QUERY_ITEM_LOG_LIST(33,"物品日志查询"),
	QUERY_MONEY_LOG_LIST(34,"货币系统日志查询"),
	QUERY_ONLINE_TIME_LOG_LIST(35,"登陆日志查询"),
	QUERY_PROPERTY_LOG_LIST(36,"角色属性日志查询"),
	QUERY_QUEST_LOG_LIST(37,"任务日志查询"),
	QUERY_ONLINE_NUMBER(38,"查询当前在线人数"),
	CHECK_ONLINE_STATE(39,"查询角色在线状态"),
	QUERY_QUESTION_LIST(40,"查询玩家反馈信息"),	
	ANSWER_QUESTION(41,"回复玩家反馈"),
	CHARACTER_STAGE_INFO(42,"查看角色的关卡信息"),
	CHARACTER_ELITE_STAGE_INFO(43,"查看角色的精英副本信息"),
	QUERY_PLAYER_STATE_STATISTIC(44,"查看玩家状态统计信息"),
	KICK_OFF_ALL_CHARACTER(45,"将所有玩家踢下线"),
	QUERY_GAME_SERVER_LIST(46,"查询游戏服务器列表"),
	CHANGE_GAME_SERVER(47,"切换游戏服务器"),
	UPDATE_MARKET_ACTIVITY_SETTING(48,"更新运营活动设置"),
	QUERY_ALL_MARKET_ACTIVITY_SETTINGS(49,"查看活动状态列表"),
	QUERY_RECHARGE_LOG_LIST(50,"充值日志查询"),
	QUERY_OPERATION_LOG_LIST(51,"gm后台操作日志查询"),
	SHOW_CHARACTER_SKILL_INFO(52,"角色技能查询"),
	SHOW_CHARACTER_MINE_INFO(53,"角色矿场查询"),
	SHOW_CHARACTER_QUESTION_INFO(54,"角色奇遇答题查询"),
	SHOW_STONE_COLLECT_INFO(55,"主城魔法石收集查询"),
	SHOW_LOGIN_REWARD_INFO(56,"连续登陆奖励查询"),
	SHOW_COSTNOTIFY_INFO(57,"消费提醒查询"),
	USER_PASSWORD_UPDATE(58,"修改密码"),
	MANAGER_UPDATE_USER_PASSWORD(59,"管理员修改密码"),
	QUERY_DB_ENTITY_OPERATION_INFO(60,"数据库操作实时信息查询"),	
	EDIT_BULLETIN(61,"编辑公告"),
	QUERY_CHARACTER_DISTRIBUTE(62,"角色分布统计"),
	SHOW_GIFT_INFO(63,"角色天赋查询"),
	SHOW_WARRIOR_INFO(64,"角色勇士之路查询"),
	SHOW_REFINE_INFO(65,"角色试炼信息查询"),
	STOP_GAME_SERVER(66,"停服"),
	QUERY_LOGIN_WALL_STATE(67,"查询登陆墙状态"),
	UPDATE_LOGIN_WALL_STATE(68,"更新登陆墙状态"),
	UPDATE_TIMING_MAIL(69,"更新定时邮件"),
	QUERY_RECHARGE_FLOW(70,"查询充值业务流水"),
	QUERY_MANAGE_SERVER_LIST(71,"查询管理服务器列表"),
	;
	
	private int logType;
	private String desc;
	private ManageLogType(int logType,String desc){
		this.logType = logType;
		this.desc = desc;
	}
	public int getLogType() {
		return logType;
	}
	
	public String getDesc() {
		return desc;
	}
	
	public static ManageLogType indexOf(int logType){
		if(allTypes.containsKey(logType)){
			return allTypes.get(logType);
		}
		return null;
	}
	private static Map<Integer,ManageLogType> allTypes = initAllTypes();
	
	private static Map<Integer, ManageLogType> initAllTypes(){
		Map<Integer,ManageLogType> result = new HashMap<Integer, ManageLogType>();
		for(ManageLogType type : ManageLogType.values()){
			result.put(type.getLogType(), type);
		}
		return result;
	}
}
