package com.hifun.soul.gameserver.quest.handler;

import org.springframework.stereotype.Component;

import com.hifun.soul.common.constants.LangConstants;
import com.hifun.soul.core.msg.MessageType;
import com.hifun.soul.core.msg.injection.IMessageHandlerWithType;
import com.hifun.soul.gameserver.human.Human;
import com.hifun.soul.gameserver.human.quest.Quest;
import com.hifun.soul.gameserver.human.quest.QuestState;
import com.hifun.soul.gameserver.human.quest.manager.HumanQuestManager;
import com.hifun.soul.gameserver.quest.msg.CGAbortDailyQuest;
import com.hifun.soul.gameserver.quest.msg.GCQuestState;

@Component
public class CGAbortDailyQuestHandler implements
		IMessageHandlerWithType<CGAbortDailyQuest> {

	@Override
	public short getMessageType() {
		return MessageType.CG_ABORT_DAILY_QUEST;
	}

	@Override
	public void execute(CGAbortDailyQuest message) {
		Human human = message.getPlayer().getHuman();
		HumanQuestManager questManager = human.getHumanQuestManager();
		int questId = message.getQuestId();
		Quest quest = questManager.getDailyQuest(questId);
		if (quest == null) {
			return;
		}
		// 状态校验
		if (quest.getCurrentState() != QuestState.QUEST_ACCEPTING
				&& quest.getCurrentState() != QuestState.QUEST_AUTO) {
			human.sendErrorMessage(LangConstants.DAILY_QUEST_WRONG_STATE);
			return;
		}
		// 取消任务
		quest.abortDailyQuest();
		questManager.addUpdateQuest(quest);
		// 返回任务状态
		GCQuestState msg = new GCQuestState();
		msg.setQuestId(questId);
		msg.setQuestState(quest.getCurrentState().getIndex());
		human.sendMessage(msg);

	}

}
