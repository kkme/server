package com.hifun.soul.gameserver.matchbattle;

import com.hifun.soul.core.annotation.AutoCreateClientEnumType;
import com.hifun.soul.core.annotation.ClientEnumComment;
import com.hifun.soul.core.enums.IndexedEnum;

/**
 * 匹配战玩家状态
 * @author magicstone
 *
 */
@AutoCreateClientEnumType
public enum MatchBattleRoleState implements IndexedEnum {
	@ClientEnumComment(comment="未参与")
	NOT_JOIN(1),
	@ClientEnumComment(comment="备战中")
	READY(2),
	@ClientEnumComment(comment="待战中")
	WAIT_MATCH(3),
	@ClientEnumComment(comment="战斗中")
	IN_BATTLE(4)
	;
	private int index;
	private MatchBattleRoleState(int index){
		this.index = index;
	}
	public int getIndex(){
		return index;
	}
}
