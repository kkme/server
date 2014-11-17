package com.hifun.soul.gameserver.skill.slot;

import java.util.HashMap;
import java.util.Map;

import com.hifun.soul.core.annotation.AutoCreateClientEnumType;
import com.hifun.soul.core.annotation.ClientEnumComment;
import com.hifun.soul.core.enums.IndexedEnum;

/**
 * 玩家技能栏位状态;
 * 
 * @author crazyjohn
 * 
 */
@AutoCreateClientEnumType
public enum SkillSlotState implements IndexedEnum {
	/** 栏位未开启 */
	@ClientEnumComment(comment="栏位未开启")
	UN_OPEN(0),
	/** 栏位可以开启 */
	@ClientEnumComment(comment="栏位可以开启")
	CAN_OPEN(1),
	/** 栏位已开启 */
	@ClientEnumComment(comment="栏位已开启")
	OPEN(2);

	private int type;
	private static Map<Integer, SkillSlotState> types = new HashMap<Integer, SkillSlotState>();

	static {
		for (SkillSlotState state : SkillSlotState.values()) {
			types.put(state.getIndex(), state);
		}
	}

	SkillSlotState(int type) {
		this.type = type;
	}

	@Override
	public int getIndex() {
		return type;
	}

	public static SkillSlotState typeOf(int state) {
		return types.get(state);
	}

}
