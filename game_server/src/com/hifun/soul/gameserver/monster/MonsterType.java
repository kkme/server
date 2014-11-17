package com.hifun.soul.gameserver.monster;

import com.hifun.soul.core.enums.IndexedEnum;

/**
 * 怪物类型;
 * 
 * @author crazyjohn
 * 
 */
public enum MonsterType implements IndexedEnum {
	/** 普通 */
	COMMON(1),
	/** 精英 */
	ELITE(2),
	/** Boss */
	BOSS(3), 
	/** 试炼 */
	REFINE(4),
	/** 城堡盗贼 */
	MAIN_CITY(5),
	/** NPC */
	BRAVE_NPC(6);

	private int type;

	MonsterType(int type) {
		this.type = type;
	}

	@Override
	public int getIndex() {
		return type;
	}

}
