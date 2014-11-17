package com.hifun.soul.gameserver.stage;

import java.util.List;

import com.hifun.soul.core.enums.IndexedEnum;
import com.hifun.soul.core.util.EnumUtil;

/**
 * 剧情类型;
 * @author magicstone
 */
public enum DramaType implements IndexedEnum {
	/** 对话 */
	DUIHUA(1),
	/** 旁白 */
	PANGBAI(2);
	
	private static final List<DramaType> indexes = IndexedEnumUtil.toIndexes(DramaType.values());
	
	private int index;

	DramaType(int index) {
		this.index = index;
	}

	@Override
	public int getIndex() {
		return index;
	}
	
	public static DramaType indexOf(int index){
		return EnumUtil.valueOf(indexes,index);				
	}
}
