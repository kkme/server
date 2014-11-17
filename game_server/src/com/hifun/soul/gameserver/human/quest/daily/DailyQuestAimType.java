package com.hifun.soul.gameserver.human.quest.daily;

import java.util.HashMap;
import java.util.Map;

import com.hifun.soul.core.annotation.AutoCreateClientEnumType;
import com.hifun.soul.core.annotation.ClientEnumComment;
import com.hifun.soul.core.enums.IndexedEnum;
import com.hifun.soul.gameserver.human.quest.IQuestAimType;
import com.hifun.soul.gameserver.human.quest.aim.AimInfo;
import com.hifun.soul.gameserver.human.quest.aim.counter.AbstractDailyQuestAimCounter;

/**
 * 日常任务目标;<br>
 * <ul>
 * <li>1：好友切磋</li>
 * <li>2：主城收税</li>
 * <li>3：矿场精炼</li>
 * <li>4：战斗胜利</li>
 * <li>5：主角训练</li>
 * <li>6：装备洗练</li>
 * <li>7：装备强化</li>
 * <li>8：科技升级</li>
 * <li>9：刻印成功</li>
 * <li>10：抽奖转盘</li>
 * <li>11：奇遇答题</li>
 * <li>12: 试炼挑战</li>
 * <li>13: 城堡盗贼</li>
 * <li>14: 属性培养</li>
 * <li>15: 巫术套现</li>
 * <li>16: 天赋升级</li>
 * <li>17: 竞技场挑战</li>
 * <li>18: 酒馆对酒</li>
 * <li>19: 跑商达人</li>
 * <li>20: 主城猜大小</li>
 * <li>21: 战神之巅</li>
 * <li>22: 战俘营挑战</li>
 * <li>23: 角斗场挑战</li>
 * <li>24: 嗜血神殿挑战</li>
 * <li>25: 装备位升级</li>
 * <li>26: 点亮星图</li>
 * <li>27: 主城通灵</li>
 * <li>28: 精灵升级</li>
 * </ul>
 * 
 * 
 * @author crazyjohn
 * 
 */
