package com.hifun.soul.common.constants;

import com.hifun.soul.core.annotation.SysI18nString;

/**
 * 语言相关的常量定义
 * 
 * 
 */
public class LangConstants {
	/** 公用常量 */
	public static Integer COMMON_BASE = 0;
	/** 商城相关 */
	public static Integer MALL_BASE = 10001;
	/** 背包相关 */
	public static Integer BAG_BASE = 11001;
	/** 税收相关 */
	public static Integer LEVY_BASE = 11501;
	/** 好友相关 */
	public static Integer FRIEND_BASE = 11601;
	/** 星运相关 */
	public static Integer HOROSCOPE_BASE = 11701;	
	/** 强化相关 */
	public static Integer EQUIP_UPGRADE_BASE = 11801;
	/** 科技相关 */
	public static Integer TECHNOLOGY_BASE = 11901;
	/** 魔晶兑换相关 */
	public static Integer CRYSTALEXCHANGE_BASE = 12001;
	/** 转盘大抽奖相关 */
	public static Integer TURNTABLE_BASE = 12101;
	/** 训练相关 */
	public static Integer TAINING_BASE = 12201;
	/** 问答相关 */
	public static Integer QUESTION_BASE = 12301;
	/** 任务相关 */
	public static Integer QUEST_BASE = 12401;
	/** 宝石相关 */
	public static Integer GEM_BASE = 12501;	
	/** 关卡相关 */
	public static Integer STAGE_BASE = 12601;
	/** 竞技场相关  */
	public static Integer ARENA_BASE = 12701;
	/** 技能相关  */
	public static Integer SKILL_BASE = 12801;
	/** boss战相关 */
	public static Integer BOSS_BASE = 12901;
	/** 冥想相关  */
	public static Integer MEDITATION_BASE = 13001;
	/** gm问答相关  */
	public static Integer GM_QUESTION_BASE = 13101;
	/**login相关  */
	public static Integer LOGIN_BASE = 13201;
	/** 战斗相关  */
	public static Integer BATTLE_BASE = 13301;
	/** 矿场相关  */
	public static Integer MINE_BASE = 13401;
	/** 邮件相关  */
	public static Integer MAIL_BASE = 13501;
	/** 精英副本相关  */
	public static Integer ELITE_BASE = 13601;
	/** 神秘商店相关 */
	public static Integer SPECIAL_SHOP_BASE = 13701;
	/** 天赋相关 */
	public static Integer GIFT_BASE = 13801;
	/** 匹配战相关 */
	public static Integer MATCH_BATTLE_BASE = 13901;
	/** 勇者之路相关 */
	public static Integer WARRIOR_BASE = 14001;
	/** 军衔相关 */
	public static Integer TITLE_BASE = 14101;
	/** 军团相关 */
	public static Integer LEGION_BASE = 14201;
	/** 神魄相关 */
	public static Integer GODSOUL_BASE = 14301;
	/** 战俘营相关 */
	public static Integer PRISON_BASE = 14401;
	/** 角斗场相关 */
	public static Integer ABATTOIR_BASE = 14501;
	/** 嗜血神殿相关 */
	public static Integer BLOOD_TEMPLE_BASE = 14601;	
	/** 精灵相关 */
	public static Integer SPRITE_BASE = 14701;
	/** 充值相关 */
	public static Integer RECHARE_BASE = 14801;
	/** 战神之巅相关 */
	public static Integer MARS_BASE = 14901;
	/** 军团boss战相关 */
	public static Integer LEGION_BOSS_BASE = 15001;
	/** 军团矿战相关 */
	public static Integer LEGION_MINE_BASE = 15101;
	/** 秘药相关 */
	public static Integer NOSTRUM_BASE = 15201;
	/** 押运相关 */
	public static Integer ESCORT_BASE = 15301;
	/** 预见相关 */
	public static Integer PREDICT_BASE = 15401;
	/** 试炼相关 */
	public static Integer REFINE_BASE = 15501;
	
	/** 公用常量 */
	@SysI18nString(content = "服务器错误")
	public static final Integer SERVER_ERROR = ++COMMON_BASE;
	@SysI18nString(content = "冷却时间未到")
	public static final Integer COMMON_COOLINGDOWN = ++COMMON_BASE;
	@SysI18nString(content = "您的{0}不足", comment = "{0}某种属性，例如金钱、声望等等")
	public static final Integer COMMON_NOT_ENOUGH = ++COMMON_BASE;
	@SysI18nString(content = "连接服务器失败")
	public static final Integer CONNECT_SERVER_FAIL = ++COMMON_BASE;
	@SysI18nString(content = "该玩家已下线")
	public static final Integer PLAYER_OFFLINE = ++COMMON_BASE;
	@SysI18nString(content = "参数不正确")
	public static final Integer DEBUG_PARAM_ERROR = ++COMMON_BASE;
	@SysI18nString(content = "{0}为空", comment = "{0}角色名或宠物名或签名")
	public static final Integer GAME_INPUT_NULL = ++COMMON_BASE;
	@SysI18nString(content = "{0}最小长度为{1}个字符", comment = "{0}角色名或宠物名或签名,{1}为长度")
	public static final Integer GAME_INPUT_TOO_SHORT = ++COMMON_BASE;
	@SysI18nString(content = "{0}最大长度为{1}个字符", comment = "{0}角色名或宠物名或签名,{1}为长度")
	public static final Integer GAME_INPUT_TOO_LONG = ++COMMON_BASE;
	@SysI18nString(content = "{0}包含异常字符", comment = "{0}输入项")
	public static final Integer GAME_INPUT_ERROR1 = ++COMMON_BASE;
	@SysI18nString(content = "{0}包含屏蔽字符", comment = "{0}输入项")
	public static final Integer GAME_INPUT_ERROR2 = ++COMMON_BASE;
	@SysI18nString(content = "{0}包含非法字符", comment = "{0}输入项")
	public static final Integer GAME_INPUT_ERROR3 = ++COMMON_BASE;
	@SysI18nString(content = "角色等级还没有达到{0}级")
	public static final Integer CHARACTER_LEVEL_LIMIT = ++COMMON_BASE;
	@SysI18nString(content = "金币")
	public static final Integer COIN = ++COMMON_BASE;
	@SysI18nString(content = "魔晶")
	public static final Integer CRYSTAL = ++COMMON_BASE;
	@SysI18nString(content = "经验")
	public static final Integer EXPERIENCE = ++COMMON_BASE;
	@SysI18nString(content = "{2}{0}{1}",comment="获得金钱经验：{0}数值，{1}金币、魔晶、经验等") 
	public static final Integer COMMON_OBTAIN = ++COMMON_BASE;
	@SysI18nString(content = "您获得了{0}个{1}",comment="{0}获得个数，{1}获得物品名称")
	public static final Integer ITEM_OBTAIN = ++COMMON_BASE;
	@SysI18nString(content = "vip等级需要{0}级才能开启此功能")
	public static final Integer VIP_NOT_ENOUGH = ++COMMON_BASE;
	@SysI18nString(content = "CD中")
	public static final Integer CD_LIMIT = ++COMMON_BASE;
	@SysI18nString(content = "今日购买体力次数已用完。 ")
	public static final Integer BUY_ENERGY_TIME_USE_OUT = ++COMMON_BASE;
	@SysI18nString(content = "体力充沛，无需购买。 ")
	public static final Integer NO_NEED_BUY_ENERGY = ++COMMON_BASE;
	@SysI18nString(content = "角色名称 ")
	public static final Integer CHARACTER_NAME = ++COMMON_BASE;
	@SysI18nString(content = "荣誉")
	public static final Integer HONOR = ++COMMON_BASE;
	@SysI18nString(content = "威望 ")
	public static final Integer PRESTIGE = ++COMMON_BASE;
	@SysI18nString(content = "技能点 ")
	public static final Integer SKILLPOINT = ++COMMON_BASE;
	@SysI18nString(content = "说话太快了,喝口茶休息一下")
	public static final Integer CHAT_CD = ++COMMON_BASE;
	@SysI18nString(content = "发言需要喇叭")
	public static final Integer NO_HORN = ++COMMON_BASE;
	@SysI18nString(content = "{0}已经达到上限")
	public static final Integer REACH_TO_MAX = ++COMMON_BASE;
	@SysI18nString(content = "一次性充值{0}魔晶,获得额外{1}魔晶奖励")
	public static final Integer CHARGE_INFO = ++COMMON_BASE;
	@SysI18nString(content = "保存成功")
	public static final Integer SAVE_SUCCESS = ++COMMON_BASE;
	@SysI18nString(content = "你已经是vip{0}用户了")
	public static final Integer CANT_CHANGE_VIP_NOTICE = ++COMMON_BASE;
	@SysI18nString(content = "你")
	public static final Integer YOU = ++COMMON_BASE;
	@SysI18nString(content = "灵石")
	public static final Integer FORGE_STONE = ++COMMON_BASE;
	
	
	/** 战斗相关 */
	@SysI18nString(content = "战斗资源未加载完成,请稍后再试")
	public static final Integer BATTLE_INIT_ING = ++LOGIN_BASE;
	
	
	/** login相关 */
	@SysI18nString(content = "角色名过长")
	public static final Integer CHARACTER_NAME_TOO_LONG = ++LOGIN_BASE;
	@SysI18nString(content = "角色名过短")
	public static final Integer CHARACTER_NAME_TOO_SHORT= ++LOGIN_BASE;
	@SysI18nString(content = "当前服务器玩家爆满，请您切换别的服务器，或者稍后再试")
	public static final Integer LOGIN_ERROR_SERVER_FULL = ++LOGIN_BASE;
	@SysI18nString(content = "服务器正在进行例行维护，服务暂未开放，请您稍后再试")
	public static final Integer LOGIN_ERROR_WALL_CLOSED = ++LOGIN_BASE;
	
