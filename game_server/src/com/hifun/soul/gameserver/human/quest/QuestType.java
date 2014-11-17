package com.hifun.soul.gameserver.human.quest;

import com.hifun.soul.core.annotation.AutoCreateClientEnumType;
import com.hifun.soul.core.annotation.ClientEnumComment;
import com.hifun.soul.core.enums.IndexedEnum;

/**
 * 任务类型;
 * 
 * @author crazyjohn
 * 
 */
@AutoCreateClientEnumType
public enum QuestType implements IndexedEnum{
	@ClientEnumComment(comment="日常任务")
	QUEST_DAILY(1),
	@ClientEnumComment(comment="主线任务")
	QUEST_MAIN(2), 
	@ClientEnumComment(comment="支线任务")
	QUEST_BRANCH(3);
	
	private int questType;
	
	QuestType(int questType) {
		this.questType = questType;
	}
	public int getQuestType() {
		return this.questType;
	}
	@Override
	public int getIndex() {
		return getQuestType();
	}
}