@AutoCreateClientEnumType
public enum DailyQuestAimType implements IndexedEnum, IQuestAimType {
	/** 好友切磋 */
	@ClientEnumComment(comment = "好友切磋")
	DAILY_FRIEND_REWARD(1) {

		@Override
		public AbstractDailyQuestAimCounter createAimCounter(AimInfo aim,
				int index) {
			return new QuestAimMiniBattleCounter(DAILY_FRIEND_REWARD, aim,
					index);
		}

	},
	/** 主城收税 */
	@ClientEnumComment(comment = "主城收税")
	DAILY_REVENUE(2) {

		@Override
		public AbstractDailyQuestAimCounter createAimCounter(AimInfo aim,
				int index) {
			return new DailyRevenueAimCounter(DAILY_REVENUE, aim, index);
		}

	},
	/** 矿场精炼 */
	@ClientEnumComment(comment = "矿场精炼")
	DAILY_MINE(3) {

		@Override
		public AbstractDailyQuestAimCounter createAimCounter(AimInfo aim,
				int index) {
			return new DailyMineAimCounter(DAILY_MINE, aim, index);
		}

	},
	/** 战斗胜利 */
	@ClientEnumComment(comment = "战斗胜利")
	DAILY_BATTLE_WIN(4) {

		@Override
		public AbstractDailyQuestAimCounter createAimCounter(AimInfo aim,
				int index) {
			return new DailyBattleWinAimCounter(DAILY_BATTLE_WIN, aim, index);
		}

	},
	/** 主角训练 */
	@ClientEnumComment(comment = "主角训练")
	DAILY_TRAINING(5) {

		@Override
		public AbstractDailyQuestAimCounter createAimCounter(AimInfo aim,
				int index) {
			return new DailyTrainingAimCounter(DAILY_TRAINING, aim, index);
		}

	},
	/** 装备洗练 */
	@ClientEnumComment(comment = "装备洗练")
	DAILY_EQUIP_REFINE(6) {

		@Override
		public AbstractDailyQuestAimCounter createAimCounter(AimInfo aim,
				int index) {
			return new DailyEquipRefineAimCounter(DAILY_EQUIP_REFINE, aim,
					index);
		}

	},
	/** 装备强化 */
	@ClientEnumComment(comment = "装备强化")
	DAILY_EQUIP_UPGRADE(7) {

		@Override
		public AbstractDailyQuestAimCounter createAimCounter(AimInfo aim,
				int index) {
			return new DailyEquipUpgradeAimCounter(DAILY_EQUIP_UPGRADE, aim,
					index);
		}

	},
	/** 科技升级 */
	@ClientEnumComment(comment = "科技升级")
	DAILY_TECH_UPGRAGE(8) {

		@Override
		public AbstractDailyQuestAimCounter createAimCounter(AimInfo aim,
				int index) {
			return new DailyUpgradeTechAimCounter(DAILY_TECH_UPGRAGE, aim,
					index);
		}

	},
	/** 刻印成功 */
	@ClientEnumComment(comment = "刻印成功")
	DAILY_HOROSCOPE(9) {

		@Override
		public AbstractDailyQuestAimCounter createAimCounter(AimInfo aim,
				int index) {
			return new DailyHoroscopeAimCounter(DAILY_HOROSCOPE, aim, index);
		}

	},
	/** 抽奖转盘 */
	@ClientEnumComment(comment = "抽奖转盘")
	DAILY_LOTTERY(10) {

		@Override
		public AbstractDailyQuestAimCounter createAimCounter(AimInfo aim,
				int index) {
			return new DailyLotteryAimCounter(DAILY_LOTTERY, aim, index);
		}

	},
	/** 奇遇答题 */
	@ClientEnumComment(comment = "奇遇答题")
	DAILY_ANSWER(11) {

		@Override
		public AbstractDailyQuestAimCounter createAimCounter(AimInfo aim,
				int index) {
			return new DailyAnswerAimCounter(DAILY_ANSWER, aim, index);
		}

	},
	/** 试炼挑战 */
	@ClientEnumComment(comment = "试炼挑战")
	REFINE_CHALLENGE(12) {

		@Override
		public AbstractDailyQuestAimCounter createAimCounter(AimInfo aim,
				int index) {
			return new DailyRefineAimCounter(REFINE_CHALLENGE, aim, index);
		}

	},
	/** 城堡盗贼 */
	@ClientEnumComment(comment = "城堡盗贼")
	MAIN_CITY_MONSTER(13) {

		@Override
		public AbstractDailyQuestAimCounter createAimCounter(AimInfo aim,
				int index) {
			return new DailyMainCityMonsterAimCounter(MAIN_CITY_MONSTER, aim,
					index);
		}

	},
	/** 属性培养 */
	@ClientEnumComment(comment = "属性培养")
	PROPERTY_FOSTER(14) {

		@Override
		public AbstractDailyQuestAimCounter createAimCounter(AimInfo aim,
				int index) {
			return new DailyPropertyFosterAimCounter(PROPERTY_FOSTER, aim,
					index);
		}

	},
	/** 巫术套现 */
	@ClientEnumComment(comment = "巫术套现")
	CRYSTAL_EXCHANGE(15) {

		@Override
		public AbstractDailyQuestAimCounter createAimCounter(AimInfo aim,
				int index) {
			return new DailyCrystalExchangeAimCounter(CRYSTAL_EXCHANGE, aim,
					index);
		}

	},
	/** 天赋升级 */
	@ClientEnumComment(comment = "天赋升级")
	GIFT_UPGRADE(16) {

		@Override
		public AbstractDailyQuestAimCounter createAimCounter(AimInfo aim,
				int index) {
			return new DailyGiftUpgradeAimCounter(GIFT_UPGRADE, aim, index);
		}

	},
	/** 竞技场挑战 */
	@ClientEnumComment(comment = "竞技场挑战")
	ARENA_CHALLENGE(17) {

		@Override
		public AbstractDailyQuestAimCounter createAimCounter(AimInfo aim,
				int index) {
			return new DailyArenaChallengeAimCounter(ARENA_CHALLENGE, aim,
					index);
		}

	},
	/** 酒馆对酒 */
	@ClientEnumComment(comment = "酒馆对酒")
	SPRITE_PUB(18) {

		@Override
		public AbstractDailyQuestAimCounter createAimCounter(AimInfo aim,
				int index) {
			return new DailySpritePubCounter(SPRITE_PUB, aim, index);
		}

	},
	/** 跑商押运 */
	@ClientEnumComment(comment = "跑商押运")
	ESCORT(19) {

		@Override
		public AbstractDailyQuestAimCounter createAimCounter(AimInfo aim,
				int index) {
			return new DailyEscortAimCounter(ESCORT, aim, index);
		}

	},
	/** 主城猜大小 */
	@ClientEnumComment(comment = "主城猜大小")
	MAIN_CITY_BET(20) {

		@Override
		public AbstractDailyQuestAimCounter createAimCounter(AimInfo aim,
				int index) {
			return new DailyMainCityBetAimCounter(MAIN_CITY_BET, aim, index);
		}

	},
	/** 战神之巅击杀 */
	@ClientEnumComment(comment = "战神之巅击杀")
	MARS_KILL(21) {

		@Override
		public AbstractDailyQuestAimCounter createAimCounter(AimInfo aim,
				int index) {
			return new DailyMarsKillAimCounter(MARS_KILL, aim, index);
		}

	},
	/** 战俘营挑战 */
	@ClientEnumComment(comment = "战俘营挑战")
	PRISON_BATTLE(22) {

		@Override
		public AbstractDailyQuestAimCounter createAimCounter(AimInfo aim,
				int index) {
			return new DailyPrisonBattleAimCounter(PRISON_BATTLE, aim, index);
		}

	},
	/** 角斗场挑战 */
	@ClientEnumComment(comment = "角斗场挑战")
	ABATTOIR_CHALLENGE(23) {

		@Override
		public AbstractDailyQuestAimCounter createAimCounter(AimInfo aim,
				int index) {
			return new DailyAbattoirChallengeAimCounter(ABATTOIR_CHALLENGE,
					aim, index);
		}

	},
	/** 嗜血神殿挑战 */
	@ClientEnumComment(comment = "嗜血神殿挑战")
	BLOOD_TEMPLE_CHALLENGE(24) {

		@Override
		public AbstractDailyQuestAimCounter createAimCounter(AimInfo aim,
				int index) {
			return new DailyBloodTempleChallengeAimCounter(
					BLOOD_TEMPLE_CHALLENGE, aim, index);
		}

	},
	/** 装备位强化 */
	@ClientEnumComment(comment = "装备位强化")
	EQUIP_BIT_UPGRADE(25) {

		@Override
		public AbstractDailyQuestAimCounter createAimCounter(AimInfo aim,
				int index) {
			return new DailyEquipBitUpgradeAimCounter(EQUIP_BIT_UPGRADE, aim,
					index);
		}

	},
	/** 点亮星图 */
	@ClientEnumComment(comment = "点亮星图")
	STAR_MAP_ACTIVATE(26) {

		@Override
		public AbstractDailyQuestAimCounter createAimCounter(AimInfo aim,
				int index) {
			return new DailyStarMapActivateAimCounter(STAR_MAP_ACTIVATE, aim,
					index);
		}

	},
	/** 主城通灵 */
	@ClientEnumComment(comment = "主城通灵")
	MAIN_CITY_COLLECT_STONE(27) {

		@Override
		public AbstractDailyQuestAimCounter createAimCounter(AimInfo aim,
				int index) {
			return new DailyMainCityCollectStoneAimCounter(
					MAIN_CITY_COLLECT_STONE, aim, index);
		}

	},
	/** 精灵升级 */
	@ClientEnumComment(comment = "精灵升级")
	SPRITE_UPGRADE(28) {

		@Override
		public AbstractDailyQuestAimCounter createAimCounter(AimInfo aim,
				int index) {
			return new DailySpriteUpgradeAimCounter(SPRITE_UPGRADE, aim, index);
		}

	};

	private int index;
	private static Map<Integer, DailyQuestAimType> types = new HashMap<Integer, DailyQuestAimType>();

	public int getAimType() {
		return this.index;
	}

	static {
		for (DailyQuestAimType each : DailyQuestAimType.values()) {
			types.put(each.getIndex(), each);
		}
	}

	DailyQuestAimType(int index) {
		this.index = index;
	}

	@Override
	public int getIndex() {
		return index;
	}

	public abstract AbstractDailyQuestAimCounter createAimCounter(AimInfo aim,
			int index);

	public static DailyQuestAimType typeOf(int aimType) {
		return types.get(aimType);
	}
}