	/** 商城相关 */
	/** 背包相关 */
	@SysI18nString(content = "背包已满")
	public static final Integer BAG_IS_FULL = ++BAG_BASE;
	@SysI18nString(content = "背包容量不足")
	public static final Integer BAG_FREE_UNIT_NOT_ENOUGH = ++BAG_BASE;
	@SysI18nString(content = "卸下装备失败")
	public static final Integer TAKE_OFF_EQUIP_FAILED = ++BAG_BASE;
	@SysI18nString(content = "您的职业不适合穿戴这件装备")
	public static final Integer EQUIP_OCCUPATION_NOT_MATCH = ++BAG_BASE;
	@SysI18nString(content = "这个物品不能直接使用")
	public static final Integer CAN_NOT_USE_THIS_ITEM = ++BAG_BASE;
	@SysI18nString(content = "该物品不能出售")
	public static final Integer CAN_NOT_SELL_ITEM = ++BAG_BASE;
	@SysI18nString(content = "与职业不符,不能打造")
	public static final Integer CAN_NOT_MAKE = ++BAG_BASE;
	@SysI18nString(content = "与职业不符,不能强化")
	public static final Integer CAN_NOT_UPGRADE = ++BAG_BASE;
	@SysI18nString(content = "体力充沛，建议先去打打怪再来享用吧 ")
	public static final Integer NO_NEED_USE_ENERGY_ITEM = ++COMMON_BASE;
	
	/** 主城相关 */
	@SysI18nString(content = "今天购买次数已用完")
	public static final Integer RUNS_OUT_BUY_TIMES = ++LEVY_BASE;
	@SysI18nString(content = "今天花费魔晶收集魔法石次数已用完")
	public static final Integer CRYSTAL_COLLECT_TIME_USE_OUT = ++LEVY_BASE;
	@SysI18nString(content = "您收集到{0}颗宝石，获得了{1}灵气值")
	public static final Integer MAIN_CITY_AURA = ++LEVY_BASE;
	@SysI18nString(content = "税收加成已满")
	public static final Integer LEVY_FULL = ++LEVY_BASE;
	@SysI18nString(content = "今天押注次数已用完")
	public static final Integer HAVE_NO_BET_NUM = ++LEVY_BASE;
	@SysI18nString(content = "今日必胜次数已用完，提升vip等级，增加必胜次数")
	public static final Integer HAVE_NO_WIN_NUM = ++LEVY_BASE;
	@SysI18nString(content = "培养币")
	public static final Integer TRAIN_COIN = ++LEVY_BASE;
	@SysI18nString(content = "你大小通吃；获得培养币+{0}，今日税收加成+{1}%")
	public static final Integer LEVY_CERTAIN_WIN = ++LEVY_BASE;
	@SysI18nString(content = "大")
	public static final Integer LEVY_BET_BIG = ++LEVY_BASE;
	@SysI18nString(content = "小")
	public static final Integer LEVY_BET_SMALL = ++LEVY_BASE;
	@SysI18nString(content = "你掷出{0}点{1}，你胜利了；获得培养币+{2}，今日税收加成+{3}%")
	public static final Integer LEVY_BET_SUCCESS = ++LEVY_BASE;
	@SysI18nString(content = "你掷出{0}点{1}，你失败了")
	public static final Integer LEVY_BET_FAILD = ++LEVY_BASE;
	@SysI18nString(content = "今天的怪物已经打完了")
	public static final Integer NO_MAIN_CITY_MONSTER = ++LEVY_BASE;
	@SysI18nString(content = "体力已满")
	public static final Integer ENERGY_IS_FULL = ++LEVY_BASE;
	@SysI18nString(content = "今日恢复体力次数已用完")
	public static final Integer HAS_NO_ENERGY_RECOVER_NUM = ++LEVY_BASE;
	@SysI18nString(content = "恢复体力{0}点")
	public static final Integer ENERGY_RECOVER_NUM = ++LEVY_BASE;
	
	/** 好友相关 */
	@SysI18nString(content = "好友切磋")
	public static final Integer FRIEND_BATTLE = ++FRIEND_BASE;
	@SysI18nString(content = "不能添加自己为好友")
	public static final Integer CAN_NOT_ADD_SELF = ++FRIEND_BASE;
	@SysI18nString(content = "不存在该玩家")
	public static final Integer NO_ROLE = ++FRIEND_BASE;
	@SysI18nString(content = "已在好友列表中")
	public static final Integer IS_IN_FRIENDS = ++FRIEND_BASE;
	@SysI18nString(content = "好友请求发送成功,等待对方确认")
	public static final Integer APPLY_SUCCESS = ++FRIEND_BASE;
	@SysI18nString(content = "{0}通过你的好友请求，成为你的好友")
	public static final Integer ADD_FRIEND_NOTICE = ++FRIEND_BASE;
	@SysI18nString(content = "成功添加{0}为你的好友")
	public static final Integer ADD_FRIEND_SUCCESS = ++FRIEND_BASE;
	@SysI18nString(content = "你的好友已经超过上限，不能继续添加好友")
	public static final Integer SELF_MAX_FRIEND_NUM = ++FRIEND_BASE;
	@SysI18nString(content = "对方好友已经超过上限，不能申请")
	public static final Integer OTHER_MAX_FRIEND_NUM = ++FRIEND_BASE;
	@SysI18nString(content = "该玩家已经向你发出好友申请,请去确认")
	public static final Integer APPLYED = ++FRIEND_BASE;
	@SysI18nString(content = "领取体力值次数已经达到上限，不能领取")
	public static final Integer FRIEND_REWARD_FULL = ++FRIEND_BASE;
	@SysI18nString(content = "体力充沛, 不能领取好友赠送的体力")
	public static final Integer ENERGY_FULL = ++FRIEND_BASE;
	@SysI18nString(content = "该玩家可能已经取消了好友申请，请确认")
	public static final Integer NO_APPLYED = ++FRIEND_BASE;
	@SysI18nString(content = "与该玩家好友关系不存在")
	public static final Integer NOT_FRIEND = ++FRIEND_BASE;
	@SysI18nString(content = "成功向{0}发出好友申请")
	public static final Integer FRIEND_APPLY_SUCCESS = ++FRIEND_BASE;
	@SysI18nString(content = "该玩家的好友申请列表已满，不能向该玩家发送申请")
	public static final Integer CAN_NOT_SEND_APPLY = ++FRIEND_BASE;
	@SysI18nString(content = "已经向该好友赠送过体力")
	public static final Integer ENERGY_SENDED = ++FRIEND_BASE;
	@SysI18nString(content = "赠送{0}{1}点体力")
	public static final Integer GIVE_ENERGY = ++FRIEND_BASE;
	@SysI18nString(content = "获得{0}点体力，今日接收好友体力剩余{1}次")
	public static final Integer GET_ENERGY = ++FRIEND_BASE;
	@SysI18nString(content = "你今天的赠送次数已经用完了")
	public static final Integer SEND_ENERGY_TIMES_FULL = ++FRIEND_BASE;
	@SysI18nString(content = "好友今天的体力接受次数已经用完了")
	public static final Integer BE_SENDED_ENERGY_TIMES_FULL = ++FRIEND_BASE;
	
	/** 星运相关 */
	@SysI18nString(content = "占星师未开启")
	public static final Integer STARGAZER_OPEN = ++HOROSCOPE_BASE;
	@SysI18nString(content = "运魂主背包已满")
	public static final Integer HOROSCOPE_MAINBAG_FULL = ++HOROSCOPE_BASE;
	@SysI18nString(content = "运魂存储背包已满")
	public static final Integer HOROSCOPE_STRORAGE_FULL = ++HOROSCOPE_BASE;
	@SysI18nString(content = "{0}将吞噬{1},获得{2}经验")
	public static final Integer HOROSCOPE_COMPOUND = ++HOROSCOPE_BASE;
	@SysI18nString(content = "不能装备多个具有同样效果的运魂")
	public static final Integer HOROSCOPE_SAME = ++HOROSCOPE_BASE;
//	@SysI18nString(content = "占星失败,返回{0}{1}")
	@SysI18nString(content = "占星失败,获得{0}")
	public static final Integer HOROSCOPE_GAMBLE_FAIL = ++HOROSCOPE_BASE;
	@SysI18nString(content = "获得{0}运魂")
	public static final Integer HOROSCOPE_GAMBLE_SUCCESS = ++HOROSCOPE_BASE;
	@SysI18nString(content = "获得{0}运魂,并且获得{1}协助")
	public static final Integer HOROSCOPE_GAMBLE_SUCCESS_AND_OPEN_NEXT = ++HOROSCOPE_BASE;
	@SysI18nString(content = "当前运魂位置尚未开启")
	public static final Integer HOROSCOPE_SOUL_TYPE_CLOSED = ++HOROSCOPE_BASE;
	@SysI18nString(content = "没有空余的运魂位置")
	public static final Integer HOROSCOPE_SOUL_TYPE_FULL = ++HOROSCOPE_BASE;
	@SysI18nString(content = "{0}已经满级，不能再合成")
	public static final Integer HOROSCOPE_TOP = ++HOROSCOPE_BASE;
	@SysI18nString(content = "【{0} 在天命神殿刻印出了 {1}，太厉害了！】")
	public static final Integer GET_GOOD_HOROSCOPE = ++HOROSCOPE_BASE;
	@SysI18nString(content = "运魂主背包容量不足")
	public static final Integer HOROSCOPE_MAINBAG_EMPTY_SIZE_NOT_ENOUGH = ++HOROSCOPE_BASE;
	
