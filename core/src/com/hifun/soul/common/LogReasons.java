package com.hifun.soul.common;

import java.lang.annotation.Documented;
import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * 日志系统的日志原因定义
 * 
 * 
 */
public interface LogReasons {

	@Documented
	@Retention(RetentionPolicy.RUNTIME)
	@Target( { ElementType.FIELD, ElementType.TYPE })
	public @interface ReasonDesc {
		/**
		 * 原因的文字描述
		 * 
		 * @return
		 */
		String value();
	}

	@Documented
	@Retention(RetentionPolicy.RUNTIME)
	@Target( { ElementType.FIELD, ElementType.TYPE })
	public @interface LogDesc {
		/**
		 * 日志描述
		 * 
		 * @return
		 */
		String desc();
	}

	/**
	 * LogReason的通用接口
	 */
	public static interface ILogReason {
		/**
		 * 取得原因的序号
		 * 
		 * @return
		 */
		public int getReason();

		/**
		 * 获取原因的文本
		 * 
		 * @return
		 */
		public String getReasonText();
	}

	/**
	 * 物品变更原因
	 * 
	 */
	@LogDesc(desc = "物品更新")
	public enum ItemLogReason implements ILogReason {
		/** 整理背包 */
		@ReasonDesc("整理背包")
		TIDY(0, ""),
		/** 加载背包 */
		@ReasonDesc("加载背包")
		LOAD(1, ""),
		/** 装备物品 */
		@ReasonDesc("装备物品")
		TAKEON(2, ""),
		/** 卸下物品 */
		@ReasonDesc("卸下装备物品")
		TAKEOFF(3, ""),
		/** 移动物品 */
		@ReasonDesc("移动物品")
		MOVE(4, "fromBag:{0};fromBagIndex:{1};toBag:{2};toBagIndex:{3}"),
		/** 销毁物品 */
		@ReasonDesc("销毁物品")
		DELETE(5, ""),
		/** 收获宝石 */
		@ReasonDesc("收获宝石")
		HARVEST_GEM(6, ""),
		/** 宝石合成 */
		@ReasonDesc("宝石合成")
		SYNTHESIS_GEM(7, ""),
		/** GM命令添加物品 */
		@ReasonDesc("gm命令添加物品")
		GM_COMMAND(8, ""),
		/** 采矿 */
		@ReasonDesc("采矿")
		HARVEST_MINE(9, ""),
		/** 采木材 */
		@ReasonDesc("采木材")
		HARVEST_WOOD(10, ""),
		/** 装备制作 */
		@ReasonDesc("装备制作")
		EQUIP_MAKE(11, ""),
		/** 装备强化 */
		@ReasonDesc("装备强化")
		EQUIP_UPGRADE(12, ""),
		/** 宝石镶嵌 */
		@ReasonDesc("宝石镶嵌")
		GEM_EMBED(13, ""),
		/** 宝石卸下 */
		@ReasonDesc("宝石卸下")
		GEM_EXTRACT(14, ""),
		/** 登陆奖励 */
		@ReasonDesc("登陆奖励")
		LOGIN_REWARD(15, ""),
		/** 邮件奖励 */
		@ReasonDesc("邮件奖励")
		MAIL_REWARD(16, ""),
		/** 商城购买 */
		@ReasonDesc("商城购买")
		MALL(17, ""),
		/** 商店购买 */
		@ReasonDesc("商店购买")
		SHOP_BUY(18, ""),
		/** 商店出售 */
		@ReasonDesc("商店出售")
		SHOP_SELL(19, ""),
		/** 战斗奖励 */
		@ReasonDesc("战斗奖励")
		BATTLE_REWARD(20, ""),
		/** 通关奖励 */
		@ReasonDesc("通关奖励")
		STAGE_REWARD(21, ""),
		/** 转盘消耗 */
		@ReasonDesc("转盘消耗")
		TURNTABLE_COST(22, ""),
		/** 转盘获取 */
		@ReasonDesc("转盘获取")
		TURNTABLE_GET(23, ""),
		/** 使用消耗品 */
		@ReasonDesc("使用消耗品")
		COMSUME_USE(24, ""),
		/** 使用礼包 */
		@ReasonDesc("使用礼包")
		SPREE_USE(25, ""),
		/** 在线奖励 */
		@ReasonDesc("在线奖励")
		ONLINE_REWARD(26, ""),
		/** 清空背包 */
		@ReasonDesc("清空背包")
		CLEAR_BAG(27, ""),
		/** 日常任务奖励 */
		@ReasonDesc("日常任务奖励")
		DAILY_QUEST_REWARD(28, ""),
		/** 竞技场排名奖励 */
		@ReasonDesc("竞技场排名奖励")
		ARENA_RANK_REWARD(29, ""),
		/** boss战奖励 */
		@ReasonDesc("boss战奖励")
		BOSS_REWARD(30, ""),
		/** 精英副本奖励 */
		@ReasonDesc("精英副本奖励")
		ELITE_STAGE_REWARD(31, ""),
		/** 学习技能 */
		@ReasonDesc("学习技能")
		SKILL_STUDY(32, ""),
		/** 矿场收获 */
		@ReasonDesc("矿场收获")
		MINE_HARVEST(33, ""),
		/** 矿场收获 */
		@ReasonDesc("神秘商店购买")
		BUY_SPECIAL_SHOP(34, ""),
		/** 发言消耗 */
		@ReasonDesc("发言消耗")
		CHAT_COST(35, ""),
		/** 荣誉商店购买 */
		@ReasonDesc("荣誉商店购买")
		HONOUR_SHOP(36, ""),
		/** 关卡评星奖励 */
		@ReasonDesc("关卡评星奖励")
		STAGE_STAR_REWARD(37, ""),
		/** 完美通关奖励 */
		@ReasonDesc("完美通关奖励")
		PERFECT_MAP_REWARD(38, ""),
		/** 装备洗练消耗灵石 */
		@ReasonDesc("装备洗练消耗灵石 ")
		EQUIP_FORGE(39, ""),
		/** 初次试练塔奖励  */
		@ReasonDesc("初次试练塔奖励 ")
		FIRST_REFINE(40, ""),
		/** 攻打试练塔奖励  */
		@ReasonDesc("攻打试练塔奖励 ")
		REFINE(41, ""),
		/** 匹配战获得连胜排名奖励  */
		@ReasonDesc("匹配战获得连胜排名奖励 ")
		MATCH_BATTLE_STREAK_WIN_RANK_REWARD(42, ""),
		/** 封测奖励  */
		@ReasonDesc("封测奖励 ")
		BETA_REWARD(43, ""),
		@ReasonDesc("主线任务奖励 ")
		MAIN_QUEST_REWARD(44, ""),
		/** 魔石混战鼓舞消耗灵石 */
		@ReasonDesc("魔石混战鼓舞消耗灵石 ")
		MATCH_BATTLE_ENCOURAGE(39, ""),
		/** 魔石混战鼓舞消耗灵石 */
		@ReasonDesc("Boss战鼓舞消耗灵石 ")
		BOSS_WAR_ENCOURAGE(40, ""),
		/** 领取黄钻新手礼包 */
		@ReasonDesc("领取黄钻新手礼包 ")
		YELLOW_VIP_ONCE_REWARD(41, ""),		
		/** 领取年费黄钻每日礼包 */
		@ReasonDesc("领取年费黄钻每日礼包 ")
		YEAR_YELLOW_VIP_DAILY_REWARD(42, ""),
		/** 领取黄钻升级礼包 */
		@ReasonDesc("领取黄钻升级礼包 ")
		YELLOW_VIP_LEVEL_UP_REWARD(43, ""),
		/** 首充奖励 */
		@ReasonDesc("首充奖励 ")
		FIRST_RECHARGE_REWARD(44, ""),
		/** 单笔充值奖励 */
		@ReasonDesc("单笔充值奖励 ")
		SINGLE_RECHARGE_REWARD(45, ""),
		/** 累计充值奖励 */
		@ReasonDesc("累计充值奖励 ")
		TOTAL_RECHARGE_REWARD(46, ""),
		/** 战神之巅击杀奖励 */
		@ReasonDesc("战神之巅击杀奖励 ")
		MARS_KILL_REWARD(47, ""),
		/** 个人目标达成奖励 */
		@ReasonDesc("个人目标达成奖励")
		TARGET_REACH_REWARD(48, ""),
		/** 军团boss战奖励 */
		@ReasonDesc("军团boss战奖励")
		LEGION_BOSS_REWARD(49, ""),
		/** 军团矿战排名奖励 */
		@ReasonDesc("军团矿战排名奖励")
		LEGION_MINE_RANK_REWARD(50, ""),
		/** 军团商店购买 */
		@ReasonDesc("军团商店购买")
		BUY_LEGION_SHOP(51, ""),
		/** 神魄升级灵图 */
		@ReasonDesc("神魄升级灵图 ")
		UPGRADE_MAGIC_PAPER(52, ""),
		/** 灵图合成 */
		@ReasonDesc("灵图合成 ")
		SYNTHESIS_MAGIC_PAPER(53, ""),
		/** 装备开孔消耗 */
		@ReasonDesc("装备开孔消耗 ")
		EQUIP_PUNCH(54, ""),
		/** 升级天赋 */
		@ReasonDesc("升级天赋 ")
		UPGRADE_GIFT(55, ""),
		/** 天赋碎片合成 */
		@ReasonDesc("天赋碎片合成 ")
		SYNTHESIS_GIFT_CHIP(56, ""),
		/** 军团矿战战斗奖励 */
		@ReasonDesc("军团矿战战斗奖励")
		LEGION_MINE_BATTLE_REWARD(57, "")
		;

