package com.hifun.soul.gameserver.role;

import java.util.HashMap;
import java.util.Map;

import com.hifun.soul.core.annotation.AutoCreateClientEnumType;
import com.hifun.soul.core.annotation.ClientEnumComment;
import com.hifun.soul.core.enums.IndexedEnum;

/**
 * 角色类型枚举;
 * 
 * @author crazyjohn
 * 
 */
@AutoCreateClientEnumType
public enum RoleType implements IndexedEnum {
	/** 玩家 */
	@ClientEnumComment(comment="玩家")
	HUMAN(1),
	/** 怪 */
	@ClientEnumComment(comment="怪")
	MONSTER(2);
	private int type;
	private static Map<Integer, RoleType> types = new HashMap<Integer, RoleType>();

	static {
		for (RoleType each : RoleType.values()) {
			types.put(each.getIndex(), each);
		}
	}

	RoleType(int type) {
		this.type = type;
	}

	@Override
	public int getIndex() {
		return type;
	}

	public static RoleType typeOf(int type) {
		return types.get(type);
	}
}