	/** 装备强化相关 */
	@SysI18nString(content = "装备已经强化到顶级")
	public static final Integer EQUIP_UPGRADE_FULL = ++EQUIP_UPGRADE_BASE;
	@SysI18nString(content = "所需强化材料不足")
	public static final Integer EQUIP_UPGRADE_MATERIAL_NOT_ENOUGH = ++EQUIP_UPGRADE_BASE;
	@SysI18nString(content = "开孔数达到上限,不能继续开孔！")
	public static final Integer MAX_HOLE = ++EQUIP_UPGRADE_BASE;
	@SysI18nString(content = "开孔成功！")
	public static final Integer PUNCH_SUCESS = ++EQUIP_UPGRADE_BASE;
	@SysI18nString(content = "装备强化成功,装备强化等级升至{0}级！")
	public static final Integer EQUIP_UPGRADE_SUCESS = ++EQUIP_UPGRADE_BASE;
	@SysI18nString(content = "装备强化失败,装备强化等级降至{0}级！")
	public static final Integer EQUIP_UPGRADE_FAIL = ++EQUIP_UPGRADE_BASE;
	@SysI18nString(content = "装备强化失败,装备强化等级不变！")
	public static final Integer EQUIP_UPGRADE_FAIL_USE_GUARDSTONE = ++EQUIP_UPGRADE_BASE;
	@SysI18nString(content = "没有足够的守护石,请确认是否使用守护石！")
	public static final Integer NO_ENOUGH_GUARD_STONE = ++EQUIP_UPGRADE_BASE;
	@SysI18nString(content = "没有足够的幸运石,请确认是否使用幸运石！")
	public static final Integer NO_ENOUGH_FORTUNE_STONE = ++EQUIP_UPGRADE_BASE;
	@SysI18nString(content = "已装备物品不能出售！")
	public static final Integer CAN_NOT_SELL = ++EQUIP_UPGRADE_BASE;
	@SysI18nString(content = "与职业不符，不能强化！")
	public static final Integer UPGRADE_LIMITOCCUPATION = ++EQUIP_UPGRADE_BASE;
	@SysI18nString(content = "与职业不符，不能打造！")
	public static final Integer EQUIPMAKE_LIMITOCCUPATION = ++EQUIP_UPGRADE_BASE;
	@SysI18nString(content = "该装备没有随机属性不能洗炼！")
	public static final Integer NO_SPECIAL_ATTRIBUTES = ++EQUIP_UPGRADE_BASE;
	
	/** 科技相关 */
	@SysI18nString(content = "冥想力")
	public static final Integer TECHNOLOGY_POINT = ++TECHNOLOGY_BASE;
	@SysI18nString(content = "等级不够，不能升级当前科技")
	public static final Integer ROLE_LEVEL_LIMIT = ++TECHNOLOGY_BASE;
	@SysI18nString(content = "科技等级已经达到上限")
	public static final Integer TECHNOLOGY_MAX_LEVEL = ++TECHNOLOGY_BASE;
	@SysI18nString(content = "冥想力不足")
	public static final Integer TECHNOLOGY_POINTS_NOT_ENOUGH = ++TECHNOLOGY_BASE;
	
	/** 魔晶兑换相关 */
	@SysI18nString(content = "魔晶兑换次数达到上限")
	public static final Integer CRYSTALEXCHANGE_TIME_LIMIT = ++CRYSTALEXCHANGE_BASE;
	
	/** 大转盘相关 */
	@SysI18nString(content = "今日魔晶抽奖次数已经用完")
	public static final Integer NO_TURNTABLE_TIME = ++TURNTABLE_BASE;
	
	/** 训练相关 */
	@SysI18nString(content = "今日普通训练时长已经用完。")
	public static final Integer NO_NORMAL_TRAINING_TIME = ++TAINING_BASE;
	@SysI18nString(content = "今日可用于VIP训练的魔晶不足。")
	public static final Integer NO_VIP_TRAINING_CRYSTAL = ++TAINING_BASE;
	@SysI18nString(content = "角色已经达到顶级，不用再训练了。")
	public static final Integer ROLE_REACHED_MAX_LEVEL = ++TAINING_BASE;
	@SysI18nString(content = "已在训练中")
	public static final Integer BE_TRAINING = ++TAINING_BASE;
	
	/** 问答相关 */
	@SysI18nString(content = "积分")
	public static final Integer ANSWER_QUESTION_SCORE = ++QUESTION_BASE;
	@SysI18nString(content = "今日购买祈福次数已经用完。")
	public static final Integer NO_BUY_UNBLESS_TIME = ++QUESTION_BASE;
	@SysI18nString(content = "一键答题未开启")
	public static final Integer ONEKEY_ANSWER_NOT_OPEN = ++QUESTION_BASE;
	@SysI18nString(content = "积分不足")
	public static final Integer SCORE_NOT_ENOUGH = ++QUESTION_BASE;
	
	/** 任务相关 */
	@SysI18nString(content = "日常奖励宝箱已经领取过了")
	public static final Integer DAILY_QUEST_REWARED_HAS_GET = ++QUEST_BASE;
	@SysI18nString(content = "任务状态不对")
	public static final Integer DAILY_QUEST_WRONG_STATE = ++QUEST_BASE;
	@SysI18nString(content = "消耗魔晶完成任务次数已用完")
	public static final Integer DAILY_QUEST_HAVE_NO_TIMES = ++QUEST_BASE;
	@SysI18nString(content = "还有未完成的委托任务")
	public static final Integer HAS_AUTO_QUEST_NOT_COMPLETE = ++QUEST_BASE;
	@SysI18nString(content = "列表中没有可刷新的日常任务")
	public static final Integer DAILY_QUEST_CAN_NOT_REFRESH = ++QUEST_BASE;
	
	/** 宝石相关 */
	@SysI18nString(content = "今日收获宝石次数已用完 ")
	public static final Integer TODAY_HARVEST_GEM_TIME_USE_OUT = ++GEM_BASE;
	@SysI18nString(content = "宝石已达到最大强化等级 ")
	public static final Integer GEM_ITEM_MAX_GRADE=++GEM_BASE;
	@SysI18nString(content = "宝石合成成功，获得{0}", comment="{0}宝石名称")
	public static final Integer GEM_SYNTHESIS_SUCCESS=++GEM_BASE;
	@SysI18nString(content = "宝石合成失败，等级不变，获得{0}", comment="{0}宝石名称")
	public static final Integer GEM_SYNTHESIS_FAILED=++GEM_BASE;
	@SysI18nString(content = "宝石合成失败，等级下降，获得{0}", comment="{0}宝石名称")
	public static final Integer GEM_SYNTHESIS_DEGRADE=++GEM_BASE;
	@SysI18nString(content = "不能装备同类型的宝石")
	public static final Integer SAME_RALITY_GEM=++GEM_BASE;
	@SysI18nString(content = "灵图已达到最大强化等级 ")
	public static final Integer MAGIC_PAPER_ITEM_MAX_GRADE=++GEM_BASE;
	@SysI18nString(content = "灵图合成成功，获得{0}", comment="{0}灵图名称")
	public static final Integer MAGIC_PAPER_SYNTHESIS_SUCCESS=++GEM_BASE;
	@SysI18nString(content = "灵图合成失败，等级不变，获得{0}", comment="{0}灵图名称")
	public static final Integer MAGIC_PAPER_SYNTHESIS_FAILED=++GEM_BASE;
	@SysI18nString(content = "灵图合成失败，等级下降，获得{0}", comment="{0}灵图名称")
	public static final Integer MAGIC_PAPER_SYNTHESIS_DEGRADE=++GEM_BASE;
	@SysI18nString(content = "天赋碎片已达到最大强化等级 ")
	public static final Integer GIFT_CHIP_ITEM_MAX_GRADE=++GEM_BASE;
	@SysI18nString(content = "天赋碎片合成成功，获得{0}", comment="{0}天赋碎片名称")
	public static final Integer GIFT_CHIP_SYNTHESIS_SUCCESS=++GEM_BASE;
	@SysI18nString(content = "天赋碎片合成失败，等级不变，获得{0}", comment="{0}天赋碎片名称")
	public static final Integer GIFT_CHIP_SYNTHESIS_FAILED=++GEM_BASE;
	@SysI18nString(content = "天赋碎片合成失败，等级下降，获得{0}", comment="{0}天赋碎片名称")
	public static final Integer GIFT_CHIP_SYNTHESIS_DEGRADE=++GEM_BASE;
	
	/** 关卡相关 */
	@SysI18nString(content = "还未征战到当前关卡 ")
	public static final Integer STAGE_NOT_OPEN = ++STAGE_BASE;
	@SysI18nString(content = "没有足够的体力值，不能攻打当前关卡 ")
	public static final Integer ENERGY_NOT_ENOUGH = ++STAGE_BASE;
	@SysI18nString(content = "攻打该关卡需要玩家等级达到{0}级。 ")
	public static final Integer LEVEl_NOT_ENOUGH = ++STAGE_BASE;
	
	/** 竞技场相关 */
	@SysI18nString(content = "在竞技场")
	public static final Integer ARENA_BATTLE = ++ARENA_BASE;
	@SysI18nString(content = "攻打玩家的排名超出允许攻打的范围 ")
	public static final Integer ARENA_MEMBER_OUT = ++ARENA_BASE;
	@SysI18nString(content = "当前没有可以领取的竞技场奖励")
	public static final Integer ARENA_NO_RANK_REWARD = ++ARENA_BASE;
	@SysI18nString(content = "你当前排名{0},没能进入竞技场排行榜。")
	public static final Integer ARENA_NOT_IN_RANK_LIST = ++ARENA_BASE;
	@SysI18nString(content = "你当前排名{0},恭喜你进入竞技场排行榜榜单！")
	public static final Integer ARENA_IN_RANK_LIST = ++ARENA_BASE;
	@SysI18nString(content = "今日挑战次数已经用完！")
	public static final Integer NO_BATTLE_TIME = ++ARENA_BASE;
	@SysI18nString(content = "你挑战了{0},你战败了！")
	public static final Integer ATTACK_FAIL_NOTICE = ++ARENA_BASE;
	@SysI18nString(content = "你挑战了{0},你战胜了！")
	public static final Integer ATTACK_WIN_NOTICE = ++ARENA_BASE;
	@SysI18nString(content = "{0}挑战了你,你战败了！")
	public static final Integer BE_ATTACK_FAIL_NOTICE = ++ARENA_BASE;
	@SysI18nString(content = "{0}挑战了你,你战胜了！")
	public static final Integer BE_ATTACK_WIN_NOTICE = ++ARENA_BASE;
	@SysI18nString(content = "当前vip等级不能购买更多的挑战次数！")
	public static final Integer CAN_NOT_BUY_MORE_TIMES = ++ARENA_BASE;
	
