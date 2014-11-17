package com.hifun.soul.gameserver.role.properties;

import com.hifun.soul.core.annotation.Comment;
import com.hifun.soul.core.annotation.Type;

/**
 * 角色特有的Int属性;
 * 
 * @author crazyjohn
 * 
 */
public class HumanIntProperty extends IntPropertyCacheSet {
	/** 基础整型属性索引开始值 */
	public static int _BEGIN = 0;

	/** 基础整型属性索引结束值 */
	public static int _END = _BEGIN;

	@Comment(content = "等级")
	@Type(Integer.class)
	public static final int LEVEL = _BEGIN;

	@Comment(content = "VIP的等级")
	@Type(Integer.class)
	public static final int VIPLEVEL = ++_END;

	@Comment(content = "当前精力值")
	@Type(Integer.class)
	public static final int ENERGY = ++_END;

	@Comment(content = "最大精力值")
	@Type(Integer.class)
	public static final int MAX_ENERGY = ++_END;

	@Comment(content = "主城的等级")
	@Type(Integer.class)
	public static final int HOME_LEVEL = ++_END;

	@Comment(content = "职业")
	@Type(Integer.class)
	public static final int OCCUPATION = ++_END;

	@Comment(content = "金币")
	@Type(Integer.class)
	public static final int COIN = ++_END;

	@Comment(content = "魔晶石")
	@Type(Integer.class)
	public static final int CRYSTAL = ++_END;

	@Comment(content = "当前经验值")
	@Type(Integer.class)
	public static final int EXPERIENCE = ++_END;

	@Comment(content = "最大经验值")
	@Type(Integer.class)
	public static final int MAX_EXPERIENCE = ++_END;

	@Comment(content = "背包大小")
	@Type(Integer.class)
	public static final int BAG_SIZE = ++_END;

	@Comment(content = "剩余收税次数")
	@Type(Integer.class)
	public static final int LEVY_REMAIN_TIMES = ++_END;

	@Comment(content = "剩余收获宝石次数")
	@Type(Integer.class)
	public static final int HARVEST_GEM_REMAIN_TIMES = ++_END;

	@Comment(content = "系统分配的火焰点数")
	@Type(Integer.class)
	public static final int SYSTEM_FIRE = ++_END;

	@Comment(content = "系统分配的敏捷点数")
	@Type(Integer.class)
	public static final int SYSTEM_ICE = ++_END;

	@Comment(content = "系统分配的体力点数")
	@Type(Integer.class)
	public static final int SYSTEM_LIGHT = ++_END;

	@Comment(content = "系统分配的智力点数")
	@Type(Integer.class)
	public static final int SYSTEM_SHADOW = ++_END;

	@Comment(content = "系统分配的精神点数")
	@Type(Integer.class)
	public static final int SYSTEM_NATURE = ++_END;

	@Comment(content = "剩余科技点数")
	@Type(Integer.class)
	public static final int TECHNOLOGY_POINT = ++_END;

	@Comment(content = "剩余普通训练时长")
	@Type(Integer.class)
	public static final int NORMAL_TRAINING_TIME = ++_END;

	@Comment(content = "vip训练剩余魔晶消费数")
	@Type(Integer.class)
	public static final int VIP_TRAINING_CRYSTAL_CONSUME_NUM = ++_END;

	@Comment(content = "正在训练的训练类型")
	@Type(Integer.class)
	public static final int CURRENT_TRAINING_TYPE = ++_END;

	@Comment(content = "占星次数")
	@Type(Integer.class)
	public static final int ASTROLOGICAL_TIME = ++_END;

	@Comment(content = "魔晶兑换次数")
	@Type(Integer.class)
	public static final int CRYSTAL_EXCHANGE_TIME = ++_END;

	@Comment(content = "当前日常任务积分")
	@Type(Integer.class)
	public static final int DAILY_SCORE = ++_END;

	@Comment(content = "在线奖励领取次数")
	@Type(Integer.class)
	public static final int ONLINE_REWARD_TIME = ++_END;

	@Comment(content = "大转盘每日领奖次数")
	@Type(Integer.class)
	public static final int TURNTABLE_TIME = ++_END;

