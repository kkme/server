package com.hifun.soul.gameserver.matchbattle;

import com.hifun.soul.core.annotation.AutoCreateClientEnumType;
import com.hifun.soul.core.annotation.ClientEnumComment;
import com.hifun.soul.core.enums.IndexedEnum;

@AutoCreateClientEnumType
public enum MatchBattleReportType implements IndexedEnum{
	@ClientEnumComment(comment="所有战报")
	ALL_REPORT(1),
	@ClientEnumComment(comment="个人战报")
	PERSONAL_REPORT(2);
	private int index;
	private MatchBattleReportType(int index){
		this.index = index;
	}
	public int getIndex(){
		return this.index;
	}
	
}
