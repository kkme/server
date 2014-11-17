package com.hifun.soul.gameserver.autobattle;

import java.util.List;

import com.hifun.soul.core.enums.IndexedEnum;
import com.hifun.soul.core.util.EnumUtil;

public enum AutoBattleStateType implements IndexedEnum{
	/** 尚未扫荡 */
	INIT(1),
	/** 扫荡进行中 */
	RUNNING(2),
	;
	private int index;
	
	private static final List<AutoBattleStateType> indexes = IndexedEnumUtil.toIndexes(AutoBattleStateType.values());
	
	private AutoBattleStateType(int index) {
		this.index = index;
	}
	
	public int getIndex() {
		return index;
	}
	
	public static AutoBattleStateType indexOf(int index){
		return EnumUtil.valueOf(indexes, index);
	}
}