	@Comment(content = "战斗完未领奖的关卡")
	@Type(Integer.class)
	public static final int STAGE_ID = ++_END;

	@Comment(content = "抽取的奖品id,因为策划需求变更，数据存储结构要变,该数据废弃掉")
	@Type(Integer.class)
	public static final int ITEM_ID = ++_END;

	@Comment(content = "下一个关卡")
	@Type(Integer.class)
	public static final int NEXT_STAGE_ID = ++_END;

	@Comment(content = "已充值数量")
	@Type(Integer.class)
	public static final int RECHARGED_NUM = ++_END;

	@Comment(content = "未分配的点数")
	@Type(Integer.class)
	public static final int UNDISTRIBUTED_POINT = ++_END;

	@Comment(content = "连续登陆天数")
	@Type(Integer.class)
	public static final int LOGINREWARD_DAYS = ++_END;

	@Comment(content = "当天剩余领奖次数")
	@Type(Integer.class)
	public static final int LOGINREWARD_TIMES = ++_END;

	@Comment(content = "特殊奖励一状态(废除,留待以后备用,删除影响原来数据)")
	@Type(Integer.class)
	public static final int LOGINREWARD_ONE = ++_END;

	@Comment(content = "特殊奖励二状态(废除,留待以后备用,删除影响原来数据)")
	@Type(Integer.class)
	public static final int LOGINREWARD_TWO = ++_END;

	@Comment(content = "特殊奖励三状态(废除,留待以后备用,删除影响原来数据)")
	@Type(Integer.class)
	public static final int LOGINREWARD_THREE = ++_END;

	@Comment(content = "剩余竞技场战斗次数")
	@Type(Integer.class)
	public static final int ARENA_BATTLE_TIME = ++_END;

	@Comment(content = "竞技场荣誉值")
	@Type(Integer.class)
	public static final int ARENA_HONOR = ++_END;

	@Comment(content = "当前军衔")
	@Type(Integer.class)
	public static final int CURRENT_TITLE = ++_END;

	@Comment(content = "是否领取当日军衔俸禄")
	@Type(Integer.class)
	public static final int TITLE_IS_GOT_SALARY = ++_END;

	@Comment(content = "当前军衔可携带技能数量")
	@Type(Integer.class)
	public static final int TITLE_SKILL_NUM = ++_END;

	@Comment(content = "今日已购买体力值次数")
	@Type(Integer.class)
	public static final int BUY_ENERGY_TIMES = ++_END;

	@Comment(content = "剩余购买体力值次数")
	@Type(Integer.class)
	public static final int BUY_ENERGY_REMAIN_TIMES = ++_END;

	@Comment(content = "体力值恢复时长（分钟）")
	@Type(Integer.class)
	public static final int ENERGY_RECOVER_INTERVAL = ++_END;

	@Comment(content = "每次恢复的体力值")
	@Type(Integer.class)
	public static final int ENERGY_RECOVER_NUM = ++_END;

	@Comment(content = "购买体力值的花费的魔晶数")
	@Type(Integer.class)
	public static final int BUY_ENERGY_CRYSTAL_COST_NUM = ++_END;

	@Comment(content = "购买体力值的活动的体力点数")
	@Type(Integer.class)
	public static final int BUY_ENERGY_ADD_NUM = ++_END;

	@Comment(content = "剩余协助好友冥想次数")
	@Type(Integer.class)
	public static final int LEFT_ASSIST_MEDITATION_TIMES = ++_END;

	@Comment(content = "竞技场最大购买次数")
	@Type(Integer.class)
	public static final int MAX_ARENA_BUY_TIMES = ++_END;

	@Comment(content = "每日免费占星次数")
	@Type(Integer.class)
	public static final int HOROSCOPE_GAMBLE_TIMES = ++_END;

	@Comment(content = "星运仓库格子开启数量")
	@Type(Integer.class)
	public static final int HOROSCOPE_STORAGE_SIZE = ++_END;

	@Comment(content = "技能点")
	@Type(Integer.class)
	public static final int SKILL_POINTS = ++_END;

