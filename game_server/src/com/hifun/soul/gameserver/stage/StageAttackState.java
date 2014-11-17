package com.hifun.soul.gameserver.stage;

import java.util.List;

import com.hifun.soul.core.enums.IndexedEnum;
import com.hifun.soul.core.util.EnumUtil;

/**
 * 攻打关卡状态;
 * @author magicstone
 */
public enum StageAttackState implements IndexedEnum {
	/** 未攻打关卡 */
	UNATTACK(1),
	/** 攻打某个关卡未抽奖(这个状态可抽奖) */
	ATTACKED(2),
	/** 已经领完奖励了 */
	GOT_REWARD(3);
	
	private static final List<StageAttackState> indexes = IndexedEnumUtil.toIndexes(StageAttackState.values());

	private int type;

	StageAttackState(int type) {
		this.type = type;
	}

	@Override
	public int getIndex() {
		return type;
	}
	
	public static StageAttackState indexOf(int index){
		return EnumUtil.valueOf(indexes,index);				
	}
}
