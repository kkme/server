package com.hifun.soul.gameserver.skill;

import java.util.HashMap;
import java.util.Map;

import com.hifun.soul.core.annotation.AutoCreateClientEnumType;
import com.hifun.soul.core.annotation.ClientEnumComment;
import com.hifun.soul.core.enums.IndexedEnum;

/**
 * 攻击类型;
 * @author crazyjohn
 *
 */
@AutoCreateClientEnumType
public enum AttackType implements IndexedEnum{
	@ClientEnumComment(comment="近身")
	CLOSE(1),
	@ClientEnumComment(comment="远程")
	LONG(2);
	
	private int type;
	private static Map<Integer, AttackType> types = new HashMap<Integer, AttackType>();
	
	static {
		for (AttackType attack : AttackType.values()) {
			types.put(attack.getIndex(), attack);
		}
	}
	
	
	AttackType(int type) {
		this.type = type;
	}
	
	public static AttackType typeOf(int type) {
		return types.get(type);
	}

	@Override
	public int getIndex() {
		return type;
	}
}
