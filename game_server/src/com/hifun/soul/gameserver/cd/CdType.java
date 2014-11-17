package com.hifun.soul.gameserver.cd;

import java.util.List;

import com.hifun.soul.core.annotation.AutoCreateClientEnumType;
import com.hifun.soul.core.annotation.ClientEnumComment;
import com.hifun.soul.core.enums.IndexedEnum;
import com.hifun.soul.core.util.EnumUtil;

@AutoCreateClientEnumType
public enum CdType  implements IndexedEnum {
	/** 建筑 */
	@ClientEnumComment(comment="")
	BUILDING(1),
	/** 征税 */
	@ClientEnumComment(comment="征税")
	LEVY(2),
	/** 收获矿石 */
	@ClientEnumComment(comment="收获矿石")
	HARVEST_GEM(3),
	/** 训练 */
	@ClientEnumComment(comment="训练 ")
	TRAINING(4),
	/** 问答 */
	@ClientEnumComment(comment="问答 ")
	ANSWER_QUESTION(5),
	/** 竞技场战斗cd */
	@ClientEnumComment(comment="竞技场战斗cd ")
	ARENA_BATTLE(6),
	/** boss战充能cd */
	@ClientEnumComment(comment="boss战充能cd ")
	CHARGED_STRIKE_CD(7),
	/** 冥想cd */
	@ClientEnumComment(comment="冥想cd ")
	MEDITATION(8),
	/** 矿场1cd */
	@ClientEnumComment(comment="矿场1cd ")
	MINE_1(9),
	/** 矿场2cd */
	@ClientEnumComment(comment="矿场2cd ")
	MINE_2(10),
	/** 矿场3cd */
	@ClientEnumComment(comment="矿场3cd ")
	MINE_3(11),
	/** 矿场4cd */
	@ClientEnumComment(comment="矿场4cd ")
	MINE_4(12),
	/** 矿场5cd */
	@ClientEnumComment(comment="矿场5cd ")
	MINE_5(13),
	/** 矿场6cd */
	@ClientEnumComment(comment="矿场6cd ")
	MINE_6(14),
	/** 矿场7cd */
	@ClientEnumComment(comment="矿场7cd ")
	MINE_7(15),
	/** 矿场8cd */
	@ClientEnumComment(comment="矿场8cd ")
	MINE_8(16),
	/** 矿场9cd */
	@ClientEnumComment(comment="矿场9cd ")
	MINE_9(17),
	/** 下一次boss战开启cd */
	@ClientEnumComment(comment="下一次boss战开启cd")
	BOSS_WAR_START(18),
	/** 扫荡的cd */
	@ClientEnumComment(comment="扫荡的cd")
	AUTO_BATTLE(19),
	/** boss战攻击 */
	@ClientEnumComment(comment="boss战攻击")
	BOSS_ATTACK(20),
	/** VIP体验cd */
	@ClientEnumComment(comment="VIP体验cd")
	VIP_TEMP(21),
	/** VIP体验cd */
	@ClientEnumComment(comment="匹配战战斗cd")
	MATCH_BATTLE_CD(22),
	/** 勇者之路任务cd */
	@ClientEnumComment(comment="勇者之路任务cd")
	WARRIOR_QUEST_CD(23),
	/** 勇者之路刷新对手cd */
	@ClientEnumComment(comment="勇者之路刷新对手cd")
	WARRIOR_REFRESH_OPPONENT_CD(24),
	/** 军团boss战充能cd */
	@ClientEnumComment(comment="军团boss战充能cd ")
	LEGION_BOSS_CHARGED(25),
	/** 下一次军团boss战开启cd */
	@ClientEnumComment(comment="下一次军团boss战开启cd")
	LEGION_BOSS_START(26),
	/** 军团boss战攻击 */
	@ClientEnumComment(comment="军团boss战攻击")
	LEGION_BOSS_ATTACK(27),
	@ClientEnumComment(comment="军团矿战收获")
	LEGION_MINE_WAR_HARVEST(28),
	@ClientEnumComment(comment="军团矿战移动")
	LEGION_MINE_WAR_MOVE(29),
	@ClientEnumComment(comment="军团矿战战斗")
	LEGION_MINE_WAR_BATTLE(30),
	@ClientEnumComment(comment="押运")
	ESCORT(31),
	@ClientEnumComment(comment="押运拦截")
	ESCORT_ROB(32),
	@ClientEnumComment(comment="攻打城堡怪物")
	MAIN_CITY_BATTLE(33),
	@ClientEnumComment(comment="体力恢复")
	ENERGY_RECOVER(34)
	;
	

	private int index;
	
	private static final List<CdType> indexes = IndexedEnumUtil.toIndexes(CdType.values());
	
	private CdType(int index){
		this.index = index;
	}
	
	@Override
	public int getIndex() {
		return index;
	}
	
	/**
	 * 根据指定的索引获取枚举的定义
	 * 
	 * @param index
	 * @return
	 */
	public static CdType indexOf(final int index) {
		return EnumUtil.valueOf(indexes, index);
	}
}
