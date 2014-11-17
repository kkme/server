package com.hifun.soul.gameserver.role.properties;

import com.hifun.soul.core.annotation.Type;

/**
 * 玩家特有的长整形属性集合;
 * 
 * @author crazyjohn
 * 
 */
public class HumanLongProperty extends LongPropertyCacheSet {
	/** 基础整型属性索引开始值 */
	public static int _BEGIN = 0;

	/** 基础整型属性索引结束值 */
	public static int _END = _BEGIN;

	/** 最后聊天时间 */
	@Type(Long.class)
	public static final int LAST_CHAT_TIME = _END++;
	
	/** 大转盘抽奖次数重置时间 */
	@Type(Long.class)
	public static final int LAST_TURNTABLE_RESET_TIME = _END++;
	
	/** 上次魔晶兑换次数重置时间 */
	@Type(Long.class)
	public static final int LAST_CRYSTAL_EXCHANGE_RESET_TIME = _END++;
	
	/** 上次训练相关数据重置时间 */
	@Type(Long.class)
	public static final int LAST_TRAINING_RESET_TIME = _END++;
	
	/** 最后一次登出时间 */
	@Type(Long.class)
	public static final int LAST_LOGOUT_TIME = _END++;
	
	/** 上次cd重置时间 */
	@Type(Long.class)
	public static final int LAST_CD_RESET_TIME = _END++;
	
	/** 上次好友奖励重置时间 */
	@Type(Long.class)
	public static final int LAST_FRIEND_REWARD_RESET_TIME = _END++;
	
	/** 上次收获宝石重置时间 */
	@Type(Long.class)
	public static final int LAST_HARVEST_GEM_RESET_TIME = _END++;
	
	/** 上次税收重置时间 */
	@Type(Long.class)
	public static final int LAST_LEVY_RESET_TIME = _END++;
	
	/** 上次登陆天数重置时间*/
	@Type(Long.class)
	public static final int LAST_LOGINREWARD_RESET_TIME = _END++;
	
	/** 上次获取在线奖励的时间*/
	@Type(Long.class)
	public static final int ONLINT_REWARD_GET_TIME = _END++;
	/** 上次每周问答重置时间*/
	@Type(Long.class)
	public static final int LAST_DAILY_QUESTION_RESET_TIME = _END++;
	/** 上次每周问答重置时间*/
	@Type(Long.class)
	public static final int LAST_WEEKLY_QUESTION_RESET_TIME = _END++;
	
	/** 上次恢复体力时间*/
	@Type(Long.class)
	public static final int LAST_ENERGY_RECOVER_TIME = _END++;
	/** 上次重置购买体力次数的时间*/
	@Type(Long.class)
	public static final int LAST_RESET_BUY_ENERGY_TIME = _END++;
	
	/** 上次重置竞技场战斗次数的时间*/
	@Type(Long.class)
	public static final int LAST_ARENA_BATTLE_TIME_RESET_TIME = _END++;
	
	/** 上次重置竞技场排行奖励的时间*/
	@Type(Long.class)
	public static final int LAST_ARENA_RANK_REWARD_RESET_TIME = _END++;
	
	/** 上次重置剩余协助好友冥想次数的时间*/
	@Type(Long.class)
	public static final int LAST_ASSIST_MEDITATION_RESET_TIME = _END++;
	
	/** 上次重置竞技场战斗次数的时间*/
	@Type(Long.class)
	public static final int LAST_ARENA_BUY_TIME_RESET_TIME = _END++;
	
	/** 上次重置每日任务的时间*/
	@Type(Long.class)
	public static final int LAST_DAILY_QUEST_RESET_TIME = _END++;
	
	/** 上次重置免费占星次数的时间*/
	@Type(Long.class)
	public static final int LAST_GAMBLE_RESET_TIME = _END++;
	
	/** 上次重置矿场数据的时间*/
	@Type(Long.class)
	public static final int LAST_MINE_DATA_RESET_TIME = _END++;
	
	/** 上次刷新神秘商店的时间*/
	@Type(Long.class)
	public static final int LAST_REFRESH_SPECIAL_SHOP_TIME = _END++;
	