	/** 技能相关 */
	@SysI18nString(content = "开启技能格子的等级不满足 ")
	public static final Integer NOT_OPEN_SLOT_CONDITION = ++SKILL_BASE;
	@SysI18nString(content = "释放该技能需要的魔法不足 ")
	public static final Integer SKILL_ENERGY_NOT_ENOUGH = ++SKILL_BASE;
	@SysI18nString(content = "该技能暂时无法学习")
	public static final Integer SKILL_CAN_NOT_STUDY_NO_TEMPLATE = ++SKILL_BASE;
	@SysI18nString(content = "不满足学习条件，不能学习")
	public static final Integer SKILL_CAN_NOT_STUDY = ++SKILL_BASE;
	@SysI18nString(content = "你已经学习了该技能，不能重复学习")
	public static final Integer SKILL_STUDYED = ++SKILL_BASE;
	@SysI18nString(content = "成功开启{0}技能")
	public static final Integer SKILL_STUDY_SUCESS = ++SKILL_BASE;
	@SysI18nString(content = "需要{0}，精英副本可获得")
	public static final Integer SKILL_CAN_NOT_STUDY_ONE = ++SKILL_BASE;
	@SysI18nString(content = "所需技能点不足")
	public static final Integer SKILL_CAN_NOT_STUDY_TWO = ++SKILL_BASE;
	@SysI18nString(content = "需要开启前置技能")
	public static final Integer SKILL_CAN_NOT_STUDY_THREE = ++SKILL_BASE;
	@SysI18nString(content = "技能未冷却, 还需要等待{0}回合")
	public static final Integer SKILL_NOT_COOL_DOWN = ++SKILL_BASE;
	
	/** Boss战相关 */
	@SysI18nString(content = "boss战尚未开启,请耐心等待boss复活 ")
	public static final Integer BOSS_WAR_NOT_OPEN = ++BOSS_BASE;
	@SysI18nString(content = "充能容量已满,不能继续充能! ")
	public static final Integer CHARGED_FULL = ++BOSS_BASE;
	@SysI18nString(content = "没有充能点,不能对boss造成伤害! ")
	public static final Integer NO_ENOUGH_CHARGED = ++BOSS_BASE;
	@SysI18nString(content = "达到鼓舞上限! ")
	public static final Integer MAX_ENCOURAGE_RATE = ++BOSS_BASE;
	@SysI18nString(content = "鼓舞成功! ")
	public static final Integer ENCOURAGE_SUCCESS = ++BOSS_BASE;
	@SysI18nString(content = "鼓舞失败! ")
	public static final Integer ENCOURAGE_FAIL = ++BOSS_BASE;
	@SysI18nString(content = "boss战进行中,没有可领取奖励! ")
	public static final Integer BOSS_WAR_NOT_REWARD = ++BOSS_BASE;
	@SysI18nString(content = "【BOSS战已结束】【{0}击杀了Boss！】")
	public static final Integer KILL_BOSS = ++BOSS_BASE;
	@SysI18nString(content = "{0}对boss造成{1}伤害 ")
	public static final Integer BOSS_RANKING = ++BOSS_BASE;
	@SysI18nString(content = "你对boss造成{0}伤害 ")
	public static final Integer BOSS_DAMAGE_DESC = ++BOSS_BASE;
	@SysI18nString(content = "【Boss战开启，勇士们一起来击退它吧！】")
	public static final Integer BOSS_WAR_OPEN= ++BOSS_BASE;
	@SysI18nString(content = "【BOSS战已结束】")
	public static final Integer BOSS_WAR_CLOSE= ++BOSS_BASE;
	@SysI18nString(content = "本次击退Boss伤害伤害排名前三：\n第一名:{0} 伤害{1}%\n第二名:{2} 伤害{3}%\n第三名:{4} 伤害{5}%")
	public static final Integer BOSS_RANK_INFO = ++BOSS_BASE;
	@SysI18nString(content = "本次伤害奖励 +{0}金币")
	public static final Integer BOSS_STAGE_REWARD = ++BOSS_BASE;
	
	/** 冥想相关 */
	@SysI18nString(content = "成功发送{0}条协助申请 ",comment="{0}申请数量")
	public static final Integer ASSIST_APPLY_SEND_SUCCESS = ++MEDITATION_BASE;
	@SysI18nString(content = "对不起，您的好友 {0} 已经完成了冥想。 ")
	public static final Integer FRIEND_MEDITATION_FINISH = ++MEDITATION_BASE;
	@SysI18nString(content = "冥想进行中才能申请好友协助 ")
	public static final Integer FRIEND_ASSIST_NEED_MEDITATION_FIRST = ++MEDITATION_BASE;	
	@SysI18nString(content = "需要更多的协助位才能邀请更多好友帮您哦 ")
	public static final Integer NEED_MORE_FRIEND_ASSIST_POSITION = ++MEDITATION_BASE;
	@SysI18nString(content = "您已经在该好友的协助队列中 ")
	public static final Integer ALREADY_IN_FRIEND_ASSIST_QUENE = ++MEDITATION_BASE;
	@SysI18nString(content = "{0}的好友协助位已满，下次请赶早。 ")
	public static final Integer FRIEND_ASSIST_QUENE_FULL = ++MEDITATION_BASE;
	@SysI18nString(content = "协助成功，今日协助好友冥想奖励次数已用完 ")
	public static final Integer ASSIST_MEDITATION_NO_REWARD = ++MEDITATION_BASE;
	
	/** gm问答相关 */
	@SysI18nString(content = "请选择正确的问题类型 ")
	public static final Integer ERROR_QUESTION_TYPE = ++GM_QUESTION_BASE;
	@SysI18nString(content = "今日提交反馈次数达到上限")
	public static final Integer SUBMIT_GM_QUESTION_TIME_USE_OUT = ++GM_QUESTION_BASE;
	
	/** 矿场相关 */
	@SysI18nString(content = "今日魔晶精炼次数已用完 ")
	public static final Integer BUY_OPEN_MINE_TIME_USE_OUT = ++MINE_BASE;
	@SysI18nString(content = "今日精炼次数已用完 ")
	public static final Integer MINE_TIME_USE_OUT = ++MINE_BASE;
	@SysI18nString(content = "恭喜精炼出{0}！ ")
	public static final Integer OPEN_GOOD_MINE_FIELD = ++MINE_BASE;
	@SysI18nString(content = "很遗憾！这是一个{0}，不能精炼宝石！ ")
	public static final Integer OPEN_BAD_MINE_FIELD = ++MINE_BASE;
	@SysI18nString(content = "没有可加速的矿地 ")
	public static final Integer CAN_NOT_SPEED = ++MINE_BASE;
	
	/** 邮件相关 */
	@SysI18nString(content = "收件人不存在 ")
	public static final Integer RECIEVER_NOT_EXIST = ++MAIL_BASE;
	@SysI18nString(content = "不能给自己发送邮件 ")
	public static final Integer CONNOT_SEND_TO_SELF = ++MAIL_BASE;
	@SysI18nString(content = "超过邮件发送上限")
	public static final Integer SEND_MAIL_NUM_LIMITED = ++ MAIL_BASE;
	@SysI18nString(content = "邮件发送成功")
	public static final Integer SEND_MAIL_SUCCESS = ++ MAIL_BASE;
	@SysI18nString(content = "邮件发送失败")
	public static final Integer SEND_MAIL_FAILED = ++ MAIL_BASE;
	@SysI18nString(content = "系统")
	public static final Integer SYSTEM_MAIL = ++ MAIL_BASE;
	
	/** 精英副本相关 */
	@SysI18nString(content = "刷新次数已用完")
	public static final Integer ELITE_REFRESH_TIME_USE_OUT = ++ ELITE_BASE;
	@SysI18nString(content = "背包已满，战斗奖励可能丢失，是否确定进入战斗？")
	public static final Integer ELITE_BAG_IS_FULL_WARNING = ++ ELITE_BASE;
	@SysI18nString(content = "很遗憾没有物品掉落")
	public static final Integer ELITE_NO_ITEM_REWARD = ++ ELITE_BASE;
	
	/** 神秘商店相关 */
	@SysI18nString(content = "神秘商店刷新次数已用完")
	public static final Integer SPECIAL_SHOP_REFRESH_TIME_USE_OUT = ++ SPECIAL_SHOP_BASE;
	
	/** 天赋相关 */
	@SysI18nString(content = "天赋点不足")
	public static final Integer GIFT_POINT_NOT_ENOUGH = ++ GIFT_BASE;
	@SysI18nString(content = "天赋状态不正确")
	public static final Integer GIFT_INVALID_STATE = ++ GIFT_BASE;
	@SysI18nString(content = "需要角色等级{0}级才能升级")
	public static final Integer HUMAN_LEVEL_NOT_ENGOUGH = ++ GIFT_BASE;
	@SysI18nString(content = "天赋已达满级")
	public static final Integer GIFT_TO_MAX_LEVEL = ++ GIFT_BASE;
	
