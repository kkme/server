package com.hifun.soul.gameserver.quest.handler;

import org.springframework.stereotype.Component;

import com.hifun.soul.common.constants.LangConstants;
import com.hifun.soul.core.msg.MessageType;
import com.hifun.soul.core.msg.injection.IMessageHandlerWithType;
import com.hifun.soul.gameserver.common.GameServerAssist;
import com.hifun.soul.gameserver.human.Human;
import com.hifun.soul.gameserver.human.quest.Quest;
import com.hifun.soul.gameserver.human.quest.QuestState;
import com.hifun.soul.gameserver.human.quest.manager.HumanQuestManager;
import com.hifun.soul.gameserver.quest.msg.CGAutoCompleteDailyQuest;
import com.hifun.soul.gameserver.quest.msg.GCAutoCompleteDailyQuest;

@Component
public class CGAutoCompleteDailyQuestHandler implements
		IMessageHandlerWithType<CGAutoCompleteDailyQuest> {

	@Override
	public short getMessageType() {
		return MessageType.CG_AUTO_COMPLETE_DAILY_QUEST;
	}

	@Override
	public void execute(CGAutoCompleteDailyQuest message) {
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
		// 一次只能委托一个任务
		if (questManager.hasAutoCompleteQuest()) {
			human.sendErrorMessage(LangConstants.HAS_AUTO_QUEST_NOT_COMPLETE);
			return;
		}
		// 开始自动完成任务
		quest.autoCompleteDailyQuest();
		questManager.addUpdateQuest(quest);
		// 返回消息
		GCAutoCompleteDailyQuest msg = new GCAutoCompleteDailyQuest();
		msg.setQuestId(questId);
		msg.setQuestState(quest.getCurrentState().getIndex());
		msg.setRemainTime((int) (quest.getEndTime() - GameServerAssist
				.getSystemTimeService().now()));
		human.sendMessage(msg);

	}

}
