package com.hifun.soul.gameserver.skill.buff;

import java.util.HashMap;
import java.util.Map;

import com.hifun.soul.core.annotation.AutoCreateClientEnumType;
import com.hifun.soul.core.annotation.ClientEnumComment;
import com.hifun.soul.core.enums.IndexedEnum;

/**
 * buff的自身类型;
 * 
 * @author crazyjohn
 * 
 */
@AutoCreateClientEnumType
public enum BuffSelfType implements IndexedEnum {
	/** BUFF */
	@ClientEnumComment(comment = "BUFF")
	BUFF(1),
	/** DEBUFF */
	@ClientEnumComment(comment = "DEBUFF")
	DEBUFF(2);

	private int type;
	private static Map<Integer, BuffSelfType> types = new HashMap<Integer, BuffSelfType>();

	static {
		for (BuffSelfType type : BuffSelfType.values()) {
			types.put(type.getIndex(), type);
		}
	}

	BuffSelfType(int type) {
		this.type = type;
	}

	@Override
	public int getIndex() {
		return type;
	}

	public static BuffSelfType typeOf(int buffSelfType) {
		return types.get(buffSelfType);
	}

}