	/** 匹配战相关 */
	@SysI18nString(content = "魔石混战活动尚未开始")
	public static final Integer MATCH_BATTLE_NOT_OPEN = ++ MATCH_BATTLE_BASE;
	@SysI18nString(content = "魔石混战活动已经开始")
	public static final Integer MATCH_BATTLE_OPEN = ++ MATCH_BATTLE_BASE;
	@SysI18nString(content = "魔石混战活动已经结束")
	public static final Integer MATCH_BATTLE_CLOSE = ++ MATCH_BATTLE_BASE;
	@SysI18nString(content = "{0} 终结了 {1} 的{2}连胜，已{3}连胜，获得{4}荣誉，{5}金币")
	public static final Integer MATCH_BATTLE_TERMINATE_AND_STREAK_WIN = ++ MATCH_BATTLE_BASE;
	@SysI18nString(content = "{0} 终结了 {1} 的{2}连胜，获得{3}荣誉，{4}金币")
	public static final Integer MATCH_BATTLE_TERMINATE_WIN = ++ MATCH_BATTLE_BASE;
	@SysI18nString(content = "{0} 击败了 {1} ，已{2}连胜，获得{3}荣誉，{4}金币")
	public static final Integer MATCH_BATTLE_STREAK_WIN = ++ MATCH_BATTLE_BASE;
	@SysI18nString(content = "{0} 击败了 {1} ，获得{2}荣誉，{3}金币")
	public static final Integer MATCH_BATTLE_WIN = ++ MATCH_BATTLE_BASE;
	@SysI18nString(content = "{0} 本轮轮空 ，获得{1}金币")
	public static final Integer MATCH_BATTLE_OUT_OF_TURN = ++ MATCH_BATTLE_BASE;
	@SysI18nString(content = "魔石混战活动结束，本次活动连胜排名：\n")
	public static final Integer MATCH_BATTLE_STREAK_WIN_RANK_HEAD = ++ MATCH_BATTLE_BASE;
	@SysI18nString(content = "第{0}名  {1} {2}连胜\n")
	public static final Integer MATCH_BATTLE_STREAK_WIN_RANK_MSG = ++ MATCH_BATTLE_BASE;
	
	/** 勇者之路相关 */
	@SysI18nString(content = "{0}在勇者之路向你发起挑战，接受挑战胜利将获得{1}金币，战败获得{2}金币")
	public static final Integer WARRIOR_BATTLE_REQUEST_CONTENT= ++ WARRIOR_BASE;
	@SysI18nString(content = "{0}在勇者之路向你发起挑战，你敢接受挑战吗？")
	public static final Integer WARRIOR_BATTLE_REQUEST_CONTENT_WITHOUT_REWARD= ++ WARRIOR_BASE;
	
	/** 角色军衔相关  */
	@SysI18nString(content = "威望值不够，无法升级军衔！")
	public static final Integer TITLE_LEVEL_UP_PRESTIGE_NOT_ENOUGH= ++ TITLE_BASE;
	@SysI18nString(content = "军衔已升至满级！")
	public static final Integer TITLE_LEVEL_UP_MAX= ++ TITLE_BASE;
	@SysI18nString(content = "当日军衔俸禄已领取！")
	public static final Integer TITLE_GET_SALARY_FINISHED= ++ TITLE_BASE;
	
	/** 军团相关 */
	@SysI18nString(content = "权限不足，不能操作")
	public static final Integer HAVE_NO_RIGHT= ++ LEGION_BASE;
	@SysI18nString(content = "玩家需要达到{0}级才能创建军团")
	public static final Integer CREATE_LEGION_LEVEL_NOT_ENOUGH= ++ LEGION_BASE;
	@SysI18nString(content = "玩家金币需要{0}才能创建军团")
	public static final Integer CREATE_LEGION_COIN_NOT_ENOUGH= ++ LEGION_BASE;
	@SysI18nString(content = "已加入军团，不能创建军团")
	public static final Integer CREATE_LEGION_IS_JOINED_LEGION= ++ LEGION_BASE;
	@SysI18nString(content = "军团名称不能为空")
	public static final Integer LEGION_NAME_CAN_NOT_EMPTY= ++ LEGION_BASE;
	@SysI18nString(content = "军团名称过长，{0}个汉字以内")
	public static final Integer LEGION_NAME_TOO_LANG= ++ LEGION_BASE;
	@SysI18nString(content = "军团名称中含有屏蔽字符")
	public static final Integer LEGION_NAME_INPUT_ERROR= ++ LEGION_BASE;
	@SysI18nString(content = "军团名称已存在")
	public static final Integer LEGION_NAME_IS_EXIST= ++ LEGION_BASE;
	@SysI18nString(content = "不在军团中")
	public static final Integer NOT_IN_LEGION= ++ LEGION_BASE;
	@SysI18nString(content = "已加入军团，不能申请加入别的军团")
	public static final Integer APPLY_LEGION_IS_JOINED_LEGION= ++ LEGION_BASE;
	@SysI18nString(content = "超出了申请次数限制{0}次")
	public static final Integer APPLY_LEGION_OVER_NUM= ++ LEGION_BASE;
	@SysI18nString(content = "申请信息已经发送")
	public static final Integer APPLY_LEGION_SUCCESS= ++ LEGION_BASE;
	@SysI18nString(content = "您已加入军团【{0}】")
	public static final Integer JOIN_LEGION_SUCCESS= ++ LEGION_BASE;
	@SysI18nString(content = "军团人数已满")
	public static final Integer JOIN_LEGION_MEMBER_IS_FULL= ++ LEGION_BASE;
	@SysI18nString(content = "你是团长，团内人员移除完才能退团")
	public static final Integer QUIT_LEGION_HAS_OTHER_MEMBER= ++ LEGION_BASE;
	@SysI18nString(content = "你是团长，退出军团将解散")
	public static final Integer QUIT_LEGION_WILL_DISSOLVE= ++ LEGION_BASE;
	@SysI18nString(content = "角色不具有踢人权限")
	public static final Integer REMOVE_LEGION_MEMBER_WITHOUT_RIGHT= ++ LEGION_BASE;
	@SysI18nString(content = "不能剔除自己")
	public static final Integer REMOVE_LEGION_CAN_NOT_SELF= ++ LEGION_BASE;
	@SysI18nString(content = "不能剔除同级别或高级别的成员")
	public static final Integer REMOVE_LEGION_MEMBER_SAME_POSITION= ++ LEGION_BASE;
	@SysI18nString(content = "只有团长才能转让军团")
	public static final Integer TRANSFER_LEGION_IS_NOT_COMMANDER= ++ LEGION_BASE;
	@SysI18nString(content = "军团已转让")
	public static final Integer LEGION_TRANSFER_SUCCESS= ++ LEGION_BASE;
	@SysI18nString(content = "只有团长才能解散军团")
	public static final Integer DISSOLVE_LEGION_IS_NOT_COMMANDER= ++ LEGION_BASE;
	@SysI18nString(content = "军团只能转让给自己的军团成员")
	public static final Integer TRANSFER_LEGION_IS_NOT_MEMBER= ++ LEGION_BASE;
	@SysI18nString(content = "军团不能转让给自己")
	public static final Integer TRANSFER_LEGION_CAN_NOT_SELF= ++ LEGION_BASE;
	@SysI18nString(content = "角色不具有审核申请权限")
	public static final Integer CHECK_APPLY_JOIN_LEGION_WITHOUT_RIGHT= ++ LEGION_BASE;
	@SysI18nString(content = "该角色已在军团【{0}】中")
	public static final Integer CHECK_APPLY_JOINED_LEGION= ++ LEGION_BASE;
	@SysI18nString(content = "你已经是团长了")
	public static final Integer APPLY_LEGION_COMMANDER_IS= ++ LEGION_BASE;
	@SysI18nString(content = "晋升团长贡献值需达到团长的10%")
	public static final Integer APPLY_LEGION_COMMANDER_EXP_NOT_ENOUGH= ++ LEGION_BASE;
	@SysI18nString(content = "晋升团长需要团长3天不在线")
	public static final Integer APPLY_LEGION_COMMANDER_OFFLINE_THREE_DAYS= ++ LEGION_BASE;
	@SysI18nString(content = "【{0}】 {1}创建了军团。")
	public static final Integer LEGION_LOG_CREATE= ++ LEGION_BASE;
	@SysI18nString(content = "【{0}】 {1}加入了军团。")
	public static final Integer LEGION_LOG_ADD= ++ LEGION_BASE;
	@SysI18nString(content = "【{0}】 {1}退出了军团。")
	public static final Integer LEGION_LOG_QUIT= ++ LEGION_BASE;
	@SysI18nString(content = "【{0}】 {1}将{2}踢出了军团。")
	public static final Integer LEGION_LOG_REMOVE_MEMBER= ++ LEGION_BASE;
	@SysI18nString(content = "【{0}】 {1}职位变动成了{2}。")
	public static final Integer LEGION_LOG_POSITION_CHANGE= ++ LEGION_BASE;
	@SysI18nString(content = "【{0}】 {1}将军团转让给了{2}。")
	public static final Integer LEGION_LOG_TRANSFER= ++ LEGION_BASE;
	@SysI18nString(content = "【{0}】 {1}获得军团个人贡献{2}点。")
	public static final Integer LEGION_LOG_HUMAN_CONTRIBUTION_ADD= ++ LEGION_BASE;
	@SysI18nString(content = "【{0}】 {1}获得军团勋章{2}点。")
	public static final Integer LEGION_LOG_MEDAL_ADD= ++ LEGION_BASE;
	@SysI18nString(content = "【{0}】 军团获得经验{1}点。")
	public static final Integer LEGION_LOG_EXPERIENCE_ADD= ++ LEGION_BASE;
	@SysI18nString(content = "【{0}】 军团获得资金{1}点。")
	public static final Integer LEGION_LOG_COIN_ADD= ++ LEGION_BASE;
	@SysI18nString(content = "军团公告过长，{0}个汉字以内")
	public static final Integer LEGION_NOTICE_TOO_LANG= ++ LEGION_BASE;
	@SysI18nString(content = "军团公告中含有屏蔽字符")
	public static final Integer LEGION_NOTICE_INPUT_ERROR= ++ LEGION_BASE;
	@SysI18nString(content = "军团已解散")
	public static final Integer LEGION_DISSOLVED= ++ LEGION_BASE;
	@SysI18nString(content = "至少捐赠1魔晶")
	public static final Integer LEGION_DONATE_ONE_CRYSTAL= ++ LEGION_BASE;
	@SysI18nString(content = "捐赠魔晶需要VIP等级{0}")
	public static final Integer DONATE_CRYSTAL_NEED_VIP= ++ LEGION_BASE;
	@SysI18nString(content = "必须是团长才能升级军团建筑")
	public static final Integer LEGION_UPGRADE_BUILDING_MUST_COMMANDER= ++ LEGION_BASE;
	@SysI18nString(content = "军团建筑已到最高级")
	public static final Integer LEGION_BUILDING_IS_MAX_LEVEL= ++ LEGION_BASE;
	@SysI18nString(content = "军团等级不够，不能升级")
	public static final Integer LEGION_LEVEL_NOT_ENOUGH= ++ LEGION_BASE;
	@SysI18nString(content = "建筑等级不够，不能升级")
	public static final Integer LEGION_BUILDING_LEVEL_NOT_ENOUGH= ++ LEGION_BASE;
	@SysI18nString(content = "军团资金不足")
	public static final Integer LEGION_COIN_NOT_ENOUGH= ++ LEGION_BASE;
	@SysI18nString(content = "【{0}】 {1}进行了{2}，获得了{3}冥想力，{4}贡献值，{5}勋章，{6}军团资金")
	public static final Integer LEGION_MEDITATION_LOG= ++ LEGION_BASE;
	@SysI18nString(content = "该商品今日已断货")
	public static final Integer LEGION_SHOP_ITEM_NOT_ENOUGH= ++ LEGION_BASE;
	@SysI18nString(content = "军团勋章不足")
	public static final Integer LEGION_MEDAL_NOT_ENOUGH= ++ LEGION_BASE;
	@SysI18nString(content = "科技已达最高级")
	public static final Integer LEGION_TECH_MAX_LEVEL= ++ LEGION_BASE;
	@SysI18nString(content = "你正在使用相同的头衔")
	public static final Integer LEGION_TITLE_IS_USING= ++ LEGION_BASE;
	@SysI18nString(content = "职位等级没达到，不能兑换该头衔")
	public static final Integer LEGION_TITLE_POSITION_NOT_ENOUGH= ++ LEGION_BASE;
	@SysI18nString(content = "该头衔已被兑换完")
	public static final Integer LEGION_TITLE_HAS_NONE= ++ LEGION_BASE;
	@SysI18nString(content = "任务次数已用完")
	public static final Integer LEGION_TASK_NO_TIMES= ++ LEGION_BASE;
	@SysI18nString(content = "任务与主题相同，奖励加倍")
	public static final Integer LEGION_TASK_SAME_THEME= ++ LEGION_BASE;
	@SysI18nString(content = "列表中没有可以刷新的悬赏任务")
	public static final Integer LEGION_TASK_CAN_NOT_REFRESH= ++ LEGION_BASE;
	@SysI18nString(content = "军团勋章")
	public static final Integer LEGION_MEDAL= ++ LEGION_BASE;
	@SysI18nString(content = "军团资金")
	public static final Integer LEGION_COIN= ++ LEGION_BASE;
	@SysI18nString(content = "军团经验")
	public static final Integer LEGION_EXPERICENCE= ++ LEGION_BASE;
	@SysI18nString(content = "个人贡献")
	public static final Integer LEGION_SELF_CONTRIBUTION= ++ LEGION_BASE;
	@SysI18nString(content = "任务积分")
	public static final Integer LEGION_TASK_SCORE= ++ LEGION_BASE;
	@SysI18nString(content = "军团等级未开启")
	public static final Integer LEGION_LEVEL_NOT_OPEN= ++ LEGION_BASE;
	
