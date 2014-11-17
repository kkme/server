package com.hifun.soul.gameserver.role;

import java.util.HashMap;
import java.util.Map;

import com.hifun.soul.core.enums.IndexedEnum;

/**
 * 
 * 角色类型
 * 
 * @author magicstone
 * 
 */
public enum Occupation implements IndexedEnum {
	/** 战士 */
	WARRIOR((short) 1),
	/** 游侠 */
	RANGER((short) 2),
	/** 法师 */
	MAGICIAN((short) 4);
	/** 注意：添加新的职业时，type值应设置为2的幂次方形式，便于按位操作 */
	private short type;
	private static Map<Integer, Occupation> types = new HashMap<Integer, Occupation>();

	static {
		for (Occupation each : Occupation.values()) {
			types.put(each.getIndex(), each);
		}
	}

	Occupation(short type) {
		this.type = type;
	}

	public short getType() {
		return type;
	}

	@Override
	public int getIndex() {
		return this.getType();
	}

	public static Occupation typeOf(int type) {
		return types.get(type);
	}
}