		/** 原因序号 */
		public final int reason;
		/** 原因文本 */
		public final String reasonText;

		private ItemLogReason(int reason, String reasonText) {
			this.reason = reason;
			this.reasonText = reasonText;
		}

		@Override
		public int getReason() {
			return reason;
		}

		@Override
		public String getReasonText() {
			return reasonText;
		}
	}

	@LogDesc(desc = "金钱改变")
	public enum MoneyLogReason implements ILogReason {
		/** 升级建筑 */
		@ReasonDesc("升级建筑")
		BUILDING_UPGRADE(0, ""),
		/** 消cd */
		@ReasonDesc("消cd")
		REMOVE_CD(1, ""),
		/** 魔晶兑换 */
		@ReasonDesc("魔晶兑换")
		CRYSTAL_EXCHANGE(2, ""),
		/** 宝石合成 */
		@ReasonDesc("宝石合成")
		GEM_SYNTHESIS(3, ""),
		/** GM命令 */
		@ReasonDesc("GM命令")
		GM_COMMAND(4, ""),
		/** 占星 */
		@ReasonDesc("占星")
		HOROSCOPE(5, ""),
		/** 任务奖励 */
		@ReasonDesc("任务奖励")
		QUEST(6, ""),
		/** 装备制作 */
		@ReasonDesc("装备制作")
		EQUIP_MAKE(7, ""),
		/** 装备升级 */
		@ReasonDesc("装备升级")
		EQUIP_UPGRADE(8, ""),
		/** 宝石镶嵌 */
		@ReasonDesc("宝石镶嵌")
		GEM_EMBED(9, ""),
		/** 宝石卸下 */
		@ReasonDesc("宝石卸下")
		GEM_EXTRACT(10, ""),
		/** 宝石打孔 */
		@ReasonDesc("宝石打孔")
		GEM_PUNCH(11, ""),
		/** 征税 */
		@ReasonDesc("征税")
		LEVY(12, ""),
		/** 商城购买 */
		@ReasonDesc("商城购买")
		MALL_BUY(13, ""),
		/** 在线奖励 */
		@ReasonDesc("在线奖励")
		ONLINE_REWARD(14, ""),
		/** 排行榜奖励 */
		@ReasonDesc("排行榜奖励")
		RANK_REWARD(15, ""),
		/** 充值 */
		@ReasonDesc("充值")
		RECHARGE(16, ""),
		/** 商店购买 */
		@ReasonDesc("商店购买")
		SHOP_BUY(17, ""),
		/** 商店出售 */
		@ReasonDesc("商店出售")
		SHOP_SELL(18, ""),
		/** 战斗抽奖 */
		@ReasonDesc("战斗抽奖")
		BATTLE_LOTTERY(19, ""),
		/** 战斗奖励 */
		@ReasonDesc("战斗奖励")
		BATTLE_REWARD(20, ""),
		/** 训练 */
		@ReasonDesc("训练")
		TRAIN(21, ""),
		/** 问答奖励  */
		@ReasonDesc("问答奖励")
		ANSWER_QUESTION(22, ""),
		/** 问答积分兑换  */
		@ReasonDesc("问答积分兑换")
		ANSWER_QUESTION_POINT_EXCHANGE(23, ""),
		/** 测试消息 */
		@ReasonDesc("测试消息")
		TEST(24, ""),
		/** 好友奖励 */
		@ReasonDesc("好友奖励")
		FRIEND_REWARD(25, ""),
		/** 使用礼包 */
		@ReasonDesc("使用礼包")
		SPREE_USE(26, ""),
		/** 通关奖励 */
		@ReasonDesc("通关奖励")
		STAGE_PASS(27, ""),
		@ReasonDesc("日常任务奖励")
		DAILY_QUEST_REWARD(28, ""),
		@ReasonDesc("主背包升级")
		MAIN_BAG_UPGRADE(29, ""),
		@ReasonDesc("买竞技场战斗次数")
		ARENA_BUY_BATTLE_TIME(30, ""),
		@ReasonDesc("竞技场排名奖励")
		ARENA_RANK_REWARD(31, ""),
		@ReasonDesc("竞技场战斗奖励")
		ARENA_BATTLE_REWARD(32, ""),
		@ReasonDesc("购买体力值")
		BUY_ENERGY(33, ""),
		@ReasonDesc("提前开启冥想协助位")
		MEDITATION_OPEN_POSITION(34, ""),
		@ReasonDesc("等级满足, 开启技能栏位")
		OPEN_SKILL_SLOT(35, ""),
		@ReasonDesc("花费魔晶直接开启技能栏位")
		DIRECT_OPEN_SKILL_SLOT(36, ""),
		@ReasonDesc("boss战鼓舞")
		BOSS_ENCOURAGE(37, ""),
		@ReasonDesc("boss战奖励")
		BOSS_REWARD(38, ""),
		@ReasonDesc("刷新精英副本挑战状态")
		REFRESH_ELITE_STAGE_CHALLENGE_STATE(39, ""),
		@ReasonDesc("精英副本挑战奖励")
		ELITE_STAGE_REWARD(40, ""),
		@ReasonDesc("冥想")
		MEDITATION(41, ""),
		@ReasonDesc("星运开仓库格子")
		HOROSCOPE_STORAGE_UPGRADE(42, ""),
		@ReasonDesc("重置技能点")
		RESET_SKILL_POINTS(43, ""),
		@ReasonDesc("重置矿坑")
		RESET_MINE_FIELD(44, ""),
		@ReasonDesc("购买矿坑开采次数")
		BUY_MINE_TIMES(45, ""),
		@ReasonDesc("刷新神秘商店")
		REFRESH_SPECIAL_SHOP(46, ""),
		@ReasonDesc("购买神秘商店物品")
		BUY_SPECIAL_SHOP_ITEM(47, ""),
		@ReasonDesc("魔晶收集魔法石")
		CRYSTAL_COLLECT_MAGIC_STONE(48, ""),
		@ReasonDesc("完成收集魔法石任务奖励")
		COLLECT_MAGIC_STONE_REWARD(49, ""),
		@ReasonDesc("全部拿去关卡奖励消耗")
		GET_ALL_STAGE_REWARD(50, ""),
		@ReasonDesc("荣誉商店购买")
		HONOUR_SHOP_BUY(51, ""),
		@ReasonDesc("一键加速消除矿场cd")
		REMOVE_ALL_MINE_CD(52, ""),
		@ReasonDesc("装备洗炼")
		EQUIP_FORGE(53, ""),
		@ReasonDesc("属性培养")
		PROPERTY_FOSTER(54, ""),
		@ReasonDesc("自动攻打试炼塔")
		AUTO_ATTACK_REFINE(55, ""),
		@ReasonDesc("刷新试炼塔")
		REFRESH_REFINE(56, ""),
		@ReasonDesc("匹配战鼓舞")
		MATCH_BATTLE_ENCOURAGE(57, ""),
		/** 充值奖励 */
		@ReasonDesc("充值奖励")
		RECHARGE_REWARD(58, ""),
		@ReasonDesc("勇者之路任务奖励")
		WARRIOR_QUEST_REWARD(59, ""),
		@ReasonDesc("接受勇者之路挑战胜利")
		RECEIVE_WARRIOR_CHALLENGE_WIN(60, ""),
		@ReasonDesc("接受勇者之路挑战失败")
		RECEIVE_WARRIOR_CHALLENGE_LOSS(61, ""),
		@ReasonDesc("魔晶抽奖")
		TURNTABLE_USE_CRYSTAL(62, ""),		
		@ReasonDesc("GM充值奖励")
		GM_RECHARGE_REWARD(63, ""),
		/** 领取黄钻每日礼包 */
		@ReasonDesc("领取黄钻每日礼包 ")
		YELLOW_VIP_DAILY_REWARD(64, ""),
		/** 领取黄钻升级礼包 */
		@ReasonDesc("领取黄钻升级礼包 ")
		YELLOW_VIP_LEVEL_UP_REWARD(65, ""),
		/** QQ充值 */
		@ReasonDesc("QQ充值")
		QQ_RECHARGE(66, ""),
		/** 匹配战战斗奖励 */
		@ReasonDesc("匹配战战斗奖励")
		MATCH_BATTLE_REWARD(67,""),
		/** 神魄强化 */
		@ReasonDesc("神魄强化")
		GODSOUL_UPGRADE(68,""),
		/** 战俘营购买抓捕次数 */
		@ReasonDesc("战俘营购买抓捕次数")
		PRISON_BUY_ARREST_NUM(69,""),
		/** 战俘营刷新抓捕列表 */
		@ReasonDesc("战俘营刷新抓捕列表")
		PRISON_REFRESH_ARREST_LIST(70,""),
		/** 战俘营解锁抓捕房间 */
		@ReasonDesc("战俘营解锁抓捕房间")
		PRISON_UNLOCK_ARREST_ROOM(71,""),
		/** 战俘营提取全部经验 */
		@ReasonDesc("战俘营提取经验")
		PRISON_EXTRACT_EXPERIENCE(72,""),
		@ReasonDesc("领取军衔俸禄")
		GET_TITLE_SALARY(73,""),
		@ReasonDesc("挑选精灵对酒")
		FINGER_GUESS(74,""),
		@ReasonDesc("角斗场购买角斗次数")
		ABATTOIR_BUY_WRESTLE_NUM(75,""),
		@ReasonDesc("酒馆对酒必胜消耗")
		SUCCEED_FINGER_GUESS_ROUND(76,""),
		@ReasonDesc("嗜血神殿购买角斗次数")
		BLOOD_TEMPLE_BUY_WRESTLE_NUM(77,""),
		@ReasonDesc("精灵背包购买格子")
		SPRITE_BAG_CELL_BUY(78,""),
		@ReasonDesc("转技能系")
		CHANGE_DEVELOP_TYPE(79,""),
		@ReasonDesc("创建军团")
		CREATE_LEGION(80,""),
		@ReasonDesc("随机加入阵营奖励")
		RANDOM_JOIN_FACTION(81,""),
		@ReasonDesc("战神之巅刷新房间")
		MARS_FEFRESH_ROOM(82,""),
		@ReasonDesc("战神之巅解锁房间")
		MARS_UNLOCK_ROOM(83,""),
		@ReasonDesc("战神之巅购买加倍次数")
		MARS_BUY_MULTIPLE_NUM(84,""),
		@ReasonDesc("战神之巅加倍击杀")
		MARS_MULTIPLE_KILL(85,""),
		@ReasonDesc("军团boss战奖励")
		LEGION_BOSS_REWARD(86, ""),
		@ReasonDesc("军团boss战鼓舞")
		LEGION_BOSS_ENCOURAGE(87, ""),
		@ReasonDesc("军团矿战鼓舞")
		LEGION_MINE_ENCOURAGE(88, ""),
		@ReasonDesc("军团矿战侦查")
		LEGION_MINE_WATCH(89, ""),
		@ReasonDesc("税收押注必胜")
		LEVY_BET_WIN(90, ""),
		@ReasonDesc("军团矿战排名奖励")
		LEGION_MINE_REWARD(91, ""),
		@ReasonDesc("押运刷新怪物")
		ESCORT_REFRESH_MONSTER(92, ""),
		@ReasonDesc("押运召唤怪物")
		ESCORT_CALL_MONSTER(93, ""),
		@ReasonDesc("押运拦截奖励")
		ESCORT_ROB_REWARD(94, ""),
		@ReasonDesc("押运购买拦截次数")
		ESCORT_BUY_ROB_NUM(95, ""),
		@ReasonDesc("押运奖励")
		ESCORT_REWARD(96, ""),
		@ReasonDesc("协助押运奖励")
		ESCORT_HELP_REWARD(97, ""),
		@ReasonDesc("押运拦截排行奖励")
		ESCORT_ROB_RANK_REWARD(98, ""),
		@ReasonDesc("军团冥想")
		LEGION_MEDITATION(99, ""),
		@ReasonDesc("军团科技捐献")
		LEGION_TECHNOLOGY_CONTRIBUTE(100, ""),
		@ReasonDesc("军团任务加速完成")
		LEGION_TASK_COMPLETE(101, ""),
		@ReasonDesc("刷新军团任务列表")
		REFRESH_LEGION_TASK_LIST(102, ""),
		@ReasonDesc("灵图合成")
		MAGIC_PAPER_SYNTHESIS(103, ""),
		@ReasonDesc("主城打怪")
		MAIN_CITY_MONSTER(104, ""),
		@ReasonDesc("日常任务")
		DAILY_QUEST(105, ""),
		@ReasonDesc("天赋碎片合成")
		GIFT_CHIP_SYNTHESIS(106, ""),
		@ReasonDesc("一键答题")
		ONEKEY_ANSWER_QUESTION(107, ""),
		@ReasonDesc("军团捐献魔晶")
		LEGION_DONATE_CRYSTAL(108, ""),
		@ReasonDesc("精炼厂收获")
		MINE_HARVEST(109, "")
		;
		
