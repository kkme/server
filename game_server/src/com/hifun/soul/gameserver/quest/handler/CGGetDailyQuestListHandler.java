package com.hifun.soul.gameserver.quest.handler;

import java.util.List;

import org.springframework.stereotype.Component;

import com.hifun.soul.core.msg.MessageType;
import com.hifun.soul.core.msg.injection.IMessageHandlerWithType;
import com.hifun.soul.gameserver.common.GameServerAssist;
import com.hifun.soul.gameserver.human.Human;
import com.hifun.soul.gameserver.human.quest.QuestInfo;
import com.hifun.soul.gameserver.quest.msg.CGGetDailyQuestList;
import com.hifun.soul.gameserver.quest.msg.GCDailyQuestList;

/**
 * 处理获取日常任务列表请求;
 * 
 * @author crazyjohn
 * 
 */
@Component
public class CGGetDailyQuestListHandler implements
		IMessageHandlerWithType<CGGetDailyQuestList> {

	@Override
	public short getMessageType() {
		return MessageType.CG_GET_DAILY_QUEST_LIST;
	}

	@Override
	public void execute(CGGetDailyQuestList message) {
		Human human = message.getPlayer().getHuman();
		// 下发日常任务列表
		GCDailyQuestList msg = new GCDailyQuestList();
		List<QuestInfo> questList = message.getPlayer().getHuman()
				.getHumanQuestManager().getAllDailyQuests();
		msg.setQuestList(questList.toArray(new QuestInfo[0]));
		msg.setCurrentStore(message.getPlayer().getHuman().getDailyQuestScore());
		msg.setDailyRewardList(message.getPlayer().getHuman()
				.getHumanQuestManager().getDailyRewardList());
		int totalQuestNum = GameServerAssist.getGameConstants()
				.getFreeQuestNum();
		msg.setTotalQuestNum(totalQuestNum);
		msg.setRemainFreeQuestNum(totalQuestNum
				- human.getDailyQuestReceivedNum());
		msg.setReceiveQuestCost(GameServerAssist
				.getVipPrivilegeTemplateManager().getDailyQuestBuyNumCost(
						human.getDailyQuestBuyNum() + 1, human.getVipLevel()));
		msg.setRemainCrystalQuestNum(GameServerAssist
				.getVipPrivilegeTemplateManager()
				.getVipTemplate(human.getVipLevel()).getMaxBuyDailyQuestTimes()
				- human.getDailyQuestBuyNum());
		msg.setRefreshQuestCost(GameServerAssist.getGameConstants()
				.getRefreshDailyQuestsCost());
		human.sendMessage(msg);

		// message.getPlayer().getHuman().getHumanGuideManager().showGuide(GuideType.DAILY_QUEST.getIndex());
	}
}