	@Comment(content = "提交gm反馈次数")
	@Type(Integer.class)
	public static final int SUBMIT_GM_QUESTION_TIMES = ++_END;

	@Comment(content = "发送邮件次数")
	@Type(Integer.class)
	public static final int SEND_MAIL_TIMES = ++_END;

	@Comment(content = "奇遇问答剩余题数")
	@Type(Integer.class)
	public static final int REMAIN_QUESTION_NUM = ++_END;

	@Comment(content = "上次关卡评星")
	@Type(Integer.class)
	public static final int LAST_STAGE_BATTLE_STAR = ++_END;

	@Comment(content = "好友领取精力的次数")
	@Type(Integer.class)
	public static final int FRIEND_REWARD_TIME = ++_END;

	@Comment(content = "神秘商店已经刷新的次数")
	@Type(Integer.class)
	public static final int SPECIAL_SHOP_REFRESH_TIME = ++_END;

	@Comment(content = "使用vip体验卡的VIP等级")
	@Type(Integer.class)
	public static final int VIP_LEVEL_TEMPORARY = ++_END;

	@Comment(content = "剩余天赋点")
	@Type(Integer.class)
	public static final int GIFT_POINT_REMAIN = ++_END;

	@Comment(content = "天赋点总数")
	@Type(Integer.class)
	public static final int GIFT_POINT_TOTAL = ++_END;

	@Comment(content = "洗炼免费次数")
	@Type(Integer.class)
	public static final int FORGE_TIMES = ++_END;

	@Comment(content = "试练塔刷新次数")
	@Type(Integer.class)
	public static final int REFINE_TIMES = ++_END;

	@Comment(content = "试练塔魔晶刷新次数")
	@Type(Integer.class)
	public static final int CRYSTAL_REFINE_TIMES = ++_END;

	@Comment(content = "培养的武力点数")
	@Type(Integer.class)
	public static final int FOSTER_FORCE = ++_END;

	@Comment(content = "培养的敏捷点数")
	@Type(Integer.class)
	public static final int FOSTER_AGILE = ++_END;

	@Comment(content = "培养的体力点数")
	@Type(Integer.class)
	public static final int FOSTER_STAMINA = ++_END;

	@Comment(content = "培养的智力点数")
	@Type(Integer.class)
	public static final int FOSTER_INTELLIGENCE = ++_END;

	@Comment(content = "培养的精神点数")
	@Type(Integer.class)
	public static final int FOSTER_SPIRIT = ++_END;

	@Comment(content = "玩家封测礼包的领取状态")
	@Type(Integer.class)
	public static final int BETA_REWARD_STATE = ++_END;

	@Comment(content = "勇者之心数量")
	@Type(Integer.class)
	public static final int WARRIOR_HEART_NUM = ++_END;

	@Comment(content = "大转盘每日魔晶抽奖次数")
	@Type(Integer.class)
	public static final int TURNTABLE_USE_CRYSTAL_TIME = ++_END;

	@Comment(content = "战斗引导是否已经结束(大于0就结束了)")
	@Type(Integer.class)
	public static final int FINISHED_BATTLE_GUIDE = ++_END;

	@Comment(content = "接受勇者挑战获得奖励次数")
	@Type(Integer.class)
	public static final int ACCEPT_WARRIOR_CHALLENGE_REWARD_TIMES = ++_END;

	@Comment(content = "接受勇者挑战获得奖励最大次数")
	@Type(Integer.class)
	public static final int ACCEPT_WARRIOR_CHALLENGE_MAX_REWARD_TIMES = ++_END;

	@Comment(content = "正常充值次数")
	@Type(Integer.class)
	public static final int NORMAL_RECHARGE_TIMES = ++_END;

	@Comment(content = "黄钻等级")
	@Type(Integer.class)
	public static final int YELLOW_VIP_LEVEL = ++_END;

	@Comment(content = "是否为年费黄钻")
	@Type(Integer.class)
	public static final int IS_YEAR_YELLOW_VIP = ++_END;

