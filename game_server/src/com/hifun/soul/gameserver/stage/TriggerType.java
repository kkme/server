package com.hifun.soul.gameserver.stage;

import java.util.List;

import com.hifun.soul.core.enums.IndexedEnum;
import com.hifun.soul.core.util.EnumUtil;

/**
 * 剧情触发点;
 * @author magicstone
 */
public enum TriggerType implements IndexedEnum {
	/** 关卡前 */
	STAGE_BEFORE(1),
	/** 关卡战斗成功 */
	STAGE_SUCCESS(2),
	/** 关卡战斗失败 */
	STAGE_FAIL(3);
	
	private static final List<TriggerType> indexes = IndexedEnumUtil.toIndexes(TriggerType.values());
	
	private int index;

	TriggerType(int index) {
		this.index = index;
	}

	@Override
	public int getIndex() {
		return index;
	}
	
	public static TriggerType indexOf(int index){
		return EnumUtil.valueOf(indexes,index);				
	}
}