		/** 原因序号 */
		public final int reason;
		/** 原因文本 */
		public final String reasonText;

		private MoneyLogReason(int reason, String reasonText) {
			this.reason = reason;
			this.reasonText = reasonText;
		}

		@Override
		public int getReason() {
			return this.reason;
		}

		@Override
		public String getReasonText() {
			return this.reasonText;
		}
	}


	/**
	 * 聊天日志产生原因
	 */
	@LogDesc(desc = "聊天")
	public enum ChatLogReason implements ILogReason {
		/** 包含不良信息 */
		@ReasonDesc("包含不良信息")
		REASON_CHAT_DIRTY_WORD(0, ""),
		/** 普通聊天信息 */
		@ReasonDesc("普通聊天信息")
		REASON_CHAT_COMMON(1, ""), ;

		/** 原因序号 */
		public final int reason;
		/** 原因文本 */
		public final String reasonText;

		private ChatLogReason(int reason, String reasonText) {
			this.reason = reason;
			this.reasonText = reasonText;
		}

		@Override
		public int getReason() {
			return this.reason;
		}

		@Override
		public String getReasonText() {
			return this.reasonText;
		}
	}

	/**
	 * 玩家基础日志原因
	 */
	@LogDesc(desc = "玩家基础日志")
	public enum BasicPlayerLogReason implements ILogReason {
		/** 正常登录 */
		@ReasonDesc("正常登录")
		REASON_NORMAL_LOGIN(0, ""),
		/** 正常退出 */
		@ReasonDesc("正常退出")
		REASON_NORMAL_LOGOUT(1, ""),
		/** GM踢人 */
		@ReasonDesc("GM踢人")
		GM_KICK(2,"GM踢人"),
		;

