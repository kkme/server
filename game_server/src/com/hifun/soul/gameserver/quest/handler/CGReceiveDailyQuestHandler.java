package com.hifun.soul.gameserver.quest.handler;

import org.springframework.stereotype.Component;

import com.hifun.soul.common.constants.LangConstants;
import com.hifun.soul.core.msg.MessageType;
import com.hifun.soul.core.msg.injection.IMessageHandlerWithType;
import com.hifun.soul.gameserver.human.Human;
import com.hifun.soul.gameserver.human.quest.Quest;
import com.hifun.soul.gameserver.human.quest.QuestState;
import com.hifun.soul.gameserver.human.quest.manager.HumanQuestManager;
import com.hifun.soul.gameserver.quest.msg.CGReceiveDailyQuest;
import com.hifun.soul.gameserver.quest.msg.GCQuestState;

@Component
public class CGReceiveDailyQuestHandler implements
		IMessageHandlerWithType<CGReceiveDailyQuest> {

	@Override
	public short getMessageType() {
		return MessageType.CG_RECEIVE_DAILY_QUEST;
	}

	@Override
	public void execute(CGReceiveDailyQuest message) {
		Human human = message.getPlayer().getHuman();
		HumanQuestManager questManager = human.getHumanQuestManager();
		int questId = message.getQuestId();
		Quest quest = questManager.getDailyQuest(questId);
		if (quest == null) {
			return;
		}
		// 状态校验
		if (quest.getCurrentState() != QuestState.QUEST_CAN_ACCEPT) {
			human.sendErrorMessage(LangConstants.DAILY_QUEST_WRONG_STATE);
			return;
		}
		// 启动任务
		quest.startQuest();
		questManager.addUpdateQuest(quest);
		// 返回任务状态
		GCQuestState msg = new GCQuestState();
		msg.setQuestId(questId);
		msg.setQuestState(quest.getCurrentState().getIndex());
		human.sendMessage(msg);
	}

}
