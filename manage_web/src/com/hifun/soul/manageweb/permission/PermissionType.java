package com.hifun.soul.manageweb.permission;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * 权限枚举
 * <p>
 * 1.如果扩展的权限仅仅只是某个权限的附属权限(绑定权限),比如说USER_ADD_FROWARD和USER_ADD,
 * 前者只是后者的一个转向操作;那么对于此种权限需要覆写isMainPermission()方法,并且让该方法返回false;
 * </p>
 * <p>
 * 2.如果某个权限比如USER_ADD有附属权限,如USER_ADD_FROWARD;那么这类权限需要覆写getBindPermissions()
 * 方法,给此权限添加相应的附属权限
 * </p>
 * <p>
 * 3.如果想给某个权限的方法添加中文描述,可以覆写getDesc()方法返回相应的描述
 * </p>
 * 
 * @author crazyjohn
 * 
 */
public enum PermissionType {

	USER_ADD(1, "用户管理", "addUser") {
		@Override
		public String getDesc() {
			return "添加管理用户";
		}

		@Override
		public List<PermissionType> getBindPermissions() {
			List<PermissionType> permissions = super.getBindPermissions();
			permissions.add(USER_ADD_FROWARD);
			return permissions;
		}
	},

	USER_ADD_FROWARD(2, "用户管理", "addUserForward") {
		@Override
		public boolean isMainPermission() {
			return false;
		}
	},

	USER_DELETE(3, "用户管理", "deleteUser") {
		@Override
		public String getDesc() {
			return "删除管理用户";
		}
	},

	USER_LIST(4, "用户管理", "getUserList"),

	USER_PERMISSION_UPDATE(5, "用户管理", "updateUserPermission") {
		@Override
		public String getDesc() {
			return "修改管理用户权限";
		}

		@Override
		public List<PermissionType> getBindPermissions() {
			List<PermissionType> permissions = super.getBindPermissions();
			permissions.add(USER_PERMISSION_UPDATE_FROWARD);
			return permissions;
		}
	},

	USER_PERMISSION_UPDATE_FROWARD(6, "用户管理", "updateUserPermissionForward") {
		@Override
		public boolean isMainPermission() {
			return false;
		}
	},

