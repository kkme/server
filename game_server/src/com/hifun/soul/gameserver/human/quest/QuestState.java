package com.hifun.soul.gameserver.human.quest;

import java.util.HashMap;
import java.util.Map;

import com.hifun.soul.core.annotation.AutoCreateClientEnumType;
import com.hifun.soul.core.annotation.ClientEnumComment;
import com.hifun.soul.core.enums.IndexedEnum;

/**
 * 任务状态;
 * 
 * @author crazyjohn
 * 
 */
@AutoCreateClientEnumType
public enum QuestState implements IndexedEnum{
	@ClientEnumComment(comment = "任务可接受 ")
	QUEST_CAN_ACCEPT(1),
	@ClientEnumComment(comment = "任务已经接受, 在完成中 ")
	QUEST_ACCEPTING(2),
	@ClientEnumComment(comment = "任务已经委托, 在完成中 ")
	QUEST_AUTO(3),
	@ClientEnumComment(comment = "任务已经完成, 但是还未领取奖励 ")
	QUEST_CAN_FINISH(4),
	@ClientEnumComment(comment = "任务已经完成, 领取完奖励了 ")
	QUEST_FINISHED(5);

	private int stateCode;
	private static Map<Integer, QuestState> states = new HashMap<Integer, QuestState>();
	
	static {
		for (QuestState each : QuestState.values()) {
			states.put(each.getIndex(), each);
		}
	}

	QuestState(int stateCode) {
		this.stateCode = stateCode;
	}

	public int getStateCode() {
		return stateCode;
	}
	
	public static QuestState typeOf(int type) {
		return states.get(type);
	}

	@Override
	public int getIndex() {
		return this.getStateCode();
	}

}