	/** 上次重置提交问题反馈次数的时间*/
	@Type(Long.class)
	public static final int LAST_RESET_SUBMIT_GM_QUESTION_NUM_TIME = _END++;
	
	/** 上次重置精英副本的时间*/
	@Type(Long.class)
	public static final int LAST_RESET_ELITE_STAGE_TIME = _END++;
	
	/** 上次重置发送邮件数的时间*/
	@Type(Long.class)
	public static final int LAST_RESET_SEND_MAIL_NUM_TIME = _END++;
	
	/** 登陆时间*/
	@Type(Long.class)
	public static final int LOGIN_TIME = _END++;
	
	/** 神秘商店购买次数刷新时间 */
	@Type(Long.class)
	public static final int LAST_RESET_SPECIAL_SHOP_BUY_TIME = _END++;
	
	/** 上次使用vip体验卡时间 */
	@Type(Long.class)
	public static final int LAST_USE_VIP_TEMP_ITEM_TIME = _END++;
	
	/** 上次重置免费洗炼的时间 */
	@Type(Long.class)
	public static final int LAST_RESET_FREE_EQUIP_FORGE = _END++;
	
	/** 上次重置免费试炼刷新次数的时间 */
	@Type(Long.class)
	public static final int LAST_RESET_REFINE_REFRESH_TIMES = _END++;
	
	/** 上次重置勇士之路数据的时间 */
	@Type(Long.class)
	public static final int LAST_RESET_WARRIOR_DATA_TIME = _END++;
	
	/** 上次重置黄钻每日数据的时间 */
	@Type(Long.class)
	public static final int LAST_RESET_YELLOW_VIP_DAILY_DATA_TIME = _END++;
	
	/** 上次领取军衔俸禄的时间 */
	@Type(Long.class)
	public static final int LAST_GET_TITLE_SALARY_TIME = _END++;
	
	/** 上次重置角斗场数据的时间 */
	@Type(Long.class)
	public static final int LAST_RESET_ABATTOIR_DATA_TIME = _END++;
	
	/** 上次重置嗜血神殿数据的时间 */
	@Type(Long.class)
	public static final int LAST_RESET_BLOOD_TEMPLE_DATA_TIME = _END++;
	
	/** 上次重置周累计充值数据的时间 */
	@Type(Long.class)
	public static final int LAST_RESET_WEEK_TOTAL_RECHARGE_DATA_TIME = _END++;
	
	/** 上次重置战神之巅数据的时间 */
	@Type(Long.class)
	public static final int LAST_RESET_MARS_DAILY_DATA_TIME = _END++;
	
	/** 上次重置押运数据的时间 */
	@Type(Long.class)
	public static final int LAST_RESET_ESCORT_DATA_TIME = _END++;
	
	/** 军团头衔结束时间 */
	@Type(Long.class)
	public static final int LEGION_TITLE_END_TIME = _END++;
	
	/** 上次重置角色军团每日数据时间 */
	@Type(Long.class)
	public static final int LAST_RESET_LEGION_DATA_TIME = _END++;
	
	/** 上次刷新角色军团任务时间 */
	@Type(Long.class)
	public static final int LAST_REFRESH_LEGION_TASK_TIME = _END++;
	
	/** 上次重置精灵酒馆数据任务时间 */
	@Type(Long.class)
	public static final int LAST_RESET_SPRITE_PUB_DATA_TIME = _END++;
	
	/** 上次重置恢复体力次数时间 */
	@Type(Long.class)
	public static final int LAST_RESET_RECOVER_ENERGY_TIME = _END++;
	
	/** 上次每日自动恢复体力值时间 */
	@Type(Long.class)
	public static final int LAST_DAY_RECOVER_ENERGY_TIME = _END++;
	
	/** 属性的个数 */
	public static final int SIZE = _END - _BEGIN + 1;

	public static final int TYPE = PropertyType.HUMAN_LONG_PROPERTY;

	public HumanLongProperty() {
		super(Long.class, SIZE, PropertyType.HUMAN_LONG_PROPERTY);
	}

	@Override
	public int getPropertyType() {
		return PropertyType.HUMAN_LONG_PROPERTY;
	}

}