	USER_LOGIN_OUT(7, "用户管理", "loginOut") {
		@Override
		public boolean isMainPermission() {
			return false;
		}
	},
	RUN_TIME_GET_REALM_INFO(8, "服务器运行时管理", "getRealmInfo") {
		@Override
		public String getDesc() {
			return "查看服务器运行信息";
		}
	},
	RUN_TIME_START_EXTERNAL_SERVICE(9, "服务器运行时管理", "startExternalService"){
		@Override
		public String getDesc() {
			return "打开服务";
		}
	},
	RUN_TIME_STOP_EXTERNAL_SERVICE(10, "服务器运行时管理", "stopExternalService"){
		@Override
		public String getDesc() {
			return "关闭服务";
		}
	},
	ACCOUNT_QUERY_ACCOUNT_FORWARD(11, "账号管理", "queryAccountForward") {
		@Override
		public boolean isMainPermission() {
			return false;
		}
	},
	ACCOUNT_QUERY_ACCOUNT(12, "账号管理", "queryAccount") {
		@Override
		public String getDesc() {
			return "账号查询";
		}
		@Override
		public List<PermissionType> getBindPermissions() {
			List<PermissionType> permissions = super.getBindPermissions();
			permissions.add(ACCOUNT_QUERY_ACCOUNT_FORWARD);
			return permissions;
		}
	},
	ACCOUNT_LOCK_ACCOUNT(13, "账号管理", "lockAccount") {
		@Override
		public String getDesc() {
			return "锁定账号";
		}
		@Override
		public List<PermissionType> getBindPermissions() {
			List<PermissionType> permissions = super.getBindPermissions();
			permissions.add(ACCOUNT_LOCK_ACCOUNT_FORWARD);
			return permissions;
		}
	},
	ACCOUNT_LOCK_ACCOUNT_FORWARD(14, "账号管理", "lockAccountForward") {
		@Override
		public boolean isMainPermission() {
			return false;
		}
	},
	ACCOUNT_UNLOCK_ACCOUNT(15, "账号管理", "unlockAccount"){
		@Override
		public String getDesc() {
			return "账号解锁";
		}
	},
	CHARACTER_LIST(16,"角色管理","queryCharacterList"){
		@Override
		public String getDesc() {
			return "角色查询";
		}
	},
	CHARACTER_PROPERTY_INFO(17,"角色管理","showCharacterPropertyInfo"){
		@Override
		public String getDesc() {
			return "查看角色属性信息";
		}		
	},
	CHARACTER_ITEM_INFO(19,"角色管理","showCharacterItemInfo"){
		@Override
		public String getDesc() {
			return "查看角色的物品信息";
		}		
	},
	CHARACTER_FRIEND_INFO(20,"角色管理","showCharacterFriendInfo"){
		@Override
		public String getDesc() {
			return "查看角色的好友信息";
		}		
	},
	CHARACTER_QUEST_INFO(21,"角色管理","showCharacterQuestInfo"){
		@Override
		public String getDesc() {
			return "查看角色的任务信息";
		}		
	},
	CHARACTER_HOROSCOPE_INFO(22,"角色管理","showCharacterHoroscopeInfo"){
		@Override
		public String getDesc() {
			return "查看角色的星运信息";
		}		
	},
	CHARACTER_TECHNOLOGY_INFO(23,"角色管理","showCharacterTechnologyInfo"){
		@Override
		public String getDesc() {
			return "查看角色的科技信息";
		}		
	},
	BULLETIN_LIST(25,"公告管理","queryBulletinList"){
		@Override
		public String getDesc() {
			return "公告列表";
		}		
	},
	BULLETIN_ADD_FORWARD(26,"公告管理","addBulletinForward"){
		@Override
		public String getDesc() {
			return "添加公告跳转";
		}		
	},
	BULLETIN_ADD(27,"公告管理","addBulletin"){
		@Override
		public String getDesc() {
			return "添加公告";
		}		
		@Override
		public List<PermissionType> getBindPermissions() {
			List<PermissionType> permissions = super.getBindPermissions();
			permissions.add(BULLETIN_ADD_FORWARD);
			return permissions;
		}
	},
	BULLETIN_REMOVE(28,"公告管理","removeBulletin"){
		@Override
		public String getDesc() {
			return "移除公告";
		}
	},
	SEND_MAIL_LIST(29,"邮件管理","querySendMailList"){
		@Override
		public String getDesc() {
			return "邮件列表";
		}
	},
	SEND_MAIL_FORWARD(30,"邮件管理","sendMailForward"){
		@Override
		public String getDesc() {
			return "发送邮件转向";
		}
	},
	SEND_MAIL(31,"邮件管理","sendMail"){
		@Override
		public String getDesc() {
			return "发送邮件";
		}
		@Override
		public List<PermissionType> getBindPermissions() {
			List<PermissionType> permissions = super.getBindPermissions();
			permissions.add(SEND_MAIL_FORWARD);
			return permissions;
		}
	},
	KICK_OFF_CHARACTER(32,"踢人下线","kickOffCharacter"){
		@Override
		public String getDesc() {
			return "踢人下线";
		}		
	},
	QUERY_BASIC_PLAYER_LOG_LIST(33,"日志查询","queryBasicPlayerLogList"){
		@Override
		public String getDesc() {
			return "角色基本日志查询";
		}		
	},
	QUERY_BATTLE_LOG_LIST(34,"日志查询","queryBattleLogList"){
		@Override
		public String getDesc() {
			return "战斗日志查询";
		}		
	},
	QUERY_CHAT_LOG_LIST(35,"日志查询","queryChatLogList"){
		@Override
		public String getDesc() {
			return "聊天日志查询";
		}		
	},
	QUERY_FRIEND_LOG_LIST(36,"日志查询","queryFriendLogList"){
		@Override
		public String getDesc() {
			return "好友日志查询";
		}		
	},
	QUERY_GM_COMMAND_LOG_LIST(37,"日志查询","queryGMCommandLogList"){
		@Override
		public String getDesc() {
			return "GM命令日志查询";
		}		
	},
	QUERY_HOROSCOPE_LOG_LIST(38,"日志查询","queryHoroscopeLogList"){
		@Override
		public String getDesc() {
			return "占星日志查询";
		}		
	},
	QUERY_ITEM_LOG_LIST(39,"日志查询","queryItemLogList"){
		@Override
		public String getDesc() {
			return "物品日志查询";
		}		
	},
	QUERY_MONEY_LOG_LIST(40,"日志查询","queryMoneyLogList"){
		@Override
		public String getDesc() {
			return "货币系统日志查询";
		}		
	},
	QUERY_ONLINE_TIME_LOG_LIST(41,"日志查询","queryOnlineTimeLogList"){
		@Override
		public String getDesc() {
			return "登陆日志查询";
		}		
	},
	QUERY_PROPERTY_LOG_LIST(42,"日志查询","queryPropertyLogList"){
		@Override
		public String getDesc() {
			return "角色属性日志查询";
		}		
	},
	QUERY_QUEST_LOG_LIST(43,"日志查询","queryQuestLogList"){
		@Override
		public String getDesc() {
			return "任务日志查询";
		}		
	},
	QUERY_ONLINE_NUMBER(44,"角色管理","queryCurrentOnlineNum"){
		@Override
		public String getDesc() {
			return "查询当前在线人数";
		}		
	},
	CHECK_ONLINE_STATE(45,"角色管理","checkOnlineState"){
		@Override
		public String getDesc() {
			return "查询角色在线状态";
		}		
	},
	QUERY_QUESTION_LIST(46,"玩家反馈管理","queryQuestionList"){
		@Override
		public String getDesc() {
			return "查询玩家反馈信息";
		}		
	},
	ANSWER_QUESTION_FORWARD(47,"玩家反馈管理","answerQuestionForward"){
		@Override
		public boolean isMainPermission() {
			return false;
		}
		@Override
		public String getDesc() {
			return "回复玩家反馈跳转";
		}		
	},
	ANSWER_QUESTION(48,"玩家反馈管理","answerQuestion"){
		@Override
		public String getDesc() {
			return "回复玩家反馈";
		}		
	},
	CHARACTER_STAGE_INFO(49,"角色管理","showStageInfo"){
		@Override
		public String getDesc() {
			return "查看角色的关卡信息";
		}		
	},
	QUERY_PLAYER_STATE_STATISTIC(51,"角色管理","queryPlayerStateStatistic"){
		@Override
		public String getDesc() {
			return "查看玩家状态统计信息";
		}		
	},
	KICK_OFF_ALL_CHARACTER(52,"角色管理","kickOffAllCharacter"){
		@Override
		public String getDesc() {
			return "将所有玩家踢下线";
		}		
	},
	QUERY_GAME_SERVER_LIST(53,"服务器运行时管理","queryGameServerList"){
		@Override
		public String getDesc() {
			return "查询游戏服务器列表";
		}		
	},
	CHANGE_GAME_SERVER(54,"服务器运行时管理","changeGameServer"){
		@Override
		public String getDesc() {
			return "切换游戏服务器";
		}		
	},
	UPDATE_MARKET_ACTIVITY_SETTING(55,"运营活动管理","updateMarketActivitySetting"){
		@Override
		public String getDesc() {
			return "更新活动设置";
		}		
	},
	QUERY_ALL_MARKET_ACTIVITY_SETTINGS(56,"运营活动管理","queryAllMarketActivitySettings"){
		@Override
		public String getDesc() {
			return "查看活动状态列表";
		}		
	},
	QUERY_RECHARGE_LOG_LIST(57,"日志查询","queryRechargeLogList"){
		@Override
		public String getDesc() {
			return "充值日志查询";
		}	
	},
	QUERY_OPERATION_LOG_LIST(58,"日志查询","queryOperationLogList"){
		@Override
		public String getDesc() {
			return "gm后台操作日志查询";
		}	
	},
	UPDATE_ACCOUNT_INFO(59,"账号管理","updateAccountInfo"){
		@Override
		public String getDesc() {
			return "更新账号信息";
		}	
	},
	QUERY_ENERGY_LOG_LIST(60,"日志查询","queryEnergyLogList"){
		@Override
		public String getDesc() {
			return "体力日志查询";
		}	
	}
	,
	QUERY_EXPERIENCE_LOG_LIST(61,"日志查询","queryExperienceLogList"){
		@Override
		public String getDesc() {
			return "经验日志查询";
		}	
	}
	,
	QUERY_HONOUR_LOG_LIST(62,"日志查询","queryHonourLogList"){
		@Override
		public String getDesc() {
			return "荣誉日志查询";
		}	
	}
	,
	QUERY_TECH_POINT_LOG_LIST(63,"日志查询","queryTechPointLogList"){
		@Override
		public String getDesc() {
			return "科技点日志查询";
		}	
	}	,
	query_Skill_Point_Log_List(64,"日志查询","querySkillPointLogList"){
		@Override
		public String getDesc() {
			return "技能点日志查询";
		}	
	},
	SHOW_CHARACTER_SKILL_INFO(65,"角色管理","showCharacterSkillInfo"){
		@Override
		public String getDesc() {
			return "角色技能查询";
		}	
	},
	SHOW_CHARACTER_MINE_INFO(66,"角色管理","showCharacterMineInfo"){
		@Override
		public String getDesc() {
			return "角色矿场查询";
		}	
	},
	SHOW_CHARACTER_QUESTION_INFO(67,"角色管理","showCharacterQuestionInfo"){
		@Override
		public String getDesc() {
			return "角色奇遇答题查询";
		}	
	},
	SHOW_STONE_COLLECT_INFO(68,"角色管理","showStoneCollectInfo"){
		@Override
		public String getDesc() {
			return "主城魔法石收集查询";
		}	
	},
	SHOW_LOGIN_REWARD_INFO(69,"角色管理","showLoginRewardInfo"){
		@Override
		public String getDesc() {
			return "连续登陆奖励查询";
		}	
	},
	SHOW_COSTNOTIFY_INFO(70,"角色管理","showCostnotifyInfo"){
		@Override
		public String getDesc() {
			return "消费提醒查询";
		}	
	},
	USER_PASSWORD_UPDATE_FORWARD(71, "用户管理", "updateUserPasswordForward") {
		@Override
		public String getDesc() {
			return "修改密码跳转";
		}
		@Override
		public boolean isMainPermission() {
			return false;
		}
	},
	USER_PASSWORD_UPDATE(72, "用户管理", "updateUserPassword") {
		@Override
		public String getDesc() {
			return "修改密码";
		}
		@Override
		public List<PermissionType> getBindPermissions() {
			List<PermissionType> permissions = super.getBindPermissions();
			permissions.add(USER_PASSWORD_UPDATE_FORWARD);
			return permissions;
		}
	},
	MANAGER_UPDATE_PASSWORD_FORWARD(73, "用户管理", "managerUpdateUserPasswordForward") {
		@Override
		public String getDesc() {
			return "管理员修改用户密码跳转";
		}
		@Override
		public boolean isMainPermission() {
			return false;
		}
	},
	MANAGER_UPDATE_USER_PASSWORD(74, "用户管理", "managerUpdateUserPassword") {
		@Override
		public String getDesc() {
			return "管理员修改用户密码";
		}
		@Override
		public List<PermissionType> getBindPermissions() {
			List<PermissionType> permissions = super.getBindPermissions();
			permissions.add(MANAGER_UPDATE_PASSWORD_FORWARD);
			return permissions;
		}
	},
	QUERY_DB_ENTITY_OPERATION_INFO(75, "服务器运行时管理", "queryDBEntityOperationInfo") {
		@Override
		public String getDesc() {
			return "数据库操作实时信息查询";
		}
	},
	COPY_BULLETIN(76, "公告管理", "copyBulletin") {
		@Override
		public String getDesc() {
			return "复制公告内容";
		}
	},
	EIDT_BULLETIN(77, "公告管理", "editBulletin") {
		@Override
		public String getDesc() {
			return "编辑公告";
		}
	},
	COPY_MAIL(78, "邮件管理", "copyMail") {
		@Override
		public String getDesc() {
			return "转发邮件";
		}
	},
	QUERY_TIMING_MAIL_LIST(79, "邮件管理", "queryTimingMailList") {
		@Override
		public String getDesc() {
			return "查看定时邮件列表";
		}
	},
	CANCEL_TIMING_MAIL(80, "邮件管理", "cancelTimingMail") {
		@Override
		public String getDesc() {
			return "取消定时邮件";
		}
	},
	QUERY_CHARACTER_DISTRIBUTE(81, "角色管理", "queryCharacterDistribute") {
		@Override
		public String getDesc() {
			return "角色分布统计";
		}
	},
	SHOW_GIFT_INFO(82, "角色管理", "showGiftInfo") {
		@Override
		public String getDesc() {
			return "天赋信息查询";
		}
	},
	SHOW_WARRIOR_INFO(83, "角色管理", "showWarriorInfo") {
		@Override
		public String getDesc() {
			return "勇士之路信息查询";
		}
	},
	SHOW_REFINE_INFO(84, "角色管理", "showRefineInfo") {
		@Override
		public String getDesc() {
			return "试炼信息查询";
		}
	},
	STOP_GAME_SERVER(85, "服务器运行时管理", "stopGameServer") {
		@Override
		public String getDesc() {
			return "停止服务器";
		}
	},
	QUERY_LOGIN_WALL_STATE(86, "服务器运行时管理", "queryLoginWallState") {
		@Override
		public String getDesc() {
			return "查询登陆墙状态";
		}
	},
	UPDATE_LOGIN_WALL_STATE(87, "服务器运行时管理", "updateLoginWallState") {
		@Override
		public String getDesc() {
			return "更新登陆墙状态";
		}
	}, 
	UPDATE_TIMING_MAIL(88,"邮件管理","updateTimingMail") {
		@Override
		public String getDesc() {
			return "更新定时邮件";
		}
		@Override
		public List<PermissionType> getBindPermissions() {
			List<PermissionType> permissions = super.getBindPermissions();
			permissions.add(UPDATE_TIMING_MAIL_FORWARD);
			return permissions;
		}
	},
	UPDATE_TIMING_MAIL_FORWARD(89,"邮件管理","updateTimingMailForward"){
		@Override
		public boolean isMainPermission(){
			return false;
		}
	},
	QUERY_RECHARGE_FLOW(90,"充值管理","queryRechargeFlow"){
		@Override
		public String getDesc() {
			return "查询充值业务流水";
		}
	},
	QUERY_MANAGE_SERVER_LIST(91,"运行时管理","queryManageServerList"){
		@Override
		public String getDesc() {
			return "查询管理服务器列表";
		}
	}
	,
	;

