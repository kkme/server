package com.hifun.soul.gameserver.meditation;

import java.util.List;

import com.hifun.soul.core.enums.IndexedEnum;
import com.hifun.soul.core.util.EnumUtil;

public enum MeditationTimeModeType implements IndexedEnum{
	/** 时长1 */
	TIME_MODE_ONE(0),
	/** 时长2 */
	TIME_MODE_TWO(1),
	/** 时长3 */
	TIME_MODE_THREE(2),
	/** 时长4 */
	TIME_MODE_FOUR(3),
	;
	private int index;
	
	private static final List<MeditationTimeModeType> indexes = IndexedEnumUtil.toIndexes(MeditationTimeModeType.values());
	
	private MeditationTimeModeType(int index) {
		this.index = index;
	}
	
	public int getIndex() {
		return index;
	}
	
	public static MeditationTimeModeType indexOf(int index){
		return EnumUtil.valueOf(indexes, index);
	}
}
