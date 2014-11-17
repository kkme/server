package com.hifun.soul.gameserver.stage;

import java.util.List;

import com.hifun.soul.core.enums.IndexedEnum;
import com.hifun.soul.core.util.EnumUtil;

/**
 * 头像显示位置;
 * @author magicstone
 */
public enum PositionType implements IndexedEnum {
	/** 左 */
	LEFT(1),
	/** 右 */
	RIGHT(2);
	
	private static final List<PositionType> indexes = IndexedEnumUtil.toIndexes(PositionType.values());
	
	private int index;

	PositionType(int index) {
		this.index = index;
	}

	@Override
	public int getIndex() {
		return index;
	}
	
	public static PositionType indexOf(int index){
		return EnumUtil.valueOf(indexes,index);				
	}
}