	private int type;

	private String module;

	private String action;

	private static Map<Integer, PermissionType> typeMap = new HashMap<Integer, PermissionType>();

	private static Map<String, PermissionType> methodMap = new HashMap<String, PermissionType>();

	private static Map<String, List<PermissionType>> moduleMap = new HashMap<String, List<PermissionType>>();
	static {
		for (PermissionType permission : PermissionType.values()) {
			typeMap.put(permission.getType(), permission);
			methodMap.put(permission.getAction(), permission);
			initModuleMap(permission);
		}
	}

	PermissionType(int type, String module, String method) {
		this.type = type;
		this.module = module;
		this.action = method;
	}

	/**
	 * 初始化模块信息
	 * 
	 * @param permission
	 */
	private static void initModuleMap(PermissionType permission) {
		// 添加进去的必须是主权限
		if (!permission.isMainPermission()) {
			return;
		}
		List<PermissionType> moduleOfPermissions = moduleMap.get(permission
				.getModule());
		if (moduleOfPermissions == null) {
			moduleOfPermissions = new ArrayList<PermissionType>();
			moduleMap.put(permission.getModule(), moduleOfPermissions);
		}
		moduleOfPermissions.add(permission);
	}

	public static PermissionType valueOf(int permissionType) {
		return typeMap.get(permissionType);
	}