		/** 原因序号 */
		public final int reason;
		/** 原因文本 */
		public final String reasonText;

		private BasicPlayerLogReason(int reason, String reasonText) {
			this.reason = reason;
			this.reasonText = reasonText;
		}

		@Override
		public int getReason() {
			return this.reason;
		}

		@Override
		public String getReasonText() {
			return this.reasonText;
		}
	}

	/**
	 * Gm命令日志原因
	 * 
	 */
	@LogDesc(desc = "使用GM命令")
	public enum GmCommandLogReason implements ILogReason {
		/** 非法使用GM命令 */
		@ReasonDesc("非法使用GM命令")
		REASON_INVALID_USE_GMCMD(0, ""),
		/** 合法使用GM命令 */
		@ReasonDesc("合法使用GM命令")
		REASON_VALID_USE_GMCMD(1, ""), 
		;

		/** 原因序号 */
		public final int reason;
		/** 原因文本 */
		public final String reasonText;

		private GmCommandLogReason(int reason, String reasonText) {
			this.reason = reason;
			this.reasonText = reasonText;
		}

		@Override
		public int getReason() {
			return this.reason;
		}

		@Override
		public String getReasonText() {
			return this.reasonText;
		}
	}

	/**
	 * 在线日志原因
	 * 
	 */
	@LogDesc(desc = "玩家在线时长")
	public enum OnlineTimeLogReason implements ILogReason {
		/** 无意义 */
		@ReasonDesc("无意义")
		DEFAULT(0, ""), 
		/** 登录 */
		@ReasonDesc("登录")
		LOG_IN(1, ""), 
		/** 登出 */
		@ReasonDesc("登出")
		LOG_OUT(2, ""), 
		/** GM踢出 */
		@ReasonDesc("GM踢出")
		GM_KICK(3,"")
		;

