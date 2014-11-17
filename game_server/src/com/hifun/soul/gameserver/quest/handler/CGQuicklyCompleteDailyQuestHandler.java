package com.hifun.soul.gameserver.quest.handler;

import org.springframework.stereotype.Component;

import com.hifun.soul.common.LogReasons.MoneyLogReason;
import com.hifun.soul.common.constants.LangConstants;
import com.hifun.soul.core.msg.MessageType;
import com.hifun.soul.core.msg.injection.IMessageHandlerWithType;
import com.hifun.soul.core.util.TimeUtils;
import com.hifun.soul.gameserver.common.GameServerAssist;
import com.hifun.soul.gameserver.currency.CurrencyType;
import com.hifun.soul.gameserver.human.Human;
import com.hifun.soul.gameserver.human.quest.Quest;
import com.hifun.soul.gameserver.human.quest.QuestState;
import com.hifun.soul.gameserver.human.quest.manager.HumanQuestManager;
import com.hifun.soul.gameserver.quest.msg.CGQuicklyCompleteDailyQuest;
import com.hifun.soul.gameserver.quest.msg.GCQuestState;

@Component
public class CGQuicklyCompleteDailyQuestHandler implements
		IMessageHandlerWithType<CGQuicklyCompleteDailyQuest> {

	@Override
	public short getMessageType() {
		return MessageType.CG_QUICKLY_COMPLETE_DAILY_QUEST;
	}

	@Override
	public void execute(CGQuicklyCompleteDailyQuest message) {
		Human human = message.getPlayer().getHuman();
		HumanQuestManager questManager = human.getHumanQuestManager();
		int questId = message.getQuestId();
		Quest quest = questManager.getDailyQuest(questId);
		if (quest == null) {
			return;
		}
		// 状态校验
		if (quest.getCurrentState() != QuestState.QUEST_AUTO) {
			human.sendErrorMessage(LangConstants.DAILY_QUEST_WRONG_STATE);
			return;
		}
		// 加速完成消费
		int costCrystal = (int) ((quest.getEndTime() - GameServerAssist
				.getSystemTimeService().now()) / TimeUtils.MIN);
		if (costCrystal <= 0) {
			return;
		}
		if (human.getWallet().costMoney(CurrencyType.CRYSTAL, costCrystal,
				MoneyLogReason.DAILY_QUEST, "")) {
			// 加速完成任务
			quest.quicklyAutoCompleteDailyQuest();
			questManager.addUpdateQuest(quest);

			// 返回任务状态
			GCQuestState msg = new GCQuestState();
			msg.setQuestId(questId);
			msg.setQuestState(quest.getCurrentState().getIndex());
			human.sendMessage(msg);
		}
	}

}
