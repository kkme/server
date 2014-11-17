package com.hifun.soul.gameserver.warrior;

import com.hifun.soul.core.annotation.AutoCreateClientEnumType;
import com.hifun.soul.core.annotation.ClientEnumComment;
import com.hifun.soul.core.enums.IndexedEnum;

/**
 * 勇者之路任务奖励状态
 * @author magicstone
 *
 */
@AutoCreateClientEnumType
public enum WarriorQuestRewardState implements IndexedEnum {
	@ClientEnumComment(comment="不可见")
	UNVISABLE(1),
	@ClientEnumComment(comment="可见不可用")
	VISABLE(2),
	@ClientEnumComment(comment="可领取")
	CAN_GET(3),
	@ClientEnumComment(comment="已领取")
	HAS_GOT(4);
	private int index;
	private WarriorQuestRewardState(int index){
		this.index = index;
	}
	@Override
	public int getIndex() {		
		return index;
	}
	
}