		/** 原因序号 */
		public final int reason;
		/** 原因文本 */
		public final String reasonText;

		private OnlineTimeLogReason(int reason, String reasonText) {
			this.reason = reason;
			this.reasonText = reasonText;
		}

		@Override
		public int getReason() {
			return this.reason;
		}

		@Override
		public String getReasonText() {
			return this.reasonText;
		}
	}


	@LogDesc(desc = "战斗")
	public enum BattleLogReason implements ILogReason {
		/** 关卡征战 */
		@ReasonDesc("关卡征战")
		STAGE_ATTACK(0, ""),
		;

		/** 原因序号 */
		public final int reason;
		/** 原因文本 */
		public final String reasonText;

		BattleLogReason(int reason, String reasonText) {
			this.reason = reason;
			this.reasonText = reasonText;
		}

		@Override
		public int getReason() {
			return this.reason;
		}

		@Override
		public String getReasonText() {
			return this.reasonText;
		}
	}
	
	@LogDesc(desc = "发送邮件")
	public enum MailLogReason implements ILogReason {
		;

		/** 原因序号 */
		public final int reason;
		/** 原因文本 */
		public final String reasonText;

		private MailLogReason(int reason, String reasonText) {
			this.reason = reason;
			this.reasonText = reasonText;
		}

		@Override
		public int getReason() {
			return this.reason;
		}

		@Override
		public String getReasonText() {
			return this.reasonText;
		}
	}
	
	@LogDesc(desc = "属性变更日志")
	public enum PropertyLogReason implements ILogReason {
		/** 装备星运 */
		@ReasonDesc("装备星运")
		HOROSCOPE_ON(0, ""),
		/** 卸下星运 */
		@ReasonDesc("卸下星运")
		HOROSCOPE_OFF(1, ""),
		/** 升级科技 */
		@ReasonDesc("升级科技")
		TECHNOLOGY_UPGRADE(2, "科技id:{0};等级:{1}"),
		/** 装备强化 */
		@ReasonDesc("装备强化")
		EQUIP_UPGRADE(3, ""),
		/** 装备宝石 */
		@ReasonDesc("装备宝石")
		GEM_EMBED(4, ""),
		/** 卸下宝石 */
		@ReasonDesc("卸下宝石")
		GEM_OFF(5, ""),
		/** 穿上或卸下装备*/
		@ReasonDesc("穿上或卸下装备")
		EQUIP_ONOFF(6, ""),		
		/** 装备洗炼*/
		@ReasonDesc("装备洗炼")
		EQUIP_FORGE(7, ""),
		/** 激活天赋 */
		@ReasonDesc("激活天赋")
		GIFT_ACTIVE(8, ""),
		/** 重置天赋 */
		@ReasonDesc("重置天赋")
		GIFT_RESET(9, ""),
		/** 军衔属性加成 */
		@ReasonDesc("军衔属性加成")
		TITLE_UP(10, ""),
		/** 神魄强化属性加成 */
		@ReasonDesc("神魄强化属性加成")
		GODSOUL_UP(11, ""),
		@ReasonDesc("精灵属性加成")
		SPRITE(12, ""),
		@ReasonDesc("精灵buff属性加成")
		SPRITE_BUFF(13, ""),
		@ReasonDesc("星座属性加成")
		SIGN(14, ""),
		@ReasonDesc("秘药属性加成")
		NOSTRUM(15, ""),
		@ReasonDesc("军团头衔属性加成")
		LEGION_TITLE(16, ""),
		@ReasonDesc("神魄灵图属性加成")
		MAGIC_PAPER(17, ""),
		@ReasonDesc("神魄buff属性加成")
		GODSOUL_BUFF(18, ""),
		@ReasonDesc("预见属性加成")
		PREDICT(19, ""),
		;