	@Comment(content = "是否为豪华黄钻用户")
	@Type(Integer.class)
	public static final int IS_YELLOW_HIGH_VIP = ++_END;
	@Comment(content = "角斗场已购买次数")
	@Type(Integer.class)
	public static final int ABATTOIR_BUY_NUM = ++_END;
	@Comment(content = "角斗场剩余次数")
	@Type(Integer.class)
	public static final int ABATTOIR_REMAIN_NUM = ++_END;
	@Comment(content = "嗜血神殿已购买次数")
	@Type(Integer.class)
	public static final int BLOOD_TEMPLE_BUY_NUM = ++_END;
	@Comment(content = "嗜血神殿剩余次数")
	@Type(Integer.class)
	public static final int BLOOD_TEMPLE_REMAIN_NUM = ++_END;
	@Comment(content = "精魂-绿")
	@Type(Integer.class)
	public static final int SPRITE_SOUL_GREEN = ++_END;
	@Comment(content = "精魂-蓝")
	@Type(Integer.class)
	public static final int SPRITE_SOUL_BLUE = ++_END;
	@Comment(content = "精魂-紫")
	@Type(Integer.class)
	public static final int SPRITE_SOUL_PURPLE = ++_END;
	@Comment(content = "精魂-橙")
	@Type(Integer.class)
	public static final int SPRITE_SOUL_ORANGE = ++_END;
	@Comment(content = "精魂-红")
	@Type(Integer.class)
	public static final int SPRITE_SOUL_RED = ++_END;
	@Comment(content = "灵气值")
	@Type(Integer.class)
	public static final int AURA = ++_END;
	@Comment(content = "当前精灵背包格子数")
	@Type(Integer.class)
	public static final int SPRITE_BAG_CELL_SIZE = ++_END;
	@Comment(content = "星魂")
	@Type(Integer.class)
	public static final int STAR_SOUL = ++_END;
	@Comment(content = "当前星图id")
	@Type(Integer.class)
	public static final int STAR_MAP_ID = ++_END;
	@Comment(content = "首充状态")
	@Type(Integer.class)
	public static final int FIRST_RECHARGE_STATE = ++_END;
	@Comment(content = "一周累计充值")
	@Type(Integer.class)
	public static final int WEEK_TOTAL_RECHARGE_NUM = ++_END;
	@Comment(content = "职业技能系")
	@Type(Integer.class)
	public static final int SKILL_DEVELOP_TYPE = ++_END;
	@Comment(content = "威望")
	@Type(Integer.class)
	public static final int PRESTIGE = ++_END;
	@Comment(content = "战神之巅今日杀戮值")
	@Type(Integer.class)
	public static final int MARS_TODAY_KILL_VALUE = ++_END;
	@Comment(content = "战神之巅今日击杀获得金币")
	@Type(Integer.class)
	public static final int MARS_TODAY_KILL_COIN = ++_END;
	@Comment(content = "战神之巅剩余挑战次数")
	@Type(Integer.class)
	public static final int MARS_REMAIN_KILL_NUM = ++_END;
	@Comment(content = "战神之巅剩余加倍次数")
	@Type(Integer.class)
	public static final int MARS_REMAIN_MULTIPLE_NUM = ++_END;
	@Comment(content = "战神之巅已购买加倍次数")
	@Type(Integer.class)
	public static final int MARS_BUY_MULTIPLE_NUM = ++_END;
	@Comment(content = "战神之巅奖励金币")
	@Type(Integer.class)
	public static final int MARS_REWARD_COIN = ++_END;
	@Comment(content = "战神之巅奖励威望")
	@Type(Integer.class)
	public static final int MARS_REWARD_PRESTIGE = ++_END;
	@Comment(content = "战神之巅领奖状态")
	@Type(Integer.class)
	public static final int MARS_REWARD_STATE = ++_END;
	
	@Comment(content = "培养币")
	@Type(Integer.class)
	public static final int TRAIN_COIN = ++_END;
	
	@Comment(content = "剩余税收押注次数")
	@Type(Integer.class)
	public static final int LEVY_BET_REMAIN_TIMES = ++_END;
	
	@Comment(content = "已使用税收押注必胜次数")
	@Type(Integer.class)
	public static final int LEVY_CERTAIN_WIN_USED_TIMES = ++_END;
	
