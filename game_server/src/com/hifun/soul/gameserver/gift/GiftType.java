package com.hifun.soul.gameserver.gift;

import com.hifun.soul.core.annotation.AutoCreateClientEnumType;
import com.hifun.soul.core.annotation.ClientEnumComment;
import com.hifun.soul.core.enums.IndexedEnum;

@AutoCreateClientEnumType
public enum GiftType implements IndexedEnum{	
	@ClientEnumComment(comment="武力系")
	ATTACT_GIFT(1),
	@ClientEnumComment(comment="防御系")
	DEFENCE_GIFT(2),
	@ClientEnumComment(comment="续航系")
	ENDURANCE_GIFT(3)
	;
	private int index;
	private GiftType(int index){
		this.index = index;
	}
	public int getIndex(){
		return index;
	}
}