	/** 神魄相关 */
	@SysI18nString(content = "装备位强化角色等级不足")
	public static final Integer UPGRADE_EQUIP_BIT_LEVEL_NOT_ENOUGH= ++ GODSOUL_BASE;
	@SysI18nString(content = "装备位强化已达灵图决定上限")
	public static final Integer UPGRADE_EQUIP_BIT_TO_PAPER_MAX= ++ GODSOUL_BASE;
	@SysI18nString(content = "装备位强化角色金币不足")
	public static final Integer UPGRADE_EQUIP_BIT_COIN_NOT_ENOUGH= ++ GODSOUL_BASE;
	@SysI18nString(content = "强化没有成功")
	public static final Integer UPGRADE_EQUIP_BIT_FAILED= ++ GODSOUL_BASE;
	@SysI18nString(content = "已强化到最高等级")
	public static final Integer UPGRADE_EQUIP_BIT_TO_MAX= ++ GODSOUL_BASE;
	@SysI18nString(content = "材料不足，无法升级")
	public static final Integer UPGRADE_MAGIC_PAPER_MATERIAL_NOT_ENOUGH= ++ GODSOUL_BASE;
	
	/** 战俘营相关 */
	@SysI18nString(content = "身份不合法")
	public static final Integer PRISON_IDENTITY_ILLEGAL = ++PRISON_BASE;
	@SysI18nString(content = "在战俘营")
	public static final Integer PRISON_BATTLE = ++PRISON_BASE;
	@SysI18nString(content = "今日抓捕次数已用完")
	public static final Integer NO_MORE_ARREST_TIMES = ++PRISON_BASE;
	@SysI18nString(content = "当前vip等级不能购买更多的抓捕次数")
	public static final Integer CAN_NOT_BUY_MORE_ARREST_TIMES = ++PRISON_BASE;
	@SysI18nString(content = "他是你的奴隶，不如去释放他吧")
	public static final Integer RESCUE_SELF_SLAVE = ++PRISON_BASE;
	@SysI18nString(content = "今日营救次数已用完")
	public static final Integer NO_MORE_RESCUE_TIMES = ++PRISON_BASE;
	@SysI18nString(content = "他可是你的主人，换个人求救吧")
	public static final Integer SOS_SELF_MASTER = ++PRISON_BASE;
	@SysI18nString(content = "今日求救次数已用完")
	public static final Integer NO_MORE_SOS_TIMES = ++PRISON_BASE;
	@SysI18nString(content = "今日互动次数已用完")
	public static final Integer NO_MORE_INTERACT_TIMES = ++PRISON_BASE;
	@SysI18nString(content = "今日反抗次数已用完")
	public static final Integer NO_MORE_REVOLT_TIMES = ++PRISON_BASE;
	@SysI18nString(content = "俘虏已满，释放俘虏才可以抓捕新的俘虏")
	public static final Integer SLAVE_IS_FULL = ++PRISON_BASE;
	@SysI18nString(content = "已经是你的俘虏了，不能抓捕")
	public static final Integer IS_YOUR_SLAVE = ++PRISON_BASE;
	@SysI18nString(content = "等级差大于10级")
	public static final Integer OVER_LEVEL_DIFF_LIMIT = ++PRISON_BASE;
	@SysI18nString(content = "抓捕失败！")
	public static final Integer PRISON_ARREST_FAILED = ++PRISON_BASE;
	@SysI18nString(content = "抓捕成功！")
	public static final Integer PRISON_ARREST_SUCCESS = ++PRISON_BASE;
	@SysI18nString(content = "互动保护中，请稍后再试...")
	public static final Integer PRISON_INTERACTING = ++PRISON_BASE;
	@SysI18nString(content = "战斗中，请稍后再试...")
	public static final Integer PRISON_FIGHTING = ++PRISON_BASE;
	@SysI18nString(content = "战俘营已达到当日最大经验")
	public static final Integer PRISON_OVER_MAX_EXPERIENCE = ++PRISON_BASE;
	@SysI18nString(content = "你在战俘营的身份已由主人变为自由人")
	public static final Integer PRISON_MASTER_TO_FREEMAN = ++PRISON_BASE;
	@SysI18nString(content = "你在战俘营的身份已由奴隶变为自由人")
	public static final Integer PRISON_SLAVE_TO_FREEMAN = ++PRISON_BASE;
	@SysI18nString(content = "你在战俘营的身份已由自由人变为奴隶")
	public static final Integer PRISON_FREEMAN_TO_SLAVE = ++PRISON_BASE;
	// 战俘营邮件相关
	@SysI18nString(content = "【战俘营】你的俘虏时间已满")
	public static final Integer PRISON_MAIL_THEME_SLAVE_OVER_TIME = ++PRISON_BASE;
	@SysI18nString(content = "【战俘营】你的俘虏被抢走")
	public static final Integer PRISON_MAIL_THEME_SLAVE_LOOTED = ++PRISON_BASE;
	@SysI18nString(content = "【战俘营】你的俘虏反抗成功")
	public static final Integer PRISON_MAIL_THEME_SLAVE_REVOLTED = ++PRISON_BASE;
	@SysI18nString(content = "【战俘营】你的俘虏被营救")
	public static final Integer PRISON_MAIL_THEME_SLAVE_RESCUED = ++PRISON_BASE;
	@SysI18nString(content = "【战俘营】你的俘虏{0}为你劳作时间满24小时，自动恢复了自由身，你获得俘虏劳作经验+{1}")
	public static final Integer PRISON_MAIL_CONTENT_SLAVE_OVER_TIME = ++PRISON_BASE;
	@SysI18nString(content = "【战俘营】你的俘虏{0}被{1}抢走，你获得俘虏劳作经验+{2}")
	public static final Integer PRISON_MAIL_CONTENT_SLAVE_LOOTED = ++PRISON_BASE;
	@SysI18nString(content = "【战俘营】你的俘虏{0}反抗你获胜，恢复了自由身，你获得俘虏劳作经验+{1}")
	public static final Integer PRISON_MAIL_CONTENT_SLAVE_REVOLTED = ++PRISON_BASE;
	@SysI18nString(content = "【战俘营】你的俘虏{0}被{1}成功营救，恢复了自由身，你获得俘虏劳作经验+{2}")
	public static final Integer PRISON_MAIL_CONTENT_SLAVE_RESCUED = ++PRISON_BASE;
	