		/** 原因序号 */
		public final int reason;
		/** 原因文本 */
		public final String reasonText;

		private PropertyLogReason(int reason, String reasonText) {
			this.reason = reason;
			this.reasonText = reasonText;
		}

		@Override
		public int getReason() {
			return this.reason;
		}

		@Override
		public String getReasonText() {
			return this.reasonText;
		}
	}
	
	@LogDesc(desc = "好友日志")
	public enum FriendLogReason implements ILogReason {
		/** 添加好友 */
		@ReasonDesc("添加好友")
		ADD_FRIEND(0, ""),
		/** 删除好友 */
		@ReasonDesc("删除好友")
		REMOVE_FRIEND(1, ""),
		;

		/** 原因序号 */
		public final int reason;
		/** 原因文本 */
		public final String reasonText;

		private FriendLogReason(int reason, String reasonText) {
			this.reason = reason;
			this.reasonText = reasonText;
		}

		@Override
		public int getReason() {
			return this.reason;
		}

		@Override
		public String getReasonText() {
			return this.reasonText;
		}
	}
	
	@LogDesc(desc = "星运日志")
	public enum HoroscopeLogReason implements ILogReason {
		/** 占星 */
		@ReasonDesc("占星")
		GAMBLE(0, ""),
		/** 星运合成狼 */
		@ReasonDesc("星运合成")
		COMPOUND(1, "被吞噬星运信息id={0},level={1},exp={2}"),		
		/** 装备或卸下星运*/
		@ReasonDesc("装备或卸下星运")
		TAKEONOFF_HOROSCOPE(2, ""),
		/** 移动位置 */
		@ReasonDesc("移动位置")
		MOVE(3, "fromBagType={0},fromIndex={1}"),
		/** gm命令 */
		@ReasonDesc("gm命令占星")
		GM_GAMBLE(4, ""),
		/** 打开虚拟礼包获得 */
		@ReasonDesc("打开虚拟礼包获得")
		GET_FROM_VIRTURL_SPREE(5, ""),
		/** 使用礼包获得 */
		@ReasonDesc("使用礼包获得")
		USE_SPREE(6, ""),
		/** 使用礼包获得 */
		@ReasonDesc("拾取星运")
		PICK_UP(7, ""),
		;

		/** 原因序号 */
		public final int reason;
		/** 原因文本 */
		public final String reasonText;

		private HoroscopeLogReason(int reason, String reasonText) {
			this.reason = reason;
			this.reasonText = reasonText;
		}

		@Override
		public int getReason() {
			return this.reason;
		}

		@Override
		public String getReasonText() {
			return this.reasonText;
		}
	}
	
	@LogDesc(desc = "任务日志")
	public enum QuestLogReason implements ILogReason {
		/** 完成任务 */
		@ReasonDesc("完成任务")
		FINISH(0, ""),
		;

		/** 原因序号 */
		public final int reason;
		/** 原因文本 */
		public final String reasonText;

		private QuestLogReason(int reason, String reasonText) {
			this.reason = reason;
			this.reasonText = reasonText;
		}

		@Override
		public int getReason() {
			return this.reason;
		}

		@Override
		public String getReasonText() {
			return this.reasonText;
		}
	}
	@LogDesc(desc = "充值日志")
	public enum RechargeLogReason implements ILogReason{
		/** 正常充值 */
		@ReasonDesc("正常充值")
		RECHARGE(0, ""),
		/** GM充值 */
		@ReasonDesc("GM充值")
		RECHARGE_BY_GM(1, ""),
		/** QQ充值 */
		@ReasonDesc("QQ充值")
		QQ_RECHARGE(2, "")
		
		;
		
		private RechargeLogReason(int reason, String reasonText){
			this.reason = reason;
			this.reasonText = reasonText;
		}

		/** 原因序号 */
		public final int reason;
		/** 原因文本 */
		public final String reasonText;

		@Override
		public int getReason() {
			return reason;
		}

		@Override
		public String getReasonText() {
			return reasonText;
		}
		
	}
	
