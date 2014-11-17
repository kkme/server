package com.hifun.soul.gameserver.quest.handler;

import java.util.List;

import org.springframework.stereotype.Component;

import com.hifun.soul.common.LogReasons.MoneyLogReason;
import com.hifun.soul.common.constants.LangConstants;
import com.hifun.soul.core.msg.MessageType;
import com.hifun.soul.core.msg.injection.IMessageHandlerWithType;
import com.hifun.soul.gameserver.common.GameServerAssist;
import com.hifun.soul.gameserver.currency.CurrencyType;
import com.hifun.soul.gameserver.human.Human;
import com.hifun.soul.gameserver.human.quest.QuestInfo;
import com.hifun.soul.gameserver.quest.msg.CGRefreshDailyQuestList;
import com.hifun.soul.gameserver.quest.msg.GCRefreshDailyQuestList;

@Component
public class CGRefreshDailyQuestListHandler implements
		IMessageHandlerWithType<CGRefreshDailyQuestList> {

	@Override
	public short getMessageType() {
		return MessageType.CG_REFRESH_DAILY_QUEST_LIST;
	}

	@Override
	public void execute(CGRefreshDailyQuestList message) {
		Human human = message.getPlayer().getHuman();
		// 判断是否有可以刷新的任务
		if (!human.getHumanQuestManager().canRefreshDailyQuest()) {
			human.sendErrorMessage(LangConstants.DAILY_QUEST_CAN_NOT_REFRESH);
			return;
		}
		// 刷新消费
		int costCrystal = GameServerAssist.getGameConstants()
				.getRefreshDailyQuestsCost();
		if (human.getWallet().costMoney(CurrencyType.CRYSTAL, costCrystal,
				MoneyLogReason.DAILY_QUEST, "")) {
			GCRefreshDailyQuestList msg = new GCRefreshDailyQuestList();
			human.getHumanQuestManager().refreshDailyQuests();
			List<QuestInfo> questList = human.getHumanQuestManager()
					.getAllDailyQuests();
			msg.setQuestList(questList.toArray(new QuestInfo[0]));
			human.sendMessage(msg);
		}

	}

}