	/** 角斗场相关 */
	@SysI18nString(content = "在角斗场")
	public static final Integer ABATTOIR_WRESTLE = ++ABATTOIR_BASE;
	@SysI18nString(content = "已没有角斗次数")
	public static final Integer NO_MORE_ABATTOIR_WRESTLE_TIMES = ++ABATTOIR_BASE;
	@SysI18nString(content = "角斗场房间不存在")
	public static final Integer ABATTOIR_ROOM_NOT_EXIST = ++ABATTOIR_BASE;
	@SysI18nString(content = "房间保护中...")
	public static final Integer ABATTOIR_ROOM_PROTECTING = ++ABATTOIR_BASE;
	@SysI18nString(content = "抢夺成功后，将与你当前房间互换")
	public static final Integer ABATTOIR_ROOM_WILL_CHANGE = ++ABATTOIR_BASE;
	@SysI18nString(content = "房间抢夺中，请稍后再试")
	public static final Integer ABATTOIR_ROOM_IS_FIGHTING = ++ABATTOIR_BASE;
	@SysI18nString(content = "抢夺房间成功，你成为房主")
	public static final Integer ABATTOIR_LOOT_ROOM_SUCCESS = ++ABATTOIR_BASE;
	@SysI18nString(content = "抢夺房间失败")
	public static final Integer ABATTOIR_LOOT_ROOM_FAILED = ++ABATTOIR_BASE;
	@SysI18nString(content = "已退出房间")
	public static final Integer ABATTOIR_QUIT_ROOM_SUCCESS = ++ABATTOIR_BASE;
	@SysI18nString(content = "当前vip等级不能购买更多的角斗次数")
	public static final Integer CAN_NOT_BUY_MORE_ABATTOIR_WRESTLE_TIMES = ++ABATTOIR_BASE;
	@SysI18nString(content = "你在角斗场的房间已被抢夺")
	public static final Integer  ABATTOIR_ROOM_BE_LOOTED = ++ABATTOIR_BASE;
	
	/** 嗜血神殿相关 */
	@SysI18nString(content = "在嗜血神殿")
	public static final Integer BLOOD_TEMPLE_WRESTLE = ++BLOOD_TEMPLE_BASE;
	@SysI18nString(content = "嗜血神殿需要军衔达到【{0}】才能开启")
	public static final Integer BLOOD_TEMPLE_OPEN_TITLE = ++BLOOD_TEMPLE_BASE;
	@SysI18nString(content = "已没有角斗次数")
	public static final Integer NO_MORE_BLOOD_TEMPLE_WRESTLE_TIMES = ++BLOOD_TEMPLE_BASE;
	@SysI18nString(content = "嗜血神殿房间不存在")
	public static final Integer BLOOD_TEMPLE_ROOM_NOT_EXIST = ++BLOOD_TEMPLE_BASE;
	@SysI18nString(content = "房间保护中...")
	public static final Integer BLOOD_TEMPLE_ROOM_PROTECTING = ++BLOOD_TEMPLE_BASE;
	@SysI18nString(content = "抢夺成功后，将与你当前房间互换")
	public static final Integer BLOOD_TEMPLE_ROOM_WILL_CHANGE = ++BLOOD_TEMPLE_BASE;
	@SysI18nString(content = "房间抢夺中，请稍后再试")
	public static final Integer BLOOD_TEMPLE_ROOM_IS_FIGHTING = ++BLOOD_TEMPLE_BASE;
	@SysI18nString(content = "抢夺房间成功，你成为房主")
	public static final Integer BLOOD_TEMPLE_LOOT_ROOM_SUCCESS = ++BLOOD_TEMPLE_BASE;
	@SysI18nString(content = "抢夺房间失败")
	public static final Integer BLOOD_TEMPLE_LOOT_ROOM_FAILED = ++BLOOD_TEMPLE_BASE;
	@SysI18nString(content = "已退出房间")
	public static final Integer BLOOD_TEMPLE_QUIT_ROOM_SUCCESS = ++BLOOD_TEMPLE_BASE;
	@SysI18nString(content = "当前vip等级不能购买更多的抢夺次数")
	public static final Integer CAN_NOT_BUY_MORE_BLOOD_TEMPLE_WRESTLE_TIMES = ++BLOOD_TEMPLE_BASE;
	@SysI18nString(content = "你在嗜血神殿的房间已被抢夺")
	public static final Integer  BLOOD_TEMPLE_ROOM_BE_LOOTED = ++BLOOD_TEMPLE_BASE;
	
	/** 精灵相关 */
	@SysI18nString(content = "灵气值")
	public static final Integer AURA = ++SPRITE_BASE;
	@SysI18nString(content = "星魂")
	public static final Integer STAR_SOUL = ++SPRITE_BASE;
	@SysI18nString(content = "灵气值不足")
	public static final Integer AURA_NOT_ENOUTH = ++SPRITE_BASE;
	@SysI18nString(content = "所需精魂值不足")
	public static final Integer SPRITE_SOUL_NOT_ENOUTH = ++SPRITE_BASE;
	@SysI18nString(content = "精灵背包中没有空余位置了")
	public static final Integer SPRITE_BAG_NO_SPACE = ++SPRITE_BASE;
	@SysI18nString(content = "恭喜您获得了精灵 {0}")
	public static final Integer SPRITE_PUB_PUT_SUCCESS = ++SPRITE_BASE;
	@SysI18nString(content = "请先激活前置星座")
	public static final Integer ACTIVATE_PRESIGN_FIRST = ++SPRITE_BASE;
	@SysI18nString(content = "星魂值不足")
	public static final Integer STAR_SOUL_NOT_ENOUGH = ++SPRITE_BASE;
	@SysI18nString(content = "不能装备多个相同类型的精灵")
	public static final Integer CAN_NOT_EQUIP_MORE_SAME_TYPE_SPRITE = ++SPRITE_BASE;
	@SysI18nString(content = "精灵已经达到顶级")
	public static final Integer SPRITE_MAX_LEVEL = ++SPRITE_BASE;
	@SysI18nString(content = "对酒必胜次数已经用完")
	public static final Integer SPRITE_PUB_SUCCEED_NO_TIMES = ++SPRITE_BASE;
	@SysI18nString(content = "开启下一个对酒页签需要等级达到{0}级!")
	public static final Integer SPRITE_PUB_HUMAN_LEVEL_LIMITED = ++SPRITE_BASE;
	
	/** 充值相关 */
	@SysI18nString(content = "您还未首充，不能领取奖励")
	public static final Integer NO_FIRST_RECHARGE = ++RECHARE_BASE;
	@SysI18nString(content = "首充奖励已领取")
	public static final Integer FIRST_RECHARGE_FINISHED = ++RECHARE_BASE;
	@SysI18nString(content = "不在活动时间范围内")
	public static final Integer NOT_IN_TIME = ++RECHARE_BASE;
	@SysI18nString(content = "没有直充奖励可领取")
	public static final Integer SINGLE_RECHARGE_FINISHED = ++RECHARE_BASE;
	@SysI18nString(content = "没有累充奖励可领取")
	public static final Integer TOTAL_RECHARGE_FINISHED = ++RECHARE_BASE;
	
	/** 战神之巅相关 */
	@SysI18nString(content = "在战神之巅")
	public static final Integer MARS_BATTLE = ++MARS_BASE;
	@SysI18nString(content = "当前vip等级不能购买更多的加倍次数")
	public static final Integer CAN_NOT_BUY_MORE_MULTIPLE_TIMES = ++MARS_BASE;
	@SysI18nString(content = "今日挑战次数已用完")
	public static final Integer HAVE_NO_KILL_TIMES = ++MARS_BASE;
	@SysI18nString(content = "当前vip等价不能加{0}倍")
	public static final Integer MARS_CAN_NOT_MULTIPLE = ++MARS_BASE;
	@SysI18nString(content = "今日加倍次数已用完")
	public static final Integer HAVE_NO_MULTIPLE_TIMES = ++MARS_BASE;
	
	/** 军团Boss战相关 */
	@SysI18nString(content = "boss战尚未开启,请耐心等待boss复活 ")
	public static final Integer LEGION_BOSS_NOT_OPEN = ++LEGION_BOSS_BASE;
	@SysI18nString(content = "boss战刚开启，冷却中，请等待")
	public static final Integer LEGION_BOSS_JUST_OPEN = ++LEGION_BOSS_BASE;
	@SysI18nString(content = "你还没有加入军团，不能进入军团boss战")
	public static final Integer LEGION_BOSS_NOT_JOINED = ++LEGION_BOSS_BASE;
	@SysI18nString(content = "充能容量已满,不能继续充能! ")
	public static final Integer LEGION_BOSS_CHARGED_FULL = ++BOSS_BASE;
	@SysI18nString(content = "没有充能点,不能对boss造成伤害! ")
	public static final Integer LEGION_BOSS_NO_ENOUGH_CHARGED = ++BOSS_BASE;
	@SysI18nString(content = "达到鼓舞上限! ")
	public static final Integer LEGION_BOSS_MAX_ENCOURAGE_RATE = ++BOSS_BASE;
	@SysI18nString(content = "鼓舞成功! ")
	public static final Integer LEGION_BOSS_ENCOURAGE_SUCCESS = ++BOSS_BASE;
	@SysI18nString(content = "鼓舞失败! ")
	public static final Integer LEGION_BOSS_ENCOURAGE_FAIL = ++BOSS_BASE;
	@SysI18nString(content = "boss战进行中,没有可领取奖励! ")
	public static final Integer LEGION_BOSS_NOT_REWARD = ++BOSS_BASE;
	@SysI18nString(content = "【军团BOSS战已结束】【{0}击杀了Boss！】")
	public static final Integer KILL_LEGION_BOSS = ++BOSS_BASE;
	@SysI18nString(content = "{0}对boss造成{1}伤害 ")
	public static final Integer MEMBER_BOSS_RANKING = ++BOSS_BASE;
	@SysI18nString(content = "{0}军团对boss造成{1}伤害 ")
	public static final Integer LEGION_BOSS_RANKING = ++BOSS_BASE;
	@SysI18nString(content = "你对boss造成{0}伤害 ")
	public static final Integer SELF_BOSS_DAMAGE_DESC = ++BOSS_BASE;
	@SysI18nString(content = "{0}军团对boss造成{1}伤害 ")
	public static final Integer LEGION_BOSS_DAMAGE_DESC = ++BOSS_BASE;
	@SysI18nString(content = "【军团Boss战开启，勇士们一起来击退它吧！】 ")
	public static final Integer LEGION_BOSS_OPEN= ++BOSS_BASE;
	@SysI18nString(content = "【军团BOSS战已结束】")
	public static final Integer LEGION_BOSS_CLOSE= ++BOSS_BASE;
	@SysI18nString(content = "本次击退Boss伤害伤害排名前三：\n第一名:{0} 伤害{1}%\n第二名:{2} 伤害{3}%\n第三名:{4} 伤害{5}%")
	public static final Integer LEGION_BOSS_RANK_INFO = ++BOSS_BASE;
	
