package com.hifun.soul.gameserver.stage;

import com.hifun.soul.core.enums.IndexedEnum;

/**
 * 关卡地图;
 * @author magicstone
 */
public enum StageMapType implements IndexedEnum {
	/** 森林 */
	FOREST(1),
	/** 沙漠 */
	DESERT(2),
	/** 墓地 */
	GRAVEYARD(3),
	/** 雪山 */
	SNOW(4)	,
	/** 火山 */
	VOLCANO(5);
	
	private int type;

	StageMapType(int type) {
		this.type = type;
	}

	@Override
	public int getIndex() {
		return type;
	}
	
	public static int getLastMapId() {
		return SNOW.getIndex();
	}
}
