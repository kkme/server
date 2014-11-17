package com.hifun.soul.gameserver.shop;

import com.hifun.soul.core.annotation.ClientEnumComment;

public enum SpecialShopRefreshType {
	/** 金币 */
	@ClientEnumComment(comment = "金币")
	COIN(1),
	/** 魔晶 */
	@ClientEnumComment(comment = "魔晶")
	CRYSTAL(2);
	
	private int type;

	SpecialShopRefreshType(int type) {
		this.type = type;
	}

	public int getType() {
		return type;
	}
	
}