	/** 军团矿战战相关 */
	@SysI18nString(content = "你还没有加入军团，不能进入军团矿战")
	public static final Integer LEGION_MINE_NOT_JOINED = ++LEGION_MINE_BASE;
	@SysI18nString(content = "你所在的军团没有进入军团boss战前两名，不能参加军团矿战")
	public static final Integer LEGION_MINE_NO_RIGHT = ++LEGION_MINE_BASE;
	@SysI18nString(content = "你方占领红矿，全员占领值收益增加{0}%")
	public static final Integer RED_MINE_LEGION_BUF = ++LEGION_MINE_BASE;
	@SysI18nString(content = "你方占领矿地少于{0}个，全员攻防增加{1}%")
	public static final Integer LEGION_BUF = ++LEGION_MINE_BASE;
	@SysI18nString(content = "鼓舞失败")
	public static final Integer LEGION_MINE_ENCOURAGE_FAIL = ++LEGION_MINE_BASE;
	@SysI18nString(content = "鼓舞成功，攻击力加{0}%，还可鼓舞{1}次")
	public static final Integer LEGION_MINE_ENCOURAGE_SUCCESS = ++LEGION_MINE_BASE;
	@SysI18nString(content = "达到鼓舞上限! ")
	public static final Integer LEGION_MINE_MAX_ENCOURAGE_RATE = ++BOSS_BASE;
	@SysI18nString(content = "对方战斗中，请稍后...")
	public static final Integer LEGION_MINE_BATTLING = ++LEGION_MINE_BASE;
	@SysI18nString(content = "您主动出击失败了，收获CD减少{0}s ")
	public static final Integer LEGION_MINE_BATTLE_FAILED = ++LEGION_MINE_BASE;
	@SysI18nString(content = "您主动出击胜利了，收获CD减少{0}s")
	public static final Integer LEGION_MINE_BATTLE_SUCCESS_CD = ++LEGION_MINE_BASE;
	@SysI18nString(content = "获得强化石{0}个，矿战结束领取")
	public static final Integer LEGION_MINE_BATTLE_SUCCESS_ITEM = ++LEGION_MINE_BASE;
	@SysI18nString(content = "您被打败了，返回大本营，获得指令【{0}】")
	public static final Integer LEGION_MINE_BE_BATTLED_FAILED = ++LEGION_MINE_BASE;
	@SysI18nString(content = "没有占领矿地，不能收获")
	public static final Integer LEGION_MINE_CANT_HARVEST = ++LEGION_MINE_BASE;
	@SysI18nString(content = "收获占领值 +{0}")
	public static final Integer HARVEST_OCCUPY_VALUE = ++LEGION_MINE_BASE;
	@SysI18nString(content = "没有占领矿地，不能侦查")
	public static final Integer LEGION_MINE_CANT_WATCH = ++LEGION_MINE_BASE;
	@SysI18nString(content = "没有占领矿地，不能骚扰")
	public static final Integer LEGION_MINE_CANT_DISTURB = ++LEGION_MINE_BASE;
	@SysI18nString(content = "骚扰成功")
	public static final Integer LEGION_MINE_DISTURB_SUCCESS = ++LEGION_MINE_BASE;
	@SysI18nString(content = "您被敌方骚扰了，收获CD增加{0}s")
	public static final Integer LEGION_MINE_BE_DISTURBED = ++LEGION_MINE_BASE;
	@SysI18nString(content = "相邻矿地没有敌对军团，不能骚扰")
	public static final Integer LEGION_MINE_DISTURB_NONE = ++LEGION_MINE_BASE;
	@SysI18nString(content = "相邻矿地没有敌对军团，不能侦查")
	public static final Integer LEGION_MINE_WATCH_NONE = ++LEGION_MINE_BASE;
	@SysI18nString(content = "【军团矿战开启，击杀军团Boss伤害前二名的军团可以参加！】")
	public static final Integer LEGION_MINE_WAR_OPEN = ++LEGION_MINE_BASE;
	@SysI18nString(content = "【军团矿战已结束】")
	public static final Integer LEGION_MINE_WAR_CLOSED = ++LEGION_MINE_BASE;
	@SysI18nString(content = "【军团矿战已结束，{0}军团获得了胜利！】")
	public static final Integer LEGION_MINE_WAR_REWARD = ++LEGION_MINE_BASE;
	
	/** 角色秘药相关 */
	@SysI18nString(content = "秘药使用已达VIP上限，提高VIP等级可使用更多秘药")
	public static final Integer NOSTRUM_TO_MAX_NUM = ++NOSTRUM_BASE;
	
	/** 押运相关 */
	@SysI18nString(content = "【{0}】成功抢夺了【{1}】【{2}】的队伍，抢到了{3}金币")
	public static final Integer ESCORT_ROB_LOG = ++ESCORT_BASE;
	@SysI18nString(content = "怪物品质已经是最高了")
	public static final Integer ESCORT_MONSTER_IS_HIGHEST = ++ESCORT_BASE;
	@SysI18nString(content = "当前vip等级不具备召唤权限")
	public static final Integer ESCORT_NO_CALL_RIGHT = ++ESCORT_BASE;
	@SysI18nString(content = "不是你的好友，不能邀请")
	public static final Integer ESCORT_INVITE_NOT_FRIEND = ++ESCORT_BASE;
	@SysI18nString(content = "该好友已下线")
	public static final Integer ESCORT_INVITE_FRIEND_OFFLINE = ++ESCORT_BASE;
	@SysI18nString(content = "现在不能邀请")
	public static final Integer ESCORT_CAN_NOT_INVITE = ++ESCORT_BASE;
	@SysI18nString(content = "该好友已经取消邀请")
	public static final Integer ESCORT_ABORTED_INVITE = ++ESCORT_BASE;
	@SysI18nString(content = "已经接受别人邀请")
	public static final Integer ESCORT_RECEIVED_INVITE = ++ESCORT_BASE;
	@SysI18nString(content = "没有加入军团，不能祈福")
	public static final Integer ESCORT_PRAY_NO_LEGION = ++ESCORT_BASE;
	@SysI18nString(content = "当前vip等级不具备召唤神灵权限")
	public static final Integer ESCORT_NO_LEGION_PRAY_RIGHT = ++ESCORT_BASE;
	@SysI18nString(content = "军团中已经有成员祈福")
	public static final Integer ESCORT_LEGION_PRAYED = ++ESCORT_BASE;
	@SysI18nString(content = "不能拦截自己押运的货物")
	public static final Integer ESCORT_CAN_NOT_ROB_SELF = ++ESCORT_BASE;
	@SysI18nString(content = "不能拦截自己协助押运的货物")
	public static final Integer ESCORT_CAN_NOT_ROB_HELP = ++ESCORT_BASE;
	@SysI18nString(content = "当日拦截次数已用完")
	public static final Integer ESCORT_NO_ROB_NUM = ++ESCORT_BASE;
	@SysI18nString(content = "对方被拦截中，请稍后再来")
	public static final Integer ESCORT_BE_ROBING = ++ESCORT_BASE;
	@SysI18nString(content = "对方已经遭到太多次拦截")
	public static final Integer ESCORT_NO_BE_ROBBED_NUM = ++ESCORT_BASE;
	@SysI18nString(content = "已达当前VIP最大购买次数")
	public static final Integer CAN_NOT_BUY_ROB_NUM = ++ESCORT_BASE;
	@SysI18nString(content = "请先领取押运奖励再开始押运")
	public static final Integer ESCORT_HAS_REWARD = ++ESCORT_BASE;
	@SysI18nString(content = "正在押运中，不能押运")
	public static final Integer IS_ESCORTING = ++ESCORT_BASE;
	@SysI18nString(content = "当日押运次数已用完")
	public static final Integer HAS_NO_ESCORT_NUM = ++ESCORT_BASE;
	@SysI18nString(content = "拦截了你")
	public static final Integer ESCORT_ROB_BATTLE = ++ESCORT_BASE;
	@SysI18nString(content = "拦截了你的好友")
	public static final Integer ESCORT_ROB_FRIEND_BATTLE = ++ESCORT_BASE;
	@SysI18nString(content = "【12点-13点跑商押运收益增加10%，快来参加吧！】")
	public static final Integer ESCORT_AMEND_START = ++ESCORT_BASE;
	
	/** 预见相关 */
	@SysI18nString(content = "该页要等级{0}才可见")
	public static final Integer PREDICT_PAGE_NOT_VISIBLE = ++PREDICT_BASE;
	@SysI18nString(content = "该预见已激活")
	public static final Integer PREDICT_HAD_ACTIVATED = ++PREDICT_BASE;
	@SysI18nString(content = "请按顺序激活")
	public static final Integer PREDICT_ACTIVATE_NEED_ORDER = ++PREDICT_BASE;
	@SysI18nString(content = "等级达到{0}级可激活")
	public static final Integer PREDICT_ACTIVATE_LEVEL = ++PREDICT_BASE;
	
	/** 试炼相关 */
	@SysI18nString(content = "+{0}灵气值")
	public static final Integer ANIMA_OBTAIN = ++REFINE_BASE;
}
