package com.hifun.soul.gameserver.battle.gem.erase;

import java.util.HashMap;
import java.util.Map;

import com.hifun.soul.core.enums.IndexedEnum;

/**
 * 消除位置类型;
 * 
 * @author crazyjohn
 * 
 */
public enum ErasePositionType implements IndexedEnum {
	/** 随机位置 */
	RANDOM(1),
	/** 指定位置 */
	ASSIGN(2),
	;

	private int type;
	private static Map<Integer, ErasePositionType> types = new HashMap<Integer, ErasePositionType>();

	static {
		for (ErasePositionType each : ErasePositionType.values()) {
			types.put(each.getIndex(), each);
		}
	}

	ErasePositionType(int type) {
		this.type = type;
	}

	@Override
	public int getIndex() {
		return type;
	}

	public static ErasePositionType typeOf(int positionType) {
		return types.get(positionType);
	}
}
