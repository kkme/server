package com.hifun.soul.gameserver.skill;

import java.util.HashMap;
import java.util.Map;

import com.hifun.soul.core.enums.IndexedEnum;

/**
 * 第二效果是否可以被闪避类型;
 * 
 * @author crazyjohn
 * 
 */
public enum SecondEffectDodgeType implements IndexedEnum {
	/** 不可以被闪避 */
	NO(0),
	/** 可以被闪避 */
	YES(1);
	private int type;
	private static Map<Integer, SecondEffectDodgeType> types = new HashMap<Integer, SecondEffectDodgeType>();
	
	static {
		for (SecondEffectDodgeType each : SecondEffectDodgeType.values()) {
			types.put(each.getIndex(), each);
		}
	}

	SecondEffectDodgeType(int type) {
		this.type = type;
	}
	
	public static SecondEffectDodgeType typeOf(int dodgeType) {
		return types.get(dodgeType);
	}

	@Override
	public int getIndex() {
		return type;
	}
}
