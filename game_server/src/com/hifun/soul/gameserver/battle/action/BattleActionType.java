package com.hifun.soul.gameserver.battle.action;

import com.hifun.soul.core.enums.IndexedEnum;

/**
 * 战斗行动类型;
 * 
 * @author crazyjohn
 * 
 */
public enum BattleActionType implements IndexedEnum {
	/** 普通攻击 */
	NORMAL_ATTACK(0),
	/** 技能攻击 */
	SKILL_ATTACK(1);

	private int type;

	BattleActionType(int type) {
		this.type = type;
	}

	@Override
	public int getIndex() {
		return type;
	}
}
