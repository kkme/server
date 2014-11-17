package com.hifun.soul.gameserver.stage;

import java.util.List;

import com.hifun.soul.core.enums.IndexedEnum;
import com.hifun.soul.core.util.EnumUtil;

/**
 * 关卡评星奖励领取状态;
 * @author magicstone
 */
public enum StageStarRewardState implements IndexedEnum {
	/** 不能领取 */
	NOGET(1),
	/** 可以领取 */
	CANGET(2),
	/** 已经领取 */
	GETTED(3);
	
	private static final List<StageStarRewardState> indexes = IndexedEnumUtil.toIndexes(StageStarRewardState.values());
	private int type;
	StageStarRewardState(int type) {
		this.type = type;
	}

	@Override
	public int getIndex() {
		return type;
	}
	
	public static StageStarRewardState indexOf(int index){
		return EnumUtil.valueOf(indexes,index);				
	}
}