	@Comment(content = "税收加成比例")
	@Type(Integer.class)
	public static final int LEVY_EXTRA_RATE = ++_END;
	
	@Comment(content = "剩余押运次数")
	@Type(Integer.class)
	public static final int ESCORT_REMAIN_TIMES = ++_END;
	
	@Comment(content = "押运剩余拦截次数")
	@Type(Integer.class)
	public static final int ESCORT_REMAIN_ROB_TIMES = ++_END;
	
	@Comment(content = "押运已购买拦截次数")
	@Type(Integer.class)
	public static final int ESCORT_BUY_ROB_TIMES = ++_END;
	
	@Comment(content = "押运剩余协助次数")
	@Type(Integer.class)
	public static final int ESCORT_REMAIN_HELP_TIMES = ++_END;
	
	@Comment(content = "押运已刷新怪物次数")
	@Type(Integer.class)
	public static final int ESCORT_REFRESH_MONSTER_TIMES = ++_END;
	
	@Comment(content = "押运怪物类型 - 废弃")
	@Type(Integer.class)
	public static final int ESCORT_MONSTER_TYPE = ++_END;
	
	@Comment(content = "军团冥想状态")
	@Type(Integer.class)
	public static final int LEGION_MEDITATION_STATE = ++_END;
	
	@Comment(content = "军团头衔ID")
	@Type(Integer.class)
	public static final int LEGION_TITLE_ID = ++_END;
	
	@Comment(content = "军团任务主题类型")
	@Type(Integer.class)
	public static final int LEGION_TASK_THEME_TYPE = ++_END;
	
	@Comment(content = "军团接受任务数")
	@Type(Integer.class)
	public static final int LEGION_RECEIVED_TASK_NUM = ++_END;
	
	@Comment(content = "军团任务积分排行奖励领取状态")
	@Type(Integer.class)
	public static final int LEGION_TASK_RANK_REWARD_STATE = ++_END;
	
	@Comment(content = "军团头衔状态")
	@Type(Integer.class)
	public static final int LEGION_TITLE_STATE = ++_END;
	
	@Comment(content = "当前激活的预见ID")
	@Type(Integer.class)
	public static final int CURRENT_PREDICT_ID = ++_END;
	
	@Comment(content = "主城怪物剩余个数")
	@Type(Integer.class)
	public static final int MAIN_CITY_MONSTER_REMAIN_NUM = ++_END;
	
	@Comment(content = "主城怪物等级ID")
	@Type(Integer.class)
	public static final int MAIN_CITY_MONSTER_LEVEL_ID = ++_END;
	
	@Comment(content = "日常任务接受次数")
	@Type(Integer.class)
	public static final int DAILY_QUEST_RECEIVED_NUM = ++_END;
	
	@Comment(content = "日常任务购买次数")
	@Type(Integer.class)
	public static final int DAILY_QUEST_BUY_NUM = ++_END;
	
	@Comment(content = "精灵对酒已用必胜次数")
	@Type(Integer.class)
	public static final int SPRITE_PUB_WIN_USED_NUM = ++_END;
	
	@Comment(content = "精灵酒馆对酒类型")
	@Type(Integer.class)
	public static final int SPRITE_PUB_GUESS_TYPE = ++_END;
	
	@Comment(content = "当日体力手动恢复次数")
	@Type(Integer.class)
	public static final int DAY_RECOVER_ENERGY_NUM = ++_END;
	
	@Comment(content = "战神之巅接受挑战奖励次数")
	@Type(Integer.class)
	public static final int MARS_ACCEPT_REWARD_NUM = ++_END;
	
	@Comment(content = "累计体力手动恢复次数")
	@Type(Integer.class)
	public static final int TOTAL_RECOVER_ENERGY_NUM = ++_END;
	
	/** 属性的个数 */
	public static final int SIZE = _END - _BEGIN + 1;

	public static final int TYPE = PropertyType.HUMAN_INT_PROPERTY;

	public HumanIntProperty() {
		super(Integer.class, SIZE, PropertyType.HUMAN_INT_PROPERTY);
	}

	@Override
	public int getPropertyType() {
		return PropertyType.HUMAN_INT_PROPERTY;
	}

}
