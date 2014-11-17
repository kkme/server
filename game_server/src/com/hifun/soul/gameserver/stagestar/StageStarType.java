package com.hifun.soul.gameserver.stagestar;

import com.hifun.soul.core.enums.IndexedEnum;

public enum StageStarType implements IndexedEnum{
	/** 默认星级 */
	DefaultStar(0),
	/** 关卡评级一星 */
	StageStarOne(1),
	/** 关卡评级二星 */
	StageStarTwo(2),
	/** 关卡评级三星 */
	StageStarThree(3),
	/** 关卡评级四星 */
	StageStarFour(4),
	/** 关卡评级五星 */
	StageStarFive(5),
	;

	private int index;
	
	private StageStarType(int index) {
		this.index = index;
	}
	
	@Override
	public int getIndex() {
		return index;
	}
}
