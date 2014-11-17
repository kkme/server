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
public enum WarriorQuestState implements IndexedEnum {
	@ClientEnumComment(comment="未开启")
	UNOPEN(1),
	@ClientEnumComment(comment="进行中")
	ONGOING(2),
	@ClientEnumComment(comment="已完成")
	FINISHED(3);
	private int index;
	private WarriorQuestState(int index){
		this.index = index;
	}
	@Override
	public int getIndex() {		
		return index;
	}
	
}