	public enum EnergyLogReason implements ILogReason{		
		/** 购买体力 */
		@ReasonDesc("购买体力")
		BUY_ENERGY(0, ""),
		/** 征战地图战斗消耗体力 */
		@ReasonDesc("征战地图战斗消耗体力")
		BATTLE_WITH_MAP_MONSTER_USE_ENERGY(1, ""),
		/** 精英副本战斗消耗体力 */
		@ReasonDesc("精英副本战斗消耗体力")
		BATTLE_WITH_ELITE_USE_ENERGY(2, ""),		
		/** 体力自动恢复  */
		@ReasonDesc("体力自动恢复")
		ENERGY_AUTO_RECOVER(3,""),
		/** 好友赠送体力  */
		@ReasonDesc("好友赠送体力")
		FRIEND_GIVE_ENERGY(4,""),
		/** 使用增加体力道具  */
		@ReasonDesc("使用增加体力道具")
		USE_ENERGY_ITEM(5,""),
		/** 体力手动恢复  */
		@ReasonDesc("体力手动恢复")
		ENERGY_HAND_RECOVER(6,""),
		/** 每日体力自动恢复  */
		@ReasonDesc("每日体力自动恢复")
		ENERGY_DAILY_RECOVER(7,""),
		;
		
		
		private EnergyLogReason(int reason,String reasonText){
			this.reason = reason;
			this.reasonText = reasonText;
		}
		/** 原因序号 */
		public final int reason;
		/** 原因文本 */
		public final String reasonText;
		@Override
		public int getReason() {
			return reason;
		}

		@Override
		public String getReasonText() {
			return reasonText;
		}
		
	}
	public enum HonourLogReason implements ILogReason{
		/** 使用物品,添加荣誉*/
		@ReasonDesc("使用物品,添加荣誉")
		ITEM_USE_ADD_HONOUR(0, ""),
		/** 竞技场挑战 */
		@ReasonDesc("竞技场挑战")
		ARENA_BATTLE_ADD_HONOUR(1, ""),
		/** Boss战获得荣誉*/
		@ReasonDesc("Boss战获得荣誉")
		BOSSWAR_ADD_HONOUR(2, ""),
		/** 竞技场排名奖励添加荣誉*/
		@ReasonDesc("竞技场排名奖励添加荣誉")
		ARENA_RANK_ADD_HONOUR(3, ""),
		/** 匹配战战斗获得荣誉*/
		@ReasonDesc("匹配战战斗获得荣誉")
		MATCH_BATTLE_REWARD_HONOUR(4, ""),
		@ReasonDesc("角斗场获得荣誉")
		ABATTOIR_HONOR(5, ""),
		@ReasonDesc("嗜血神殿获得荣誉")
		BlOOD_TEMPLE_HONOR(6, ""),
		@ReasonDesc("荣誉商店消耗荣誉")
		HONOR_SHOP_BUY_ITEM(7, ""),
		@ReasonDesc("战神之巅获得荣誉")
		MARS(8, "")
		;
		
		
		private HonourLogReason(int reason,String reasonText){
			this.reason = reason;
			this.reasonText = reasonText;
		}
		/** 原因序号 */
		public final int reason;
		/** 原因文本 */
		public final String reasonText;
		@Override
		public int getReason() {
			return reason;
		}

		@Override
		public String getReasonText() {
			return reasonText;
		}
		
	}
	public enum TechPointLogReason implements ILogReason{
		/** gm命令获取科技点*/
		@ReasonDesc("gm命令获取科技点")
		GM_ADD_TECH_POINT(0, ""),
		/** 使用礼包获得科技点 */
		@ReasonDesc("使用礼包获得科技点")
		ITEM_USE_ADD_TECH_POINT(1, ""),
		/** 每日问答获得科技点 */
		@ReasonDesc("每日问答获得科技点")
		DAILY_QUESTION_ADD_TECH_POINT(2, ""),
		/** 成功挑战精英副本获得科技点 */
		@ReasonDesc("成功挑战精英副本获得科技点")
		ELITE_STAGE_ADD_TECH_POINT(3, ""),		
		/** 协助冥想获取科技点*/
		@ReasonDesc("协助冥想获取科技点")
		ASSIST_MEDITATION_ADD_TECH_POINT(4, ""),
		/** 冥想获取科技点*/
		@ReasonDesc("冥想获取科技点")
		MEDITATION_ADD_TECH_POINT(5, ""),
		/** 冥想获取科技点*/
		@ReasonDesc("勇者之路奖励科技点")
		WARRIOR_WAY_ADD_TECH_POINT(6, ""),
		/** 军团冥想获取科技点*/
		@ReasonDesc("军团冥想获取科技点")
		LEGION_MEDITATION_ADD_TECH_POINT(7, "")
		;
		
		
		private TechPointLogReason(int reason,String reasonText){
			this.reason = reason;
			this.reasonText = reasonText;
		}
		/** 原因序号 */
		public final int reason;
		/** 原因文本 */
		public final String reasonText;
		@Override
		public int getReason() {
			return reason;
		}

		@Override
		public String getReasonText() {
			return reasonText;
		}
		
	}
	
	public enum ExperienceLogReason implements ILogReason{
		/** GM命令得经验*/
		@ReasonDesc("GM命令得经验")
		GM_ADD_EXP(0, ""),
		/** 使用物品,添加经验*/
		@ReasonDesc("使用物品,添加经验")
		ITEM_USE_ADD_EXP(1, ""),		
		/** 竞技场战斗奖励经验*/
		@ReasonDesc("竞技场战斗奖励经验")
		ARENA_BATTLE_ADD_EXP(2, ""),		
		/** 竞技场排名奖励添加经验*/
		@ReasonDesc("竞技场排名奖励添加经验")
		ARENA_RANK_ADD_EXP(3, ""),		
		/** 兑换积分得经验*/
		@ReasonDesc("兑换积分得经验")
		EXCHANGE_SCORE_ADD_EXP(4, ""),
		/** 答题得经验*/
		@ReasonDesc("答题得经验")
		ANSWER_QUESTION_ADD_EXP(5, ""),
		/** 战斗得经验*/
		@ReasonDesc("战斗得经验")
		BATTLE_ADD_EXP(6, ""),
		/** 通关得经验*/
		@ReasonDesc("通关地图获得经验")
		PASS_STAGE_ADD_EXP(7, ""),		
		/** 训练得经验*/
		@ReasonDesc("训练得经验")
		TRAINING_ADD_EXP(8, ""),
		/** 任务得经验*/
		@ReasonDesc("任务得经验")
		QUEST_ADD_EXP(9, ""),
		@ReasonDesc("完成勇者之路任务奖励")
		WARRIOR_QUEST_REWARD(10, ""),
		@ReasonDesc("战俘营获得")
		PRISON_GOT(11, ""),
		@ReasonDesc("主城打怪")
		MAIN_CITY_MONSTER(12, ""),
		@ReasonDesc("日常任务")
		DAILY_QUEST(13, "")
		;
		
		
		private ExperienceLogReason(int reason,String reasonText){
			this.reason = reason;
			this.reasonText = reasonText;
		}
		/** 原因序号 */
		public final int reason;
		/** 原因文本 */
		public final String reasonText;
		@Override
		public int getReason() {
			return reason;
		}

