package com.hifun.soul.gameserver.meditation;

import java.util.List;

import com.hifun.soul.core.enums.IndexedEnum;
import com.hifun.soul.core.util.EnumUtil;

public enum MeditationModeType implements IndexedEnum{
	/** 普通冥想 */
	MODE_ONE(0),
	/** 加速冥想 */
	MODE_TWO(1),
	/** 急速冥想 */
	MODE_THREE(2),
	/** 极限冥想 */
	MODE_FOUR(3),
	;
	private int index;
	
	private static final List<MeditationModeType> indexes = IndexedEnumUtil.toIndexes(MeditationModeType.values());
	
	private MeditationModeType(int index) {
		this.index = index;
	}
	
	public int getIndex() {
		return index;
	}
	
	public static MeditationModeType indexOf(int index){
		return EnumUtil.valueOf(indexes, index);
	}
}
