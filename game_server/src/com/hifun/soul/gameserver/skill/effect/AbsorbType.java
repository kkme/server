package com.hifun.soul.gameserver.skill.effect;

import java.util.HashMap;
import java.util.Map;

import com.hifun.soul.core.enums.IndexedEnum;

/**
 * 能量是否会被吸收;
 * 
 * @author crazyjohn
 * 
 */
public enum AbsorbType implements IndexedEnum {
	/** 吸收 */
	YES(1),
	/** 不吸收 */
	NO(2);
	private int type;
	private static Map<Integer, AbsorbType> types = new HashMap<Integer, AbsorbType>();
	
	static {
		for (AbsorbType eachType : AbsorbType.values()) {
			types.put(eachType.getIndex(), eachType);
		}
	}
	AbsorbType(int type) {
		this.type = type;
	}

	@Override
	public int getIndex() {
		return type;
	}

	public static AbsorbType typeOf(int absorb) {
		return types.get(absorb);
	}
}
