package com.hifun.soul.gameserver.warrior;

import com.hifun.soul.core.annotation.AutoCreateClientEnumType;
import com.hifun.soul.core.annotation.ClientEnumComment;
import com.hifun.soul.core.enums.IndexedEnum;

/**
 * 勇者之路对手类型
 * @author magicstone
 *
 */
@AutoCreateClientEnumType
public enum WarriorOpponentType implements IndexedEnum {
	@ClientEnumComment(comment="好友")
	FRIEND(1),
	@ClientEnumComment(comment="陌生人")
	STRANGER(2),
	@ClientEnumComment(comment="NPC")
	NPC(3)
	;
	private int index;
	private WarriorOpponentType(int index){
		this.index = index;
	}
	@Override
	public int getIndex() {		
		return index;
	}

}
