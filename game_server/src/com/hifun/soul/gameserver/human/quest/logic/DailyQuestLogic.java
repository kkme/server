package com.hifun.soul.gameserver.human.quest.logic;

import com.hifun.soul.gameserver.human.Human;
import com.hifun.soul.gameserver.human.quest.Quest;
import com.hifun.soul.gameserver.human.quest.QuestState;
import com.hifun.soul.gameserver.human.quest.QuestType;
import com.hifun.soul.gameserver.quest.msg.GCQuestState;

/**
 * 日常任务逻辑; <br>
 * 跟主线任务不同的逻辑：<br>
 * 
 * <pre>
 * 1. 日常任务不是每次都有金币和经验的奖励, 而是累计到一定的积分给奖励;
 * 
 * <pre>
 * @author crazyjohn
 * 
 */
public class DailyQuestLogic implements IQuestLogic {

	@Override
	public void finishQuest(Quest questInstance, Human human) {
	}

	@Override
	public boolean checkFinish(Quest questInstance, Human human) {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public void whenAllAimFinished(Quest questInstance, Human human) {
		if (questInstance.getQuestTemplate().getQuestType() != QuestType.QUEST_DAILY
				.getIndex()) {
			return;
		}
		// 任务变为奖励可领取状态
		questInstance.setState(QuestState.QUEST_CAN_FINISH);
		// 移除事件监听,防止内存泄露
		questInstance.removeListeners();
		// 更新状态
		GCQuestState questState = new GCQuestState();
		questState.setQuestId(questInstance.getQuestId());
		questState.setQuestState(questInstance.getCurrentState().getIndex());
		human.sendMessage(questState);

	}

}