	/**
	 * 获取模块信息
	 * 
	 * @return 得到的map是只读的
	 */
	public static Map<String, List<PermissionType>> getModuleMap() {
		return Collections.unmodifiableMap(moduleMap);
	}

	/**
	 * 根据方法名称获取方法描述
	 * 
	 * @param method
	 *            方法名称
	 * @return 如果有此描述返回对应描述;否则返回method;
	 */
	public static String methodToDesc(String method) {
		if (methodMap.get(method) != null) {
			return methodMap.get(method).getDesc();
		}
		return method;
	}

	public int getType() {
		return type;
	}

	public String getModule() {
		return module;
	}

	public String getAction() {
		return action;
	}

	/**
	 * 获取方法描述
	 * 
	 * @return 默认返回的是方法名;
	 */
	public String getDesc() {
		return action;
	}

	/**
	 * 判断是否是主权限
	 * <p>
	 * 非主权限指的是类似USER_ADD_FROWAR这种只是负责用户添加时候转向的,必须要和USER_ADD绑定进行赋权
	 * </p>
	 * 
	 * @return
	 */
	protected boolean isMainPermission() {
		return true;
	}

	/**
	 * 获取绑定的权限
	 * 
	 * @return 缺省情况下返回的集合中只有当前权限,子类可以根据需要去覆盖
	 */
	protected List<PermissionType> getBindPermissions() {
		List<PermissionType> permissions = new ArrayList<PermissionType>();
		permissions.add(this);
		return permissions;
	}

	/**
	 * 生成权限字符串
	 * 
	 * @return 根据此权限以及它的绑定权限生成字符窜
	 */
	public String toPermissionString() {
		StringBuilder sb = new StringBuilder();
		for (int i = 0; i < this.getBindPermissions().size(); i++) {
			sb.append(this.getBindPermissions().get(i).getType());
			if (i < this.getBindPermissions().size() - 1) {
				sb.append(",");
			}
		}
		return sb.toString();
	}
}