		@Override
		public String getReasonText() {
			return reasonText;
		}
	}
	
	public enum SkillPointLogReason implements ILogReason{
		/** gm命令获取技能点*/
		@ReasonDesc("gm命令获取技能点")
		GM_GET_SKILL_POINT(0, ""),
		/** 使用物品,添加技能点*/
		@ReasonDesc("使用物品,添加技能点")
		ITEM_USE_ADD_SKILL_POINT(1, ""),
		/** 升级添加技能点*/
		@ReasonDesc("升级添加技能点")
		LEVEL_UP_ADD_SKILL_POINT(2, ""),
		/** 重置技能点*/
		@ReasonDesc("重置技能点")
		RESET_SKILL_POINT(3, ""),
		/** 学习技能*/
		@ReasonDesc("学习技能")
		STUDY_SKILL(4, ""),
		
		;
		private SkillPointLogReason(int reason,String reasonText){
			this.reason = reason;
			this.reasonText = reasonText;
		}
		/** 原因序号 */
		public final int reason;
		/** 原因文本 */
		public final String reasonText;
		@Override
		public int getReason() {
			return reason;
		}

		@Override
		public String getReasonText() {
			return reasonText;
		}
	}
	
	public enum TrainCoinLogReason implements ILogReason{
		/** gm命令获取训练币*/
		@ReasonDesc("gm命令获取培养币")
		GM_GET_TRAIN_COIN(0, ""),
		@ReasonDesc("主城押注获得培养币")
		LEVY_BET_WIN(1, ""),
		@ReasonDesc("精炼厂收获培养币")
		MINE_HARVEST(2, ""),
		@ReasonDesc("属性培养消耗培养币")
		PROPERTY_FOSTER(3, ""),
		@ReasonDesc("训练消耗培养币")
		TRAINING(4, ""),
		@ReasonDesc("冥想消耗培养币")
		MEDITATION(5, ""),
		@ReasonDesc("使用物品添加培养币")
		ITEM_USE_ADD_TRAIN_COIN(6, "")
		;
		private TrainCoinLogReason(int reason,String reasonText){
			this.reason = reason;
			this.reasonText = reasonText;
		}
		/** 原因序号 */
		public final int reason;
		/** 原因文本 */
		public final String reasonText;
		@Override
		public int getReason() {
			return reason;
		}

		@Override
		public String getReasonText() {
			return reasonText;
		}
	}
	
	public enum PrestigeLogReason implements ILogReason{
		@ReasonDesc("gm命令获取威望")
		GM_GET_PRESTIGE(0, ""),
		@ReasonDesc("使用物品增加威望")
		ITEM_USE_ADD_PRESTIGE(1, ""),
		@ReasonDesc("角斗场提取威望")
		ABATTOIR_EXTRACT(2, ""),
		@ReasonDesc("嗜血神殿提取威望")
		BLOOD_TEMPLE_EXTRACT(3, ""),
		@ReasonDesc("军团矿战结束奖励威望")
		LEGION_MINE_WAR_REWARD(4, ""),
		@ReasonDesc("军衔升级消耗威望")
		UPGRADE_TITEL_COST(5, "")
		
		;
		private PrestigeLogReason(int reason,String reasonText){
			this.reason = reason;
			this.reasonText = reasonText;
		}
		/** 原因序号 */
		public final int reason;
		/** 原因文本 */
		public final String reasonText;
		@Override
		public int getReason() {
			return reason;
		}

		@Override
		public String getReasonText() {
			return reasonText;
		}
	}
	
	public enum AuraLogReason implements ILogReason{
		@ReasonDesc("gm命令获取灵气值")
		GM_GET_AURA(0, ""),
		@ReasonDesc("使用物品增加灵气值")
		ITEM_USE_ADD_AURA(1, ""),
		@ReasonDesc("精灵升级消耗灵气值")
		SPRITE_UPGRADE(2, ""),
		@ReasonDesc("试炼奖励灵气值")
		REFINE_REWARD(3, "")
		;
		private AuraLogReason(int reason,String reasonText){
			this.reason = reason;
			this.reasonText = reasonText;
		}
		/** 原因序号 */
		public final int reason;
		/** 原因文本 */
		public final String reasonText;
		@Override
		public int getReason() {
			return reason;
		}

		@Override
		public String getReasonText() {
			return reasonText;
		}
	}
	
	public enum StarSoulLogReason implements ILogReason{
		@ReasonDesc("gm命令获取星魂")
		GM_GET_STAR_SOUL(0, ""),
		@ReasonDesc("使用物品增加星魂")
		ITEM_USE_ADD_STAR_SOUL(1, ""),
		@ReasonDesc("点亮星图消耗星魂")
		STAR_MAP_ACTIVATE(2, "")
		;
		private StarSoulLogReason(int reason,String reasonText){
			this.reason = reason;
			this.reasonText = reasonText;
		}
		/** 原因序号 */
		public final int reason;
		/** 原因文本 */
		public final String reasonText;
		@Override
		public int getReason() {
			return reason;
		}

		@Override
		public String getReasonText() {
			return reasonText;
		}
	}
}
