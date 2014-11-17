package com.hifun.soul.gameserver.gift;

import com.hifun.soul.core.annotation.AutoCreateClientEnumType;
import com.hifun.soul.core.annotation.ClientEnumComment;
import com.hifun.soul.core.enums.IndexedEnum;

/**
 * 天赋激活状态
 * @author magicstone
 *
 */
@AutoCreateClientEnumType
public enum GiftActiveState implements IndexedEnum {
	@ClientEnumComment(comment="未解锁")
	LOCKED(0),
	@ClientEnumComment(comment="未激活")
	UNACTIVE(1),
	@ClientEnumComment(comment="已激活")
	ACTIVE(2);
	private int index;
	private GiftActiveState(int index){
		this.index = index;
	}
	public int getIndex(){
		return index;
	}
}
